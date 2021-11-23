package cundi.edu.co.demo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cundi.edu.co.demo.entity.Autor;
import cundi.edu.co.demo.entity.Libro;
import cundi.edu.co.demo.entity.Usuario;
import cundi.edu.co.demo.exception.ArgumentRequiredException;
import cundi.edu.co.demo.exception.IntegridadException;
import cundi.edu.co.demo.exception.ModelNotFoundException;
import cundi.edu.co.demo.repository.IUsuarioRepo;
import cundi.edu.co.demo.service.IUsuarioService;
import org.springframework.security.core.GrantedAuthority;

@Service
public class IUsuarioServiceImp implements IUsuarioService, UserDetailsService{
	
	@Autowired
	private IUsuarioRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	private Boolean validarExistenciaPorId(int idUsuario) {
		return repo.existsById(idUsuario);
	}	

	@Override
	public Page<Usuario> retornarPaginado(int page, int size) {
		Page<Usuario> resultado = repo.findAll(PageRequest.of(page,size));
		return resultado;
	}

	@Override
	public Usuario retonarPorId(Integer id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id)) {		
			Usuario usuario = this.repo.getById(id);
			return usuario;
 		} else
			throw new ModelNotFoundException("Usuario no encontrado");	
	}

	@Override
	public void guardar(Usuario usuario) throws IntegridadException {
		if (repo.findOneByNick(usuario.getNick()) != null) {
			throw new IntegridadException("Usuario ya existe");
		}
		usuario.setClave(bcrypt.encode(usuario.getClave()));
		this.repo.save(usuario);
		
	}

	@Override
	public void editar(Usuario usuario) throws ArgumentRequiredException, ModelNotFoundException, IntegridadException {
		if(usuario.getIdUsuario() != null) {
			if(validarExistenciaPorId(usuario.getIdUsuario())) {
				
				usuario.setClave(bcrypt.encode(usuario.getClave()));
				
				if(repo.existeNick(usuario.getIdUsuario(), usuario.getNick()) == null)
					this.repo.save(usuario);
				else 
					throw new IntegridadException("Usuario ya existe");				
				
			} else 
				throw new ModelNotFoundException("Usuario no encontrado");		
		} else {
			throw new ArgumentRequiredException("IdUsuario es requerido");
		}
	}

	@Override
	public void eliminar(int id) throws ModelNotFoundException {
		if(validarExistenciaPorId(id))
			this.repo.deleteById(id);
		else
			throw new ModelNotFoundException("Usuario no encontrado");	
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repo.findOneByNick(username);		
		if(usuario == null)
			new UsernameNotFoundException("----Usuario no encontrado");
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));
		
		UserDetails ud = new User(usuario.getNick(), usuario.getClave(), roles);
		return ud;
	}

}
