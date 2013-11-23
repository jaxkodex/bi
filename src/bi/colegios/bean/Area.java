package bi.colegios.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table(name="area")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	@Id
	@GeneratedValue
	@Column(name="id_area")
	private Integer id;
	*/
	@Id
	@Column(name="id_area")
	private String id;
	@ManyToOne
	@JoinColumn(name="id_grado")
	private Grado grado;
	@Column(name="area_descripcion")
	private String descripcion;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Grado getGrado() {
		return grado;
	}
	public void setGrado(Grado grado) {
		this.grado = grado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "Area [grado=" + grado + ", descripcion=" + descripcion + "]";
	}
	
}
