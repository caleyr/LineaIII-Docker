package cundi.edu.co.demo.service;

import cundi.edu.co.demo.entity.AutorEditorial;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;

public interface IAutorEditorialService extends ICrud<AutorEditorial, Integer>{

	public void guardarNativo(AutorEditorial autorEditorial) throws IntegridadException, ModelNotFoundException;
	
	public void eliminarNativo(Integer idAutor, Integer idEditorial) throws ModelNotFoundException;
	
	public AutorEditorial retonarPorIdNativo(Integer idAutor, Integer idEditorial) throws ModelNotFoundException;
	
}
