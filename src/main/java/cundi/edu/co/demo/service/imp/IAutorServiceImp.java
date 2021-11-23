package cundi.edu.co.demo.service.imp;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cundi.edu.co.demo.dto.AutorDto;
import cundi.edu.co.demo.entity.Autor;
import cundi.edu.co.demo.entity.Libro;
import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.repository.IAutorRepo;
import cundi.edu.co.demo.service.IAutorService;

@Service
public class IAutorServiceImp implements IAutorService{
	
	@Autowired
	private IAutorRepo repo;
	
	private Boolean validarExistenciaPorId(int idAutor) {
		return repo.existsById(idAutor);
	}	


	@Override
	public Page<AutorDto> retornarPaginadoDto(int page, int size) {
		Page<Autor> resultado = repo.findAll(PageRequest.of(page,size));
		Page<AutorDto> autorDtoPage = resultado.map(this::convertToAutorDto);
		return autorDtoPage;
	}

	@Override
	public Page<Autor> retornarPaginado(int page, int size) {
		return repo.findAll(PageRequest.of(page,size));
	}

	@Override
	public Autor retonarPorId(Integer id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id)) {			
			Autor autor = this.repo.getById(id);
			return autor;
 		} else
			throw new ModelNotFoundException("Autor no encontrado");	
	}	

	@Override
	public AutorDto retornarIdLibros(Integer id) throws ModelNotFoundException{
		ModelMapper modelMapper = new ModelMapper(); 
		if(validarExistenciaPorId(id)) {
			Autor autor = this.repo.getById(id);
			AutorDto autorDto = modelMapper.map(autor, AutorDto.class);
			return autorDto;
 		} else
			throw new ModelNotFoundException("Autor no encontrado");
	}

	@Transactional
	@Override
	public void guardar(Autor autor) throws IntegridadException {
		if (repo.buscarCedulas(autor.getCedula()) >= 1) {
			throw new IntegridadException("Cedula ya existe");
		}
		if (repo.existsByCorreo(autor.getCorreo())) {
			throw new IntegridadException("Correo ya existe");
		}
		if (autor.getLibro() != null) {
			for (Libro libro : autor.getLibro()) {
				libro.setAutorId(autor);
			}
		}
		this.repo.save(autor);
		
	}

	@Override
	public void editar(Autor autor) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException {
		if(autor.getId() != null) {
			if(validarExistenciaPorId(autor.getId())) {
				
				Autor autorAux = this.repo.findById(autor.getId()).get();
				autorAux.setCedula(autorAux.getCedula());
				
				if(repo.existeCorreo(autor.getId(), autorAux.getCorreo()) == null)
					this.repo.save(autorAux);
				else 
					throw new IntegridadException("Correo ya existe");				
				
			} else 
				throw new ModelNotFoundException("Autor no encontrado");		
		} else {
			throw new ArgumentRequiredException("IdAutor es requerido");
		}
		
	}

	@Transactional
	@Override
	public void eliminar(int id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id))
			this.repo.deleteById(id);
		else
			throw new ModelNotFoundException("Autor no encontrado");	
		
	}
	
	private AutorDto convertToAutorDto(final Autor autor) {
		final AutorDto autorDto = new AutorDto();
		autorDto.setId(autor.getId());
		autorDto.setCedula(autor.getCedula());
		autorDto.setNombre(autor.getNombre());
		autorDto.setApellido(autor.getApellido());
		autorDto.setCorreo(autor.getCorreo());
		return autorDto;
	}


}
