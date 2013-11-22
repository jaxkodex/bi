package bi.colegios.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Persona;

@Repository
@Transactional
public class PersonaDao {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	
	public Persona loadPersonaById (Integer idPersona) {
		Persona persona = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		persona = (Persona) session.get(Persona.class, idPersona);
		session.getTransaction().commit();
		return persona;
	}
	
	public Persona loadPersonaByDni (String dniPersona) {
		Persona persona = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		persona = (Persona) session.createCriteria(Persona.class)
				.add(Restrictions.eq("dni", dniPersona))
				.setMaxResults(1)
				.setFetchSize(1)
				.setFirstResult(0)
				.uniqueResult();
		session.getTransaction().commit();
		return persona;
	}
	
	public boolean nuevaPersona (Persona persona) {
		if (loadPersonaByDni(persona.getDni()) != null) {
			return false;
		}
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(persona);
		session.getTransaction().commit();
		return true;
	}
}