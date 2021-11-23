package cundi.edu.co.demo.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cundi.edu.co.demo.entity.Estudiante;
import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.repository.IEstudianteRepo;
import cundi.edu.co.demo.service.IEstudianteService;

@Service
public class IEstudianteServiceImp implements IEstudianteService{
	
	@Autowired
	private IEstudianteRepo repo;
	
	private Boolean validarExistenciaPorId(int idEstudiante) {
		return repo.existsById(idEstudiante);
	}

	@Override
	public Page<Estudiante> retornarPaginado(int page, int size) {
		return repo.findAll(PageRequest.of(page,size));
	}

	@Override
	public Estudiante retonarPorId(Integer id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id)) {			
			Estudiante estudiante = this.repo.getById(id);
			return estudiante;
 		} else
			throw new ModelNotFoundException("Estudiante no encontrado");		
	}
	
	@Override
	public void guardar(Estudiante estudiante) throws IntegridadException{
		if (repo.buscarCedulas(estudiante.getCedula()) >= 1) {
			throw new IntegridadException("Cedula ya existe");
		}
		if (repo.existsByCorreo(estudiante.getCorreo())) {
			throw new IntegridadException("Correo ya existe");
		}
		this.repo.save(estudiante);
	}

	@Override
	public void editar(Estudiante estudiante) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException{
		if(estudiante.getId() != null) {
			if(validarExistenciaPorId(estudiante.getId())) {
				
				Estudiante estudianteAux = this.repo.findById(estudiante.getId()).get();
				estudiante.setCedula(estudianteAux.getCedula());
				
				if(repo.existeCorreo(estudianteAux.getCorreo()) == null)
					this.repo.save(estudiante);
				else 
					throw new IntegridadException("Correo ya existe");				
				
			} else 
				throw new ModelNotFoundException("Estudiante no encontrado");		
		} else {
			throw new ArgumentRequiredException("IdEstudiante es requerido");
		}
	}

	@Override
	public void eliminar(int id) throws ModelNotFoundException{
		if(validarExistenciaPorId(id))
			this.repo.deleteById(id);
		else
			throw new ModelNotFoundException("Estudiante no encontrado");	
	}


}
