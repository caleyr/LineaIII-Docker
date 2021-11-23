package cundi.edu.co.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;

import javax.persistence.ForeignKey;

@Entity
@Table(name = "usuario")
@ApiModel("Modelo usuario")
public class Usuario extends RepresentationModel<Usuario> implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;		

	@Column(name = "documento", nullable = false, length = 30, unique = true)
	private String documento;

	@Column(name = "nombre", nullable = false, length = 25)
	private String nombre;

	@Column(name = "apellido", nullable = false, length = 25)
	private String apellido;
	
	@Column(name = "nick", nullable = false, unique = true)
	private String nick;

	@Column(columnDefinition="TEXT", name = "clave", nullable = false)
	private String clave;

	@ManyToOne
	@JoinColumn(name = "idRol", foreignKey = @ForeignKey(name = "FK_rol"))
	private Rol rol;

	public Usuario() {
		
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
}