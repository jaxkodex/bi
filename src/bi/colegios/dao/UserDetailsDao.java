package bi.colegios.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		usuario = (Usuario) session.get(Usuario.class, username);
		session.getTransaction().commit();
		return usuario;
	}

}
