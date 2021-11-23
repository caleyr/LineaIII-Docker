 package cundi.edu.co.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "editorial")
@ApiModel("Modelo editorial")
public class Editorial extends RepresentationModel<Editorial> implements Serializable{ 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Nit es obligatorio")
	@Size(min = 3, max = 15, message = "El nit debe estar entre 3 y 15 caracteres")
	@ApiModelProperty(required = true, dataType = "String", value = "Nit de minimo 3 y maximo 15 caracteres", example = "12039123", allowableValues = "range[3,15]")
	@Column(name = "nit", length = 12, nullable = false, unique = true)
	private String nit;
	
	@NotNull(message = "Nombre es obligatorio")
	@Size(min = 3, max = 15, message = "El nombre debe estar entre 3 y 15 caracteres")
	@ApiModelProperty(required = true, dataType = "String", value = "Nombre de minimo 3 y maximo 15 caracteres", example = "Carlos", allowableValues = "range[3,15]")
	@Column(name = "nombre", length = 15, nullable = false)
	private String nombre;
	
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

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Editorial(Integer id, String nit, String nombre, String correo) {
		super();
		this.id = id;
		this.nit = nit;
		this.nombre = nombre;
		this.correo = correo;
	}
	
	public Editorial() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editorial other = (Editorial) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
