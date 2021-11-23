package cundi.edu.co.demo.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

public class AutorDto extends RepresentationModel<AutorDto> implements Serializable{
	private Integer id;
	
	private String cedula;	
	
	private String nombre;
	
	private String apellido;
	
	private String correo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public AutorDto(Integer id, String cedula, String nombre, String apellido, String correo) {
		super();
		this.id = id;
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
	}

	public AutorDto() {
		super();
	}
	
}
