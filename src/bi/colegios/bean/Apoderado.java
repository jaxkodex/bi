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

@Entity
@Table(name="apoderado")
public class Apoderado implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_apoderado")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;
	@Column(name="apo_fregistro")
	private Date fechaRegistro;
	
	public Apoderado() {
		this.id = null;
		this.persona = new Persona();
		this.fechaRegistro = new Date();
	}
	
	public Apoderado(Apoderado apoderado) {
		this.id = apoderado.getId();
		this.persona = new Persona(apoderado.getPersona());
		this.fechaRegistro = apoderado.getFechaRegistro();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
