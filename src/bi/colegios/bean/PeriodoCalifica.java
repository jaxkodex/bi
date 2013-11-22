package bi.colegios.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table(name="periodo_califica")
public class PeriodoCalifica implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_percalifica")
	private String id;
	@ManyToOne
	@JoinColumn(name="id_pacademico")
	private PeriodoAcademico periodoAcademico;
	@Column(name="percal_desc")
	private String descripcion;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PeriodoAcademico getPeriodoAcademico() {
		return periodoAcademico;
	}
	public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
		this.periodoAcademico = periodoAcademico;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
