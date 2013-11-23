package bi.colegios.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="estudiante")
public class Estudiante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_estudiante")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_apoderado")
	private Apoderado apoderado;
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;
	@Column(name="est_codigo")
	private String codigo;
	@Column(name="est_fregistro")
	private Date fechaRegistro;
	
	public Estudiante() {
		this.id = null;
		this.apoderado = new Apoderado();
		this.persona = new Persona();
		this.codigo = "";
		this.fechaRegistro = new Date();
	}
	
	public Estudiante (Estudiante estudiante) {
		this.id = estudiante.getId();
		this.apoderado = new Apoderado (estudiante.getApoderado());
		this.persona = new Persona(estudiante.getPersona());
		this.codigo = estudiante.getCodigo();
		this.fechaRegistro = estudiante.getFechaRegistro();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Apoderado getApoderado() {
		return apoderado;
	}
	public void setApoderado(Apoderado apoderado) {
		this.apoderado = apoderado;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "Estudiante [persona=" + persona + ", codigo=" + codigo + "]";
	}
}
