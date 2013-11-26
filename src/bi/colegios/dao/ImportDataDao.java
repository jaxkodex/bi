package bi.colegios.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.ACargo;
import bi.colegios.bean.Area;
import bi.colegios.bean.Calificacion;
import bi.colegios.bean.Cargo;
import bi.colegios.bean.Consideraciones;
import bi.colegios.bean.Dcn;
import bi.colegios.bean.Desempenia;
import bi.colegios.bean.Estudiante;
import bi.colegios.bean.Grado;
import bi.colegios.bean.InstitucionEducativa;
import bi.colegios.bean.Matricula;
import bi.colegios.bean.Nivel;
import bi.colegios.bean.OfertaGrado;
import bi.colegios.bean.PeriodoAcademico;
import bi.colegios.bean.PeriodoCalifica;
import bi.colegios.bean.Persona;

@Transactional
@Repository
public class ImportDataDao {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	
	private InstitucionEducativa institucionEducativa;
	private PeriodoAcademico periodoAcademico;
	private Dcn dcn;
	private Map<String, List<Calificacion>> calificaciones;
	
	private Map<String, Map<String, Map<String, List<Map<String, String>>>>> estructura;
			//  NIVEL,      GRADO,      AREA,   CONSIDERACIONES=[(ID, DESC)]
	private Map<String, Persona> personas; // Index de personas ya registradas
	private Map<String, Estudiante> estudiantes;
	private Map<String, OfertaGrado> ofertaGrados;
	private Map<String, Matricula> matriculas;
	private Map<String, ACargo> aCargos;
	private Map<String, Cargo> cargos;
	private Map<String, Desempenia> desempenian;
	
	private Map<String, Nivel> niveles;
	private Map<String, Grado> grados;
	
	public void registrarAll (Map<String, List<Calificacion>> calificaciones, 
			PeriodoAcademico periodoAcademico,
			InstitucionEducativa institucionEducativa,
			Dcn dcn) {
		this.calificaciones = calificaciones;
		this.dcn = dcn;
		this.periodoAcademico = periodoAcademico;
		this.institucionEducativa = institucionEducativa;
		startProcess();
		/*
		for (Calificacion calificacion : this.calificaciones) {
			guardaCalificacion(calificacion);
		}
		*/
	}
	
	private void startProcess () {
		personas = new HashMap<>();
		estudiantes = new HashMap<>();
		ofertaGrados = new HashMap<>();
		matriculas = new HashMap<>();
		aCargos = new HashMap<>();
		cargos = new HashMap<>();
		desempenian = new HashMap<>();
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(this.periodoAcademico);
		for (String key : this.calificaciones.keySet()) {
			List<Calificacion> calificacionesList = this.calificaciones.get(key);
			for (Calificacion c : calificacionesList) {
				c.getPeriodoCalifica().setPeriodoAcademico(this.periodoAcademico);
				String periodoCalificaId = c.getPeriodoCalifica().getId();
				c.getPeriodoCalifica().setId(this.periodoAcademico.getId()+
						periodoCalificaId.substring(Math.max(periodoCalificaId.length()-2, 0)));
				guardaCalificacion(c, session);
			}
		}
		session.getTransaction().commit();
	}
	
	private void guardaMetaData () {
		Map<String, Nivel> niveles = new HashMap<String, Nivel>();
		Map<String, Grado> grados = new HashMap<>();
		Map<String, Area> areas = new HashMap<>(); 
		Map<String, Consideraciones> consideraciones = new HashMap<>();
		Map<String, PeriodoAcademico> periodosAcademicos = new HashMap<>();
		Map<String, PeriodoCalifica> periodosCalifica = new HashMap<>();
		
		// Guardar Niveles, Grados y Areas
		for (String key : this.calificaciones.keySet()) {
			List<Calificacion> calificacionesList = this.calificaciones.get(key);
			for (Calificacion c : calificacionesList) {
				Nivel nivel = c.getaCargo().getArea().getGrado().getNivel();
				Grado grado = c.getaCargo().getArea().getGrado();
				Area area = c.getaCargo().getArea();
				Consideraciones consideracion = c.getConsideracion();
				
				niveles.put(nivel.getId(), nivel);
				grados.put(grado.getId(), grado);
				areas.put(area.getId(), area);
				
				consideracion.setArea(area);
			}
		}
		
		for (Map.Entry<String, Nivel> entry : niveles.entrySet()) {
			Nivel n = entry.getValue();
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(n);
			session.getTransaction().commit();
		}
	}
	
	private void guardaCalificacion (Calificacion calificacion, Session session) {
		
		calificacion.getaCargo().getArea().getGrado().getNivel().setDcn(dcn);

		session.merge(calificacion.getaCargo().getArea().getGrado().getNivel());
		session.merge(calificacion.getaCargo().getArea().getGrado());
		session.merge(calificacion.getaCargo().getArea());
		
		String consideracionId = calificacion.getConsideracion().getId();
		
		calificacion.getConsideracion().setArea(calificacion.getaCargo().getArea());
		calificacion.getConsideracion().setId(calificacion.getaCargo().getArea().getId()+
				calificacion.getPeriodoCalifica().getId()+
				consideracionId.substring(Math.max(consideracionId.length()-2, 0)));
		
		session.merge(calificacion.getConsideracion());
		
		calificacion.getPeriodoCalifica().setPeriodoAcademico(this.periodoAcademico);
		session.merge(calificacion.getPeriodoCalifica());
		
		OfertaGrado ofertaGrado = getOfertaGrado(session, 
				calificacion.getaCargo().getArea().getGrado(), 
				periodoAcademico, 
				institucionEducativa,
				calificacion.getMatricula().getOfertaGrado().getSeccion());
		
		Estudiante estudiante = getEstudiante(session, calificacion.getMatricula().getEstudiante());
		
		calificacion.setMatricula(getMatricula(session, estudiante, ofertaGrado));
		
		calificacion.setaCargo(getaCargo(session, ofertaGrado, 
				calificacion.getaCargo().getArea(), 
				calificacion.getaCargo().getDesempenia()));
	}
	
	private OfertaGrado getOfertaGrado (Session session, Grado grado, 
			PeriodoAcademico periodoAcademico, InstitucionEducativa institucionEducativa,
			String seccion) {
		OfertaGrado ofertaGrado = ofertaGrados.get(grado.getId()+
				periodoAcademico.getId()+
				institucionEducativa.getId()+
				seccion);
		
		if (ofertaGrado != null) {
			return ofertaGrado;
		}
		
		Criteria c = session.createCriteria(OfertaGrado.class);
		c.createCriteria("grado").add(Restrictions.eq("id", grado.getId()));
		c.createCriteria("periodoAcademico").add(Restrictions.eq("id", periodoAcademico.getId()));
		c.createCriteria("institucionEducativa").add(Restrictions.eq("id", institucionEducativa.getId()));
		c.add(Restrictions.eq("seccion", seccion));
		c.setMaxResults(1).setFetchSize(1).setFirstResult(0);
		
		ofertaGrado = (OfertaGrado) c.uniqueResult();
		if (ofertaGrado == null) {
			ofertaGrado = new OfertaGrado();
			ofertaGrado.setGrado(grado);
			ofertaGrado.setPeriodoAcademico(periodoAcademico);
			ofertaGrado.setInstitucionEducativa(institucionEducativa);
			ofertaGrado.setSeccion(seccion);
			ofertaGrado.setFecha(new Date());
			
			session.saveOrUpdate(ofertaGrado);
		}
		ofertaGrados.put(grado.getId()+
				periodoAcademico.getId()+
				institucionEducativa.getId()+
				seccion, ofertaGrado);
		return ofertaGrado;
	}
	
	private Estudiante getEstudiante (Session session, Estudiante estudiante) {
		Estudiante est = estudiantes.get(estudiante.getCodigo());
		
		if (est != null) {
			return est;
		}
		
		Criteria c = session.createCriteria(Estudiante.class);
		c.add(Restrictions.eq("codigo", estudiante.getCodigo()));
		c.setMaxResults(1).setFetchSize(1).setFirstResult(0);
		est = (Estudiante) c.uniqueResult();
		if (est == null) {
			Persona persona = getPersona(session, estudiante.getPersona());
			estudiante.setPersona(persona);
			session.saveOrUpdate(estudiante);
			
			estudiantes.put(estudiante.getCodigo(), estudiante);
			
			return estudiante;
		}
		estudiantes.put(est.getCodigo(), est);
		return est;
	}
	
	private Persona getPersona (Session session, Persona persona) {
		Persona per = personas.get(persona.getApellidos()+persona.getNombres()+persona.getGenero());
		
		if (per != null) {
			return per;
		}
		
		Criteria c = session.createCriteria(Persona.class);
		c.add(Restrictions.eq("apellidos", persona.getApellidos()));
		c.add(Restrictions.eq("nombres", persona.getNombres()));
		c.add(Restrictions.eq("genero", persona.getGenero()));
		c.setMaxResults(1).setFetchSize(1).setFirstResult(0);
		per = (Persona) c.uniqueResult();
		
		if (per == null) {
			persona.setInstitucionEducativa(institucionEducativa);
			session.saveOrUpdate(persona);
			
			personas.put(persona.getApellidos()+persona.getNombres()+persona.getGenero(), persona);
			
			return persona;
		}
		personas.put(per.getApellidos()+per.getNombres()+per.getGenero(), per);
		return per;
	}
	
	private Matricula getMatricula (Session session, Estudiante estudiante, 
			OfertaGrado ofertaGrado) {
		Matricula m = matriculas.get(""+estudiante.getId()+""+ofertaGrado.getId());
		
		if (m != null) {
			return m;
		}
		
		Criteria c = session.createCriteria(Matricula.class);
		c.createCriteria("estudiante").add(Restrictions.eq("id", estudiante.getId()));
		c.createCriteria("ofertaGrado").add(Restrictions.eq("id", ofertaGrado.getId()));
		c.setMaxResults(1).setFetchSize(1).setFirstResult(0);
		m = (Matricula) c.uniqueResult();
		
		if (m == null) {
			m = new Matricula();
			m.setEstudiante(estudiante);
			m.setOfertaGrado(ofertaGrado);
			//m.setPeriodoAcademico(periodoAcademico);
			session.saveOrUpdate(m);
		}
		matriculas.put(""+estudiante.getId()+""+ofertaGrado.getId(), m);
		return m;
	}
	
	private ACargo getaCargo (Session session, OfertaGrado ofertaGrado, Area area, Desempenia desempenia) {
		ACargo aCargo = aCargos.get(ofertaGrado.getId()+area.getId());
		if (aCargo != null) {
			return aCargo;
		}
		
		Criteria c = session.createCriteria(ACargo.class);
		c.createCriteria("ofertaGrado").add(Restrictions.eq("id", ofertaGrado.getId()));
		c.createCriteria("area").add(Restrictions.eq("id", area.getId()));
		c.setMaxResults(1).setFetchSize(1).setFirstResult(0);
		aCargo = (ACargo) c.uniqueResult();
		
		if (aCargo != null) {
			aCargo.setDesempenia(getDesempenia(session, desempenia));
			session.merge(aCargo);
		}
		
		aCargos.put(ofertaGrado.getId()+area.getId(), aCargo);
		
		return aCargo;
	}
	
	private Desempenia getDesempenia (Session session, Desempenia desempenia) {
		Persona persona =getPersona(session, desempenia.getPersona());
		desempenia.setPersona(persona);
		Desempenia tmpDesempenia = desempenian.get(periodoAcademico.getId()+
				desempenia.getCargo().getId()+persona.getId());
		
		if (tmpDesempenia != null) {
			return tmpDesempenia;
		}
		
		Criteria c = session.createCriteria(Desempenia.class);
		c.createCriteria("persona").add(Restrictions.eq("id", persona.getId()));
		c.createCriteria("cargo").add(Restrictions.eq("id", desempenia.getCargo().getId()));
		c.createCriteria("periodoAcademico").add(Restrictions.eq("id", periodoAcademico.getId()));
		c.setMaxResults(1).setFetchSize(1).setFirstResult(0);
		tmpDesempenia = (Desempenia) c.uniqueResult();
		
		if (tmpDesempenia == null) {
			desempenia.setCargo(getCargo(session, "DOCENTE"));
			desempenia.setPersona(getPersona(session, desempenia.getPersona()));
			session.saveOrUpdate(desempenia);
			return desempenia;
		}
		
		return tmpDesempenia;
	}

	private Cargo getCargo(Session session, String idCargo) {
		Cargo cargo = cargos.get(idCargo);
		
		if (cargo != null) {
			return cargo;
		}
		
		cargo = (Cargo) session.get(Cargo.class, idCargo);
		
		if (cargo == null) {
			cargo = new Cargo ();
			cargo.setId(idCargo);
			cargo.setDescripcion(idCargo);
			
			session.saveOrUpdate(cargo);
		}
		
		return cargo;
	}
}
