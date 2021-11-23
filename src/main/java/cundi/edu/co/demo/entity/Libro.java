package cundi.edu.co.demo.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "libro")
@ApiModel("Modelo libro")
public class Libro extends RepresentationModel<Libro> implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(dataType = "String", value = "Id del estudiante", example = "1")
	private Integer id;
	
	@NotNull(message = "Nombre es obligatorio")
	@Size(min = 3, max = 40 , message = "El nombre debe estar entre 3 y 40 caracteres")
	@ApiModelProperty(required = true, dataType = "String", value = "Nombre de minimo 3 y maximo 15 caracteres", example = "Alicia en el pais de las maravillas", allowableValues = "range[3,40]")
	@Column(name = "nombre", length = 40, nullable = false)
	private String nombre;
	
	@NotNull(message = "Descripción es obligatoria")
	@Size(min = 3, max = 15, message = "El Descripción debe estar entre 3 y 30 caracteres")
	@ApiModelProperty(required = true, dataType = "String", value = "Descripción de minimo 3 y maximo 60 caracteres", example = "Trata sobre...", allowableValues = "range[3,60]")
	@Column(name = "descripcion", columnDefinition="TEXT", length = 60, nullable = false)
	private String descripcion;

	@NotNull(message = "NumPaginas es obligatorio")
	@ApiModelProperty(required = true, dataType = "int", value = "NumPaginas de minimo 3 y maximo 500", example = "Trata sobre...", allowableValues = "range[3,500]")
	@Column(name = "numPaginas", nullable = false)
	private Integer numPaginas;
	
	@Column(name = "fechaPublicacion", updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fechaPublicacion;
	
	@ManyToOne
	@JoinColumn(name = "id_autor",nullable = false, foreignKey = @ForeignKey(name = "FK_Autor_Libro"))
	private Autor autorId;

	@JsonIgnore
	public Autor getAutorId() {
		return autorId;
	}

	public void setAutorId(Autor autorId) {
		this.autorId = autorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(Integer numPaginas) {
		this.numPaginas = numPaginas;
	}

	public Calendar getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Calendar fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Libro(Integer id, String nombre, String descripcion, Integer numPaginas, Calendar fechaPublicacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.numPaginas = numPaginas;
		this.fechaPublicacion = fechaPublicacion;
	}
	
	public Libro() {
		super();
	}
}
