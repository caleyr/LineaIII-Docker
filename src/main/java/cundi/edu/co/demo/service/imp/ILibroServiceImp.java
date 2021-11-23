package cundi.edu.co.demo.service.imp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
 
import cundi.edu.co.demo.entity.Libro;
import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.repository.IAutorRepo;
import cundi.edu.co.demo.repository.ILibroRepo;
import cundi.edu.co.demo.service.ILibroService;

@Service
public class ILibroServiceImp implements ILibroService{

	@Autowired
	private ILibroRepo repo;
	
	@Autowired
	private IAutorRepo repoAutor;
	
	private Boolean validarExistenciaPorId(int idLibro) {
		return repo.existsById(idLibro);
	}
	
	@Override
	public Page<Libro> retornarPaginado(int page, int size) {
		Page<Libro> resultado = repo.findAll(PageRequest.of(page,size));
		for (Libro libro : resultado)
			libro.setAutorId(null);
		return resultado;
	}

	@Override
	public Libro retonarPorId(Integer id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id)) {		
			Libro libro = this.repo.getById(id);
			return libro;
 		} else
			throw new ModelNotFoundException("Libro no encontrado");	
	}

	@Override
	public void guardar(Libro libro) throws IntegridadException {}
	
	@Transactional
	@Override
	public void guardarLibro(Libro libro, int idAutor) throws IntegridadException {
		if (repo.existsByNombre(libro.getNombre(), idAutor) != null) {
			throw new IntegridadException("El libro ya existe para este autor");
		}
		libro.setAutorId(repoAutor.getById(idAutor));
		this.repo.save(libro);		
	}

	@Override
	public void editar(Libro libro) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException {}
	
	@Transactional
	@Override
	public void editarLibro(Libro libro, int idAutor) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException {
		if(libro.getId() != null) {
			if(validarExistenciaPorId(libro.getId())) {
				libro.setAutorId(repoAutor.getById(idAutor));
				this.repo.save(libro);		
				
			} else 
				throw new ModelNotFoundException("Libro no encontrado");		
		} else {
			throw new ArgumentRequiredException("IdLibro es requerido");
		}		
	}

	@Override
	public void eliminar(int id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id))
			this.repo.deleteById(id);
		else
			throw new ModelNotFoundException("Libro no encontrado");	
		
	}

}
