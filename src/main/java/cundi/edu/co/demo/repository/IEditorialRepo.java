package cundi.edu.co.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cundi.edu.co.demo.entity.Editorial;

@Repository
public interface IEditorialRepo extends JpaRepository<Editorial, Integer>{

	public Boolean existsByNit(String nit);
	
	public Boolean existsByCorreo(String correo);
	
	@Query(value = "SELECT m FROM Editorial m WHERE m.correo = :correo EXCEPT SELECT c FROM Editorial c WHERE c.correo = :correo", nativeQuery = true)
	public Editorial existeCorreo(String correo);
}
