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
@Table(name="matricula")
public class Matricula implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_matricula")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_estudiante")
	private Estudiante estudiante;
	@ManyToOne
	@JoinColumn(name="id_ie")
	private InstitucionEducativa institucionEducativa;
	@ManyToOne
	@JoinColumn(name="id_pacademico")
	private PeriodoAcademico periodoAcademico;
	@Column(name="matr_fecregistro")
	private Date fechaRegistro;
	
	public Matricula() {
		this.id = null;
		this.estudiante = new Estudiante();
		this.institucionEducativa = new InstitucionEducativa();
		this.periodoAcademico = new PeriodoAcademico();
		this.fechaRegistro = new Date();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Estudiante getEstudiante() {
		return estudiante;
	}
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	public InstitucionEducativa getInstitucionEducativa() {
		return institucionEducativa;
	}
	public void setInstitucionEducativa(InstitucionEducativa institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}
	public PeriodoAcademico getPeriodoAcademico() {
		return periodoAcademico;
	}
	public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
		this.periodoAcademico = periodoAcademico;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "Matricula [estudiante=" + estudiante + "]";
	}
	
}
