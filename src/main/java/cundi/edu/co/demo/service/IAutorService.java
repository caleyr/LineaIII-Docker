package cundi.edu.co.demo.service;

import org.springframework.data.domain.Page;

import cundi.edu.co.demo.dto.AutorDto;
import cundi.edu.co.demo.entity.Autor;
import cundi.edu.co.demo.exception.ModelNotFoundException;

public interface IAutorService extends ICrud<Autor, Integer>{
	
	public Page<AutorDto> retornarPaginadoDto(int page, int size); 
	
	public AutorDto retornarIdLibros(Integer id) throws ModelNotFoundException;

}
