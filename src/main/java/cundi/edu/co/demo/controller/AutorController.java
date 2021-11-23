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

import cundi.edu.co.demo.dto.AutorDto;
import cundi.edu.co.demo.entity.Autor;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.service.IAutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@PreAuthorize("hasAuthority('Administrador') 
@RestController
@RequestMapping("autor")
@Validated
@Api(value = "Servicio autor", description = "Esta API tiene el crud de autores")
public class AutorController {

	@Autowired
	private IAutorService service;
	
	//@PreAuthorize("hasAuthority('Administrador')  OR hasAuthority(' ') ")
	@ApiOperation(value = "Obtiene todos los autores", notes = "Retorna todos los autores")
	@GetMapping(value = "/obtenerPaginado/{page}/{size}" ,produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Autor.class)})	
	public ResponseEntity<?> retonarPaginado(@PathVariable int page, @PathVariable int size) {
		Page<AutorDto> listaAutores = service.retornarPaginadoDto(page, size);
		return new ResponseEntity<Page<AutorDto>>(listaAutores, HttpStatus.OK);	
	}
	
	//@PreAuthorize("hasAuthority('Administrador')  OR hasAuthority('Vendedor') ")
	@ApiOperation(value = "Buscar autor", notes = "Retorna autor por ID")
	@GetMapping(value = "obtener/{id}/{libros}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Autor.class ),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el autor") })	
	public ResponseEntity<?> retornarAutor(@ApiParam(name = "id", type = "Int") @PathVariable("id") int id, @PathVariable("libros") boolean libros ) throws ModelNotFoundException, Exception {
		if (libros == true) {
			Autor autor = service.retonarPorId(id);
			autor.add(
					linkTo(methodOn(AutorController.class).retornarAutor(id,true)).withRel("select id con libros").withType("GET"),
					linkTo(methodOn(AutorController.class).retornarAutor(id,false)).withRel("select id sin libros").withType("GET"),
					linkTo(methodOn(AutorController.class).editar(autor)).withRel("update").withType("PUT"),
					linkTo(methodOn(AutorController.class).eliminar(id)).withRel("delete").withType("DELETE"));
			
			return new ResponseEntity<Object>(autor, HttpStatus.OK);
		}else {
			AutorDto autor = service.retornarIdLibros(id);
			autor.add(
					linkTo(methodOn(AutorController.class).retornarAutor(id,true)).withRel("select id con libros").withType("GET"),
					linkTo(methodOn(AutorController.class).retornarAutor(id,false)).withRel("select id sin libros").withType("GET"),
					linkTo(methodOn(AutorController.class).eliminar(id)).withRel("delete").withType("DELETE"));
			
			return new ResponseEntity<Object>(autor, HttpStatus.OK);
		}
		
		
	}
	
	@ApiOperation(value = "Insertar autor", notes = "Crea un nuevo autor")
	@PostMapping(value = "/insertar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El autor se creo correctamente"),
            @ApiResponse(code = 409, message = "Conflict. El correo ya esta registrado") })
	public ResponseEntity<?> guardar (@Valid @RequestBody Autor autor) throws IntegridadException, Exception {
		service.guardar(autor);
		
		autor.add(
				linkTo(methodOn(AutorController.class).retornarAutor(autor.getId(),true)).withRel("select id con libros").withType("GET"),
				linkTo(methodOn(AutorController.class).retornarAutor(autor.getId(),false)).withRel("select id sin libros").withType("GET"),
				linkTo(methodOn(AutorController.class).editar(autor)).withRel("update").withType("PUT"),
				linkTo(methodOn(AutorController.class).eliminar(autor.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(autor, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Editar autor", notes = "Edita un autor")
	@PutMapping(value = "/editar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Ok. Se edito el autor correctamente"),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el autor"),
            @ApiResponse(code = 409, message = "Conflict. El correo ya esta registrado")})	
	public ResponseEntity<?> editar (@Valid @RequestBody  Autor autor) throws ModelNotFoundException, IntegridadException, Exception {
		service.editar(autor);
		
		autor.add(
				linkTo(methodOn(AutorController.class).retornarAutor(autor.getId(),true)).withRel("select id con libros").withType("GET"),
				linkTo(methodOn(AutorController.class).retornarAutor(autor.getId(),false)).withRel("select id sin libros").withType("GET"),
				linkTo(methodOn(AutorController.class).eliminar(autor.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(autor, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Eliminar autor", notes = "Elimina un autor por ID")
	@DeleteMapping (value = "/eliminar/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content. Se borró el autor correctamente"),
            @ApiResponse(code = 404, message = "No se encontro el autor") })	
	public ResponseEntity<?> eliminar(@ApiParam(name = "id", type = "Int") @PathVariable int id) throws ModelNotFoundException, IntegridadException, Exception {
		service.eliminar(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
}
