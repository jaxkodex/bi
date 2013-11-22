package bi.colegios.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Area;
import bi.colegios.bean.Ciclo;
import bi.colegios.bean.Dcn;
import bi.colegios.bean.Grado;
import bi.colegios.bean.Nivel;

@Transactional
@Repository
public class DcnDao {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	
	public Dcn loadDcnById (Integer id) {
		Dcn dcn = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		dcn = (Dcn) session.get(Dcn.class, id);
		session.getTransaction().commit();
		return dcn;
	}
	
	public boolean nuevaDcn (Dcn dcn) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(dcn);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean nuevoNivel (Nivel nivel) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(nivel);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean nuevoCiclo (Ciclo ciclo) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(ciclo);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean nuevoGrado (Grado grado) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(grado);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean nuevoArea (Area area) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(area);
		session.getTransaction().commit();
		return true;
	}

	public List<Dcn> listAllDcn() {
		List<Dcn> dcn = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		dcn = session.createCriteria(Dcn.class).list();
		session.getTransaction().commit();
		return dcn;
	}
	
	public List<Nivel> listAllNivelByDcn (Integer idDcn) {
		List<Nivel> niveles = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Nivel.class);
		c.createCriteria("dcn")
		.add(Restrictions.eq("id", idDcn));
		niveles = c.list();
		session.getTransaction().commit();
		return niveles;
	}
	
	public List<Ciclo> listAllCicloByNivel (Integer idNivel) {
		List<Ciclo> ciclos = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Ciclo.class);
		c.createCriteria("nivel")
		.add(Restrictions.eq("id", idNivel));
		ciclos = c.list();
		session.getTransaction().commit();
		return ciclos;
	}
	
	public List<Grado> listAllGradoByCiclo (Integer idCiclo) {
		List<Grado> grados = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Grado.class);
		c.createCriteria("ciclo")
		.add(Restrictions.eq("id", idCiclo));
		grados = c.list();
		session.getTransaction().commit();
		return grados;
	}
	
	public List<Area> listAllAreaByGrado (Integer idGrado) {
		List<Area> areas = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Area.class);
		c.createCriteria("grado")
		.add(Restrictions.eq("id", idGrado));
		areas = c.list();
		session.getTransaction().commit();
		return areas;
	}
	
	public List<Grado> listAllGradoByDcn (Integer idDcn) {
		List<Grado> grados = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Grado.class);
		c.createCriteria("ciclo").createCriteria("nivel").createCriteria("dcn")
		.add(Restrictions.eq("id", idDcn));
		grados = c.list();
		session.getTransaction().commit();
		return grados;
	}

	public List<Ciclo> listAllCicloByDcn(Integer idDcn) {
		List<Ciclo> ciclos = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Ciclo.class);
		Criteria cNivel = c.createCriteria("nivel");
		Criteria cDcn = cNivel.createCriteria("dcn");
		cDcn.add(Restrictions.eq("id", idDcn));
		ciclos = c.list();
		session.getTransaction().commit();
		return ciclos;
	}

	public Nivel loadNivelById(Integer idNivel) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Nivel nivel = (Nivel) session.get(Nivel.class, idNivel);
		session.getTransaction().commit();
		return nivel;
	}

	public Ciclo loadCicloById(Integer idCiclo) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Ciclo ciclo = (Ciclo) session.get(Ciclo.class, idCiclo);
		session.getTransaction().commit();
		return ciclo;
	}

	public Grado loadGradoById(Integer idGrado) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Grado grado = (Grado) session.get(Grado.class, idGrado);
		session.getTransaction().commit();
		return grado;
	}

	public List<Area> listAllAreaByDcn(Integer idDcn) {
		List<Area> areas = null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(Area.class);
		c.createCriteria("grado").addOrder(Order.desc("descripcion"))
		.createCriteria("ciclo").createCriteria("nivel").add(Restrictions.eq("id", idDcn));
		c.addOrder(Order.desc("descripcion"));
		areas = c.list();
		session.getTransaction().commit();
		return areas;
	}
}
