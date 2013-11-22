package bi.colegios.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Cargo;

@Transactional
@Repository
public class CargoDao {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	
	public Cargo loadById (String id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Cargo cargo = (Cargo) session.get(Cargo.class, id);
		session.getTransaction().commit();
		return cargo;
	}
	
	public List<Cargo> listAllCargos () {
		List<Cargo> cargos = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		cargos = session.createCriteria(Cargo.class)
				.list();
		session.getTransaction().commit();
		return cargos;
	}
	
	public List<Cargo> listCargosPaginado (int first, int pageSize) {
		List<Cargo> cargos = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		cargos = session.createCriteria(Cargo.class)
				.setMaxResults(pageSize)
				.setFetchSize(pageSize)
				.setFirstResult(first)
				.list();
		session.getTransaction().commit();
		return cargos;
	}
	
	public boolean nuevoCargo (Cargo cargo) {
		if (loadById(cargo.getId()) == null) {
			return false;
		}
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(cargo);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean eliminarCargo (Cargo cargo) {
		if (loadById(cargo.getId()) == null) {
			return false;
		}
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(cargo);
		session.getTransaction().commit();
		return true;
	}
}
