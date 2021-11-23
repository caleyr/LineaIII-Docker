package cundi.edu.co.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cundi.edu.co.demo.entity.Autor;

@Repository
public interface IAutorRepo extends JpaRepository<Autor, Integer>{
	
	public Autor findByCedula(String cedula);
	
	public Autor findByCorreo(String correo);
	 
	public Boolean existsByCedula(String cedula);
	
	public Boolean existsByCorreo(String correo);
	
	public int buscarCedulas(String cedula);
	
	@Query(value = "SELECT m FROM Autor m WHERE NOT id = :id AND correo = :correo", nativeQuery = true)
	public Autor existeCorreo(@Param("id")Integer id, @Param("correo")String correo);

}
