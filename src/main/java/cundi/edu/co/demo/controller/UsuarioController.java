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

import cundi.edu.co.demo.entity.Usuario;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.service.IUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("usuario")
@Validated
@Api(value = "Servicio usuario", description = "Esta API tiene el crud de usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService service;
	
	@ApiOperation(value = "Obtiene todos los usuarios", notes = "Retorna todos los usuarios")
	@GetMapping(value = "/obtenerPaginado/{page}/{size}" ,produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Usuario.class)})	
	public ResponseEntity<?> retonarPaginado(@PathVariable int page, @PathVariable int size) {
		Page<Usuario> listaUsuarios = service.retornarPaginado(page, size);
		return new ResponseEntity<Page<Usuario>>(listaUsuarios, HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Buscar usuario", notes = "Retorna usuario por ID")
	@GetMapping(value = "obtener/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Usuario.class ),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el usuario") })	
	public ResponseEntity<?> retornarLibro(@ApiParam(name = "id", type = "Int") @PathVariable("id") int id) throws ModelNotFoundException, Exception {
		Usuario usuario = service.retonarPorId(id);
		usuario.add(
				linkTo(methodOn(UsuarioController.class).retornarLibro(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(UsuarioController.class).editar(usuario)).withRel("update").withType("PUT"),
				linkTo(methodOn(UsuarioController.class).eliminar(id)).withRel("delete").withType("DELETE"));

		return new ResponseEntity<Object>(usuario, HttpStatus.OK);
		
		
	}
	
	@ApiOperation(value = "Insertar usuario", notes = "Crea un nuevo usuario")
	@PostMapping(value = "/insertar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El usuario se creo correctamente"),
            @ApiResponse(code = 409, message = "Conflict. El usuario ya esta registrado") })
	public ResponseEntity<?> guardar (@Valid @RequestBody Usuario usuario) throws IntegridadException, Exception {
		service.guardar(usuario);
		
		usuario.add(
				linkTo(methodOn(UsuarioController.class).retornarLibro(usuario.getIdUsuario())).withRel("select").withType("GET"),
				linkTo(methodOn(UsuarioController.class).editar(usuario)).withRel("update").withType("PUT"),
				linkTo(methodOn(UsuarioController.class).eliminar(usuario.getIdUsuario())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(usuario, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Editar usuario", notes = "Edita un usuario")
	@PutMapping(value = "/editar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Ok. Se edito el usuario correctamente"),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el usuario"),
            @ApiResponse(code = 409, message = "Conflict. El usuario ya esta registrado")})	
	public ResponseEntity<?> editar (@Valid @RequestBody Usuario usuario) throws ModelNotFoundException, IntegridadException, Exception {
		service.editar(usuario);
		
		usuario.add(
				linkTo(methodOn(UsuarioController.class).retornarLibro(usuario.getIdUsuario())).withRel("select id con libros").withType("GET"),
				linkTo(methodOn(UsuarioController.class).eliminar(usuario.getIdUsuario())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(usuario, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Eliminar usuario", notes = "Elimina un usuario por ID")
	@DeleteMapping (value = "/eliminar/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content. Se borró el usuario correctamente"),
            @ApiResponse(code = 404, message = "No se encontro el usuario") })	
	public ResponseEntity<?> eliminar(@ApiParam(name = "id", type = "Int") @PathVariable int id) throws ModelNotFoundException, IntegridadException, Exception {
		service.eliminar(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
