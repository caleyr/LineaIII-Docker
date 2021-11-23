package cundi.edu.co.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cundi.edu.co.demo.entity.Autor;
import cundi.edu.co.demo.entity.Usuario;

@Repository
public interface IUsuarioRepo  extends JpaRepository<Usuario, Integer> {

	Usuario findOneByNick(String nick);
	
	@Query(value = "SELECT m FROM Usuario m WHERE NOT id_usuario = :id AND nick = :nick", nativeQuery = true)
	public Usuario existeNick(@Param("id")Integer id, @Param("nick")String nick);
}