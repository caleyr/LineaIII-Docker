package cundi.edu.co.demo.service;


import org.springframework.data.domain.Page;

import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;

public interface ICrud<T,ID> {
	
	public Page<T> retornarPaginado(int page, int size);
	
	public T retonarPorId(ID id) throws ModelNotFoundException;
		
	public void guardar(T objeto)  throws IntegridadException;
	
	public void editar(T objeto)  throws ArgumentRequiredException, ModelNotFoundException, IntegridadException;
	
	public void eliminar(int idObjeto) throws ModelNotFoundException;	

}
