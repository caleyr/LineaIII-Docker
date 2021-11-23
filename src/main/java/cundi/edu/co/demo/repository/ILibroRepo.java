package cundi.edu.co.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cundi.edu.co.demo.entity.Libro;

@Repository
public interface ILibroRepo extends JpaRepository<Libro, Integer>{
	
	@Query(value = "SELECT m FROM Libro m WHERE nombre = :nombre AND id_autor = :autor", nativeQuery = true)
	public Libro existsByNombre(@Param("nombre")String nombre, @Param("autor")Integer autor);

	
}
