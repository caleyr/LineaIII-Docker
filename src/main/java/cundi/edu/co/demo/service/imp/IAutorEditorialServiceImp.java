package cundi.edu.co.demo.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cundi.edu.co.demo.dto.AutorDto;
import cundi.edu.co.demo.entity.Autor;
import cundi.edu.co.demo.entity.AutorEditorial;
import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.repository.IAutorEditorialRepo;
import cundi.edu.co.demo.repository.IAutorRepo;
import cundi.edu.co.demo.repository.IEditorialRepo;
import cundi.edu.co.demo.service.IAutorEditorialService;

@Service
public class IAutorEditorialServiceImp implements IAutorEditorialService{
	
	@Autowired
	private IAutorEditorialRepo repo;
	
	@Autowired
	private IAutorRepo repoAutor;

	@Autowired
	private IEditorialRepo repoEditorial;

	@Override
	public Page<AutorEditorial> retornarPaginado(int page, int size) {
		Page<AutorEditorial> resultado = repo.findAll(PageRequest.of(page,size));
		return resultado;
	}

	@Override
	public AutorEditorial retonarPorId(Integer id) throws ModelNotFoundException {
		return null;
	}
	
	@Override
	public AutorEditorial retonarPorIdNativo(Integer idAutor, Integer idEditorial) throws ModelNotFoundException {
		if(!repoAutor.existsById(idAutor))
			throw new ModelNotFoundException("Autor no encontrado");
		if(!repoEditorial.existsById(idEditorial))
			throw new ModelNotFoundException("Editorial no encontrada");
		if(repo.buscarRelacion(idAutor, idEditorial) != null) {
			AutorEditorial autorEditorial = repo.buscarRelacion(idAutor, idEditorial);
			return autorEditorial;
 		} else {
			throw new ModelNotFoundException("Asociación no encontrada");
 		}
	}

	@Override
	public void guardar(AutorEditorial autorEditorial) throws IntegridadException {}
	
	@Override
	public void guardarNativo(AutorEditorial autorEditorial) throws IntegridadException, ModelNotFoundException {
		if(!repoAutor.existsById(autorEditorial.getAutor().getId()))
			throw new ModelNotFoundException("Autor no encontrado");
		if(!repoEditorial.existsById(autorEditorial.getEditorial().getId()))
			throw new ModelNotFoundException("Editorial no encontrada");
		if(repo.buscarRelacion(autorEditorial.getAutor().getId(), autorEditorial.getEditorial().getId()) != null)
			throw new IntegridadException("Asociciación ya creada");
		
		this.repo.guardarNativo(autorEditorial.getAutor().getId(), autorEditorial.getEditorial().getId(), autorEditorial.getFecha());		
	}

	@Override
	public void editar(AutorEditorial objeto) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException {}

	@Override
	public void eliminar(int id) throws ModelNotFoundException {}

	@Override
	public void eliminarNativo(Integer idAutor, Integer idEditorial) throws ModelNotFoundException {
		if(!repoAutor.existsById(idAutor))
			throw new ModelNotFoundException("Autor no encontrado");
		if(!repoEditorial.existsById(idEditorial))
			throw new ModelNotFoundException("Editorial no encontrada");
		
		this.repo.eliminarNativa(idAutor, idEditorial);
		
	}

	

	
}
