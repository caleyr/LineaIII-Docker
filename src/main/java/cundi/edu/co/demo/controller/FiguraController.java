package cundi.edu.co.demo.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cundi.edu.co.demo.service.IPerimetroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/figura")
@Validated
@Api(value = "Servicio figura", description = "Esta API tiene servicios para calcular perimetros")
public class FiguraController {

	@Autowired
	@Qualifier("triangulo")
	private IPerimetroService service;

	@ApiOperation(value = "Hallar el perimetro" , notes = "Retorna el perimetro de la figura")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Se hallo el perimetro satisfactoriamente")})
	@GetMapping(value = "/perimetro/{lado}", produces = "application/json")
	public ResponseEntity<?> perimetroFigura(@ApiParam(name = "lado", type = "Int", value = "Longitud del lado", example = "1", required = true, allowableValues = "range[1,10]") @PathVariable("lado") @Min(1) @Max(10) int lado ) {
		int perimetro = service.obtener(lado);
		return new ResponseEntity<Object>(perimetro, HttpStatus.OK);
	}
}
