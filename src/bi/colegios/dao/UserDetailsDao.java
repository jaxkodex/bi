package bi.colegios.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Rol;
import bi.colegios.bean.Usuario;

@Repository
@Transactional
public class UserDetailsDao implements UserDetailsService {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Usuario usuario = null;
		Rol rol = new Rol();
		rol.setId("ROLE_USER");
		rol.setDescripcion("ROLE_USER");
		
		if (username.equals("super_admin")) {
			// Usuario super administrador
			usuario = new Usuario();
			
			usuario.setUsername(username);
			usuario.setPassword("fe01ce2a7fbac8fafaed7c982a04e229");
			usuario.getRoles().add(rol);
			
			return usuario;
		}
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		usuario = (Usuario) session.get(Usuario.class, username);
		session.getTransaction().commit();
		
		// Hack for enable user access since it does not
		// load roles (dont know why)
		if (usuario.getRoles().size() == 0) {
			System.out.println("Hacking the UserDetail to enable access");
			usuario.getRoles().add(rol);
		}
		return usuario;
	}

}
