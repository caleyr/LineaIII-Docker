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

import cundi.edu.co.demo.entity.AutorEditorial;
import cundi.edu.co.demo.entity.Editorial;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.service.IAutorEditorialService;
import cundi.edu.co.demo.service.IEditorialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("editorial")
@Validated
@Api(value = "Servicio editoriral", description = "Esta API tiene el crud editorial")
public class EditorialController {
	
	@Autowired
	private IAutorEditorialService service;
	
	@Autowired 
	private IEditorialService serviceEditorial;
	
	@ApiOperation(value = "Obtiene todas las asosiaciones", notes = "Retorna todas las asociaciones ")
	@GetMapping(value = "/obtenerPaginadoAsociacion/{page}/{size}" ,produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = AutorEditorial.class)})	
	public ResponseEntity<?> retonarPaginadoAsociacion(@PathVariable int page, @PathVariable int size) {
		Page<AutorEditorial> listaAsociacion = service.retornarPaginado(page, size);
		return new ResponseEntity<Page<AutorEditorial>>(listaAsociacion, HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Buscar asosiacion", notes = "Retorna asociacion por ID")
	@GetMapping(value = "obtenerAsociacion/{idAutor}/{idEditorial}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = AutorEditorial.class ),
            @ApiResponse(code = 404, message = "Not Found. No se encontro la editorial") })	
	public ResponseEntity<?> retornarAsosiacion(@ApiParam(name = "idAutor", type = "Int") @PathVariable int idAutor, @ApiParam(name = "idEditorial", type = "Int") @PathVariable int idEditorial) throws ModelNotFoundException, Exception {
		AutorEditorial autorEditorial = service.retonarPorIdNativo(idAutor, idEditorial);
		
		return new ResponseEntity<Object>(autorEditorial, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Asociar autor a editorial", notes = "Asocia un autor a una editorial")
	@PostMapping(value = "/asosiarEditorial", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El autor se asocio correctamente"),
            @ApiResponse(code = 409, message = "Conflict. El auter ya esta asociado") })
	public ResponseEntity<?> asociarEditorial (@Valid @RequestBody AutorEditorial autorEditorial) throws IntegridadException, Exception {
		service.guardarNativo(autorEditorial);
		
		autorEditorial.add(
				linkTo(methodOn(EditorialController.class).desasociarEditorial(autorEditorial.getAutor().getId(), autorEditorial.getEditorial().getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(autorEditorial, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Desasociar autor a editorial", notes = "Desasocia un autor a una editorial por los IDs")
	@DeleteMapping (value = "/desasosiarEditorial/{idAutor}/{idEditorial}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content. Se borró la asosiación correctamente"),
            @ApiResponse(code = 404, message = "No se encontro el editorial o autor") })	
	public ResponseEntity<?> desasociarEditorial(@ApiParam(name = "idAutor", type = "Int") @PathVariable int idAutor, @ApiParam(name = "idEditorial", type = "Int") @PathVariable int idEditorial) throws ModelNotFoundException, IntegridadException, Exception {
		service.eliminarNativo(idAutor, idEditorial);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Obtiene todas las editoriales", notes = "Retorna todas las editoriales")
	@GetMapping(value = "/obtenerPaginado/{page}/{size}" ,produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Editorial.class)})	
	public ResponseEntity<?> retonarPaginado(@PathVariable int page, @PathVariable int size) {
		Page<Editorial> listaEditorial = serviceEditorial.retornarPaginado(page, size);
		return new ResponseEntity<Page<Editorial>>(listaEditorial, HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Buscar editorial", notes = "Retorna editorial por ID")
	@GetMapping(value = "obtener/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Editorial.class ),
            @ApiResponse(code = 404, message = "Not Found. No se encontro la editorial") })	
	public ResponseEntity<?> retornarEditorial(@ApiParam(name = "id", type = "Int") @PathVariable("id") int id ) throws ModelNotFoundException, Exception {
		Editorial editorial = serviceEditorial.retonarPorId(id);
		
		editorial.add(
				linkTo(methodOn(EditorialController.class).retornarEditorial(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(EditorialController.class).editar(editorial)).withRel("update").withType("PUT"),
				linkTo(methodOn(EditorialController.class).eliminar(id)).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(editorial, HttpStatus.OK);
	}

	@ApiOperation(value = "Insertar editorial", notes = "Crea una nueva editorial")
	@PostMapping(value = "/insertar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. La editorial se creo correctamente"),
            @ApiResponse(code = 409, message = "Conflict. El correo ya esta registrado") })
	public ResponseEntity<?> guardar (@Valid @RequestBody Editorial editorial) throws IntegridadException, Exception {
		serviceEditorial.guardar(editorial);
		
		editorial.add(
				linkTo(methodOn(EditorialController.class).retornarEditorial(editorial.getId())).withSelfRel().withType("GET"),
				linkTo(methodOn(EditorialController.class).editar(editorial)).withRel("update").withType("PUT"),
				linkTo(methodOn(EditorialController.class).eliminar(editorial.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(editorial, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Editar editorial", notes = "Edita una editorial")
	@PutMapping(value = "/editar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Ok. Se edito la editorial correctamente"),
            @ApiResponse(code = 404, message = "Not Found. No se encontro la editorial"),
            @ApiResponse(code = 409, message = "Conflict. El correo ya esta registrado")})	
	public ResponseEntity<?> editar (@Valid @RequestBody Editorial editorial) throws ModelNotFoundException, IntegridadException, Exception {
		serviceEditorial.editar(editorial);
		
		editorial.add(
				linkTo(methodOn(EditorialController.class).retornarEditorial(editorial.getId())).withRel("get").withType("GET"),
				linkTo(methodOn(EditorialController.class).eliminar(editorial.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(editorial, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Eliminar editorial", notes = "Elimina una editorial por ID")
	@DeleteMapping (value = "/eliminar/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content. Se borró la editorial correctamente"),
            @ApiResponse(code = 404, message = "No se encontro el editorial") })	
	public ResponseEntity<?> eliminar(@ApiParam(name = "id", type = "Int") @PathVariable int id) throws ModelNotFoundException, IntegridadException, Exception {
		serviceEditorial.eliminar(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}
