package cundi.edu.co.demo.service.imp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cundi.edu.co.demo.entity.Editorial;
import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.repository.IEditorialRepo;
import cundi.edu.co.demo.service.IEditorialService;

@Service
public class IEditorialServiceImp implements IEditorialService{
	
	@Autowired
	private IEditorialRepo repo;
	
	private Boolean validarExistenciaPorId(int idEditorial) {
		return repo.existsById(idEditorial);
	}

	@Override
	public Page<Editorial> retornarPaginado(int page, int size) {
		return repo.findAll(PageRequest.of(page,size));
	}

	@Override
	public Editorial retonarPorId(Integer id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id)) {			
			Editorial editorial = this.repo.getById(id);
			return editorial;
 		} else
			throw new ModelNotFoundException("Editorial no encontrada");	
	}

	@Override
	public void guardar(Editorial editorial) throws IntegridadException {
		if (repo.existsByNit(editorial.getNit())) {
			throw new IntegridadException("Nit ya existente");
		}
		if (repo.existsByCorreo(editorial.getCorreo())) {
			throw new IntegridadException("Correo ya existe");
		}
		this.repo.save(editorial);
		
	}
	
	@Transactional
	@Override
	public void editar(Editorial editorial) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException {
		if(editorial.getId() != null) {
			if(validarExistenciaPorId(editorial.getId())) {
				
				Editorial editorial2 = this.repo.findById(editorial.getId()).get();
				editorial2.setNit(editorial2.getNit());
				
				if(repo.existeCorreo(editorial2.getCorreo()) == null)
					this.repo.save(editorial);
				else 
					throw new IntegridadException("Correo ya existe");				
				
			} else 
				throw new ModelNotFoundException("Editorial no encontrada");		
		} else {
			throw new ArgumentRequiredException("IdEditorial es requerido");
		}
		
	}

	@Transactional
	@Override
	public void eliminar(int id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id))
			this.repo.deleteById(id);
		else
			throw new ModelNotFoundException("Editorial no encontrada");	
		
	}

}
