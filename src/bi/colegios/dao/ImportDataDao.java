package bi.colegios.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bi.colegios.bean.Area;
import bi.colegios.bean.Calificacion;
import bi.colegios.bean.Consideraciones;
import bi.colegios.bean.Dcn;
import bi.colegios.bean.Grado;
import bi.colegios.bean.InstitucionEducativa;
import bi.colegios.bean.Nivel;
import bi.colegios.bean.PeriodoAcademico;
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
	private Map<Integer, Persona> personas; // Index de personas ya registradas
	
	private Map<String, Nivel> niveles;
	private Map<String, Grado> grados;
	
	public void registrarAll (Map<String, List<Calificacion>> calificaciones, 
			PeriodoAcademico periodoAcademico,
			InstitucionEducativa institucionEducativa,
			Dcn dcn) {
		this.calificaciones = calificaciones;
		/*
		for (Calificacion calificacion : this.calificaciones) {
			guardaCalificacion(calificacion);
		}
		*/
	}
	
	private void guardaCalificacion (Calificacion calificacion) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(calificacion.getConsideracion().getArea().getGrado().getNivel());
		session.saveOrUpdate(calificacion.getConsideracion().getArea().getGrado());
		session.saveOrUpdate(calificacion.getConsideracion().getArea());
		session.getTransaction().commit();
	}
}
