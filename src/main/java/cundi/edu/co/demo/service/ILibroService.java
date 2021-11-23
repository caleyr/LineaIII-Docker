package cundi.edu.co.demo.service;

import cundi.edu.co.demo.entity.Libro;
import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;

public interface ILibroService extends ICrud<Libro, Integer>{

	public void guardarLibro(Libro libro, int idAutor) throws IntegridadException;	
	
	public void editarLibro(Libro libro, int idAutor) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException ;
	
}
