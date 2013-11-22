package bi.colegios.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Rol;
import bi.colegios.bean.Usuario;

@Repository
@Transactional
public class UsuarioDao {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	
	public Usuario loadByUsername (String username) {
		Usuario usuario = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		usuario = (Usuario) session.get(Usuario.class, String.format("%-12s", username));
		session.getTransaction().commit();
		return usuario;
	}
	
	public Rol loadRolById (String rolId) {
		Rol rol = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		rol = (Rol) session.get(Rol.class, String.format("%-12s", rolId));
		session.getTransaction().commit();
		return rol;
	}
	
	public int countUsuarios () {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Usuario.class);
		Number userCount = (Number) c.setProjection(Projections.rowCount()).uniqueResult();
		session.beginTransaction();
		session.getTransaction().commit();
		return userCount.intValue();
	}
	
	public List<Usuario> listUsuariosPaginado (int first, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Usuario.class);
		c.setMaxResults(pageSize)
			.setFetchSize(pageSize)
			.setFirstResult(first);
		List<Usuario> usuarios = c.list();
		session.getTransaction().commit();
		return usuarios;
	}
	
	public boolean nuevoUsuario (Usuario usuario) {
		if (loadByUsername(usuario.getUsername()) != null) {
			return false;
		}
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String pwd = md5.encodePassword(usuario.getPassword(), null);
		usuario.setPassword(pwd);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(usuario);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean nuevoRol (Rol rol) {
		if (loadRolById(rol.getId()) != null) {
			return false;
		}
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(rol);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean eliminarUsuario (String username) {
		Usuario usuario = loadByUsername(username);
		if (usuario == null) return false;
		//usuario.setEstado('B'); // Baja
		return true;
	}

	public List<Usuario> listAllUsuario() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Usuario.class);
		List<Usuario> usuarios = c.list();
		session.getTransaction().commit();
		return usuarios;
	}

	public List<Rol> listAllRoles() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Rol> roles = session.createCriteria(Rol.class)
				.list();
		session.getTransaction().commit();
		return roles;
	}
}