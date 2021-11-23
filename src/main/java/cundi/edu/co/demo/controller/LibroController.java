package cundi.edu.co.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cundi.edu.co.demo.entity.Autor;
import cundi.edu.co.demo.entity.Libro;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.service.ILibroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("libro")
@Validated
@Api(value = "Servicio libro", description = "Esta API tiene el crud de libros")
public class LibroController {

	@Autowired
	private ILibroService service;
	
	@ApiOperation(value = "Obtiene todos los libros", notes = "Retorna todos los libros")
	@GetMapping(value = "/obtenerPaginado/{page}/{size}" ,produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Libro.class)})	
	public ResponseEntity<?> retonarPaginado(@PathVariable int page, @PathVariable int size) {
		Page<Libro> listaLibros = service.retornarPaginado(page, size);
		return new ResponseEntity<Page<Libro>>(listaLibros, HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Buscar libro", notes = "Retorna libro por ID")
	@GetMapping(value = "obtener/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Libro.class ),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el libro") })	
	public ResponseEntity<?> retornarLibro(@ApiParam(name = "id", type = "Int") @PathVariable("id") int id) throws ModelNotFoundException, Exception {
		Libro libro = service.retonarPorId(id);
		libro.add(
				linkTo(methodOn(LibroController.class).retornarLibro(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(LibroController.class).editar(libro, libro.getAutorId().getId())).withRel("update").withType("PUT"),
				linkTo(methodOn(LibroController.class).eliminar(id)).withRel("delete").withType("DELETE"));

		return new ResponseEntity<Object>(libro, HttpStatus.OK);
		
		
	}
	
	@ApiOperation(value = "Insertar libro", notes = "Crea un nuevo libro")
	@PostMapping(value = "/insertar/{idAutor}", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El libro se creo correctamente"),
            @ApiResponse(code = 409, message = "Conflict. El libro ya esta registrado") })
	public ResponseEntity<?> guardar (@Valid @RequestBody Libro libro, @ApiParam(name = "idAutor", type = "Int") @PathVariable("idAutor") int idAutor) throws IntegridadException, Exception {
		service.guardarLibro(libro, idAutor);
		
		libro.add(
				linkTo(methodOn(LibroController.class).retornarLibro(libro.getId())).withRel("select").withType("GET"),
				linkTo(methodOn(LibroController.class).editar(libro, idAutor)).withRel("update").withType("PUT"),
				linkTo(methodOn(LibroController.class).eliminar(libro.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(libro, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Editar libro", notes = "Edita un libro")
	@PutMapping(value = "/editar/{idAutor}", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Ok. Se edito el libro correctamente"),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el libro"),
            @ApiResponse(code = 409, message = "Conflict. El libro ya esta registrado")})	
	public ResponseEntity<?> editar (@Valid @RequestBody Libro libro, @ApiParam(name = "idAutor", type = "Int") @PathVariable("idAutor") int idAutor) throws ModelNotFoundException, IntegridadException, Exception {
		service.editarLibro(libro, idAutor);
		
		libro.add(
				linkTo(methodOn(LibroController.class).retornarLibro(libro.getId())).withRel("select id con libros").withType("GET"),
				linkTo(methodOn(LibroController.class).eliminar(libro.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(libro, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Eliminar libro", notes = "Elimina un libro por ID")
	@DeleteMapping (value = "/eliminar/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content. Se borró el libro correctamente"),
            @ApiResponse(code = 404, message = "No se encontro el librof") })	
	public ResponseEntity<?> eliminar(@ApiParam(name = "id", type = "Int") @PathVariable int id) throws ModelNotFoundException, IntegridadException, Exception {
		service.eliminar(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
