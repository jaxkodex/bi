package bi.colegios.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name="grado")
public class Grado implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	@Id
	@GeneratedValue
	@Column(name="id_grado")
	private Integer id;
	*/
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name="id_nivel")
	private Nivel nivel;
	@Column(name="grado_desc")
	private String descripcion;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
