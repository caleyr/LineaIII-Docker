package cundi.edu.co.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cundi.edu.co.demo.entity.Estudiante;

@Repository
public interface IEstudianteRepo extends JpaRepository<Estudiante, Integer>{
	
	public Estudiante findByCedula(String cedula);
	
	public Estudiante findByCorreo(String correo);
	 
	public Boolean existsByCedula(String cedula);
	
	public Boolean existsByCorreo(String correo);
	
	public int buscarCedulas(String cedula);

	@Query(value = "SELECT m FROM Estudiante m WHERE m.correo = :correo EXCEPT SELECT c FROM Estudiante c WHERE c.correo = :correo", nativeQuery = true)
	public Estudiante existeCorreo(String correo);
}
