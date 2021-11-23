package cundi.edu.co.demo.controller;

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

import cundi.edu.co.demo.entity.Estudiante;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.service.IEstudianteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("estudiante")
@Validated
@Api(value = "Servicio estudiante", description = "Esta API tiene el crud de estudiantes")
public class EstudianteController{
	
	@Autowired
	private IEstudianteService service;
	
	@ApiOperation(value = "Obtiene todos los estudiante", notes = "Retorna todos los estudiantes")
	@GetMapping(value = "/obtenerPaginado/{page}/{size}" ,produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Estudiante.class)})	
	public ResponseEntity<?> retonarPaginado(@PathVariable int page, @PathVariable int size) {
		Page<Estudiante> listaEstudiante = service.retornarPaginado(page, size);
		return new ResponseEntity<Page<Estudiante>>(listaEstudiante, HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Buscar estudiante", notes = "Retorna estudiante por ID")
	@GetMapping(value = "obtener/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = Estudiante.class ),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el estudiante") })	
	public ResponseEntity<?> retornarEstudiante(@ApiParam(name = "id", type = "Int") @PathVariable("id") int id ) throws ModelNotFoundException, Exception {
		Estudiante estudiante = service.retonarPorId(id);
		
		estudiante.add(
				linkTo(methodOn(EstudianteController.class).retornarEstudiante(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(EstudianteController.class).editar(estudiante)).withRel("update").withType("PUT"),
				linkTo(methodOn(EstudianteController.class).eliminar(id)).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(estudiante, HttpStatus.OK);
	}

	@ApiOperation(value = "Insertar estudiante", notes = "Crea un nuevo estudiante")
	@PostMapping(value = "/insertar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. El estudiante se creo correctamente"),
            @ApiResponse(code = 409, message = "Conflict. El correo ya esta registrado") })
	public ResponseEntity<?> guardar (@Valid @RequestBody Estudiante estudiante) throws IntegridadException, Exception {
		service.guardar(estudiante);
		
		estudiante.add(
				linkTo(methodOn(EstudianteController.class).retornarEstudiante(estudiante.getId())).withSelfRel().withType("GET"),
				linkTo(methodOn(EstudianteController.class).editar(estudiante)).withRel("update").withType("PUT"),
				linkTo(methodOn(EstudianteController.class).eliminar(estudiante.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(estudiante, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Editar estudiante", notes = "Edita un estudiante")
	@PutMapping(value = "/editar", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Ok. Se edito el estudiante correctamente"),
            @ApiResponse(code = 404, message = "Not Found. No se encontro el estudiante"),
            @ApiResponse(code = 409, message = "Conflict. El correo ya esta registrado")})	
	public ResponseEntity<?> editar (@Valid @RequestBody Estudiante estudiante) throws ModelNotFoundException, IntegridadException, Exception {
		service.editar(estudiante);
		
		estudiante.add(
				linkTo(methodOn(EstudianteController.class).retornarEstudiante(estudiante.getId())).withRel("get").withType("GET"),
				linkTo(methodOn(EstudianteController.class).eliminar(estudiante.getId())).withRel("delete").withType("DELETE"));
		
		return new ResponseEntity<Object>(estudiante, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Eliminar estudiante", notes = "Elimina un estudiante por ID")
	@DeleteMapping (value = "/eliminar/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content. Se borró el estudiante correctamente"),
            @ApiResponse(code = 404, message = "No se encontro el estudiante") })	
	public ResponseEntity<?> eliminar(@ApiParam(name = "id", type = "Int") @PathVariable int id) throws ModelNotFoundException, IntegridadException, Exception {
		service.eliminar(id);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
