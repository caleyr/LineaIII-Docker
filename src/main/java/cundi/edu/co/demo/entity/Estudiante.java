package cundi.edu.co.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "estudiante")
@ApiModel("Modelo estudiante")
@NamedQueries({
	@NamedQuery(name = "Estudiante.buscarCedulas", query = "SELECT COUNT(m) FROM Estudiante m WHERE m.cedula = :cedula"),	
})
public class Estudiante extends RepresentationModel<Estudiante> implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(dataType = "String", value = "Id del estudiante", example = "1")
	private Integer id;
	
	@NotNull(message = "Cedula es obligatorio")
	@Size(min = 7, max = 12, message = "El Cedula debe estar entre 7 y 12 caracteres")
	@ApiModelProperty(required = true, dataType = "String", value = "Cedula de minimo 7 y maximo 12 caracteres", example = "100109012", allowableValues = "range[7,12]")
	@Column(name = "cedula", length = 12, nullable = false, unique = true)
	private String cedula;	
	
	@NotNull(message = "Nombre es obligatorio")
	@Size(min = 3, max = 15, message = "El nombre debe estar entre 3 y 15 caracteres")
	@ApiModelProperty(required = true, dataType = "String", value = "Nombre de minimo 3 y maximo 15 caracteres", example = "Carlos", allowableValues = "range[3,15]")
	@Column(name = "nombre", length = 15, nullable = false)
	private String nombre;
	
	@NotNull(message = "Apellido es obligatorio")
	@Size(min = 3, max = 15, message = "El apellido debe estar entre 3 y 15 caracteres")	
	@ApiModelProperty(required = true, dataType = "String", value = "Apellido de minimo 3 y maximo 15 caracteres", example = "Ortiz", allowableValues = "range[3,15]")
	@Column(name = "apellido", length = 15, nullable = false)
	private String apellido;
	
	@NotNull(message = "Correo es obligatorio")
	@Email(message = "Correo incorrecto")
	@ApiModelProperty(required = true, dataType = "String", value = "Correo con @ y dirección valida", example = "nickolasperalta@hotmail.com")
	@Column(name = "correo", length = 60, nullable = false, unique = true)
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

	public Estudiante(Integer id, String nombre, String apellido, String correo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
	}

	public Estudiante() {
		super();
	}
	

}
