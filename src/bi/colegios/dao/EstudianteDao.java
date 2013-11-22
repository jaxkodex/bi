package bi.colegios.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Estudiante;

@Repository
@Transactional
public class EstudianteDao {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;

	public Estudiante loadById (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Estudiante estudiante = (Estudiante) session.get(Estudiante.class, id);
		session.getTransaction().commit();
		return estudiante;
	}
	
	public Estudiante loadByCodigoEstudiante (String codigoEstudiante) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Estudiante estudiante = (Estudiante) session.createCriteria(Estudiante.class)
				.add(Restrictions.eq("codigo", codigoEstudiante))
				.setFetchSize(1)
				.setMaxResults(1)
				.setFirstResult(0)
				.uniqueResult();
		session.getTransaction().commit();
		return estudiante;
	}
	
	public List<Estudiante> listAllEstudiantes () {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Estudiante> estudiantes = session.createCriteria(Estudiante.class)
				.list();
		session.getTransaction().commit();
		return estudiantes;
	}
	
	public List<Estudiante> listEstudiantesPaginado (int first, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Estudiante> estudiantes = session.createCriteria(Estudiante.class)
				.setMaxResults(pageSize)
				.setFetchSize(pageSize)
				.setFirstResult(first)
				.list();
		session.getTransaction().commit();
		return estudiantes;
	}
}
