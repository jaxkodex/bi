package bi.colegios.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Cargo;
import bi.colegios.bean.Desempenia;
import bi.colegios.bean.InstitucionEducativa;
import bi.colegios.bean.OfertaGrado;

@Repository
@Transactional
public class InstitucionEducativaDao {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	
	public InstitucionEducativa loadById (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		InstitucionEducativa ie = (InstitucionEducativa) session.get(InstitucionEducativa.class, id);
		session.getTransaction().commit();
		return ie;
	}
	
	public InstitucionEducativa loadByCodigoModular (String codigoModular) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		InstitucionEducativa ie = null;
		Criteria c = session.createCriteria(InstitucionEducativa.class);
		c.add(Restrictions.eq("codigoModular", codigoModular));
		c.setMaxResults(1).setFetchSize(1).setFirstResult(0);
		ie = (InstitucionEducativa) c.uniqueResult();
		session.getTransaction().commit();
		return ie;
	}
	
	public Cargo loadCargoById (String idCargo) {
		if (idCargo == null) return null;
		Cargo cargo = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		cargo = (Cargo) session.get(Cargo.class, idCargo);
		session.getTransaction().commit();
		return cargo;
	}
	
	public boolean nuevaInstitucioneducativa (InstitucionEducativa ie) {
		if (loadByCodigoModular(ie.getCodigoModular()) != null)
			return false;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(ie);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean nuevaOfertaGrado (OfertaGrado ofertaGrado) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(ofertaGrado);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean nuevoCargo (Cargo cargo) {
		if (cargo == null || loadCargoById(cargo.getId()) != null) {
			return false;
		}
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(cargo);
		session.getTransaction().commit();
		return true;
	}
	
	public List<InstitucionEducativa> listAllInstitucionEducativa () {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<InstitucionEducativa> ies = session.createCriteria(InstitucionEducativa.class)
				.list();
		session.getTransaction().commit();
		return ies;
	}
	
	public List<InstitucionEducativa> listInstitucionEducativaPaginado (int first, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(InstitucionEducativa.class);
		c.setFirstResult(first).setMaxResults(pageSize).setFetchSize(pageSize);
		List<InstitucionEducativa> ies = c.list();
		session.getTransaction().commit();
		return ies;
	}
	
	public List<Cargo> listAllCargo () {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Cargo> cargos = session.createCriteria(Cargo.class)
				.list();
		session.getTransaction().commit();
		return cargos;
	}

	public List<InstitucionEducativa> search(String searchQuery) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(InstitucionEducativa.class);
		c.add(Restrictions.or(Restrictions.ilike("codigoModular", "%"+searchQuery+"%"),
				Restrictions.ilike("nombre", "%"+searchQuery+"%")));
		List<InstitucionEducativa> ies = c.list();
		session.getTransaction().commit();
		return ies;
	}

	public boolean nuevoDesempenia(Desempenia desempenia) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(desempenia);
		session.getTransaction().commit();
		return true;
	}
}
