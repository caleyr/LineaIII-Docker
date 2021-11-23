package cundi.edu.co.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "autor_editorial")
@IdClass(AutorEditorialPK.class)
public class AutorEditorial extends RepresentationModel<Editorial> implements Serializable{

	@Id
	private Autor autor;
	
	@Id
	private Editorial editorial;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	public AutorEditorial() {
		super();
	}

	public AutorEditorial(Autor autor, Editorial editorial, LocalDate fecha) {
		super();
		this.autor = autor;
		this.editorial = editorial;
		this.fecha = fecha;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
}
