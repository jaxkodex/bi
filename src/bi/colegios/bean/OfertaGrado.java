package bi.colegios.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table(name="oferta_grado")
public class OfertaGrado implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_oferta_grado")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_grado")
	private Grado grado;
	@ManyToOne
	@JoinColumn(name="id_pacademico")
	private PeriodoAcademico periodoAcademico;
	@ManyToOne
	@JoinColumn(name="id_ie")
	private InstitucionEducativa institucionEducativa;
	@Column(name="og_seccion")
	private String seccion;
	@Column(name="og_fecha")
	private Date fecha;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Grado getGrado() {
		return grado;
	}
	public void setGrado(Grado grado) {
		this.grado = grado;
	}
	public PeriodoAcademico getPeriodoAcademico() {
		return periodoAcademico;
	}
	public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
		this.periodoAcademico = periodoAcademico;
	}
	public InstitucionEducativa getInstitucionEducativa() {
		return institucionEducativa;
	}
	public void setInstitucionEducativa(InstitucionEducativa institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
