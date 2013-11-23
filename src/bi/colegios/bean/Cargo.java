package bi.colegios.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table(name="cargo")
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	@Id
	@GeneratedValue
	@Column(name="id_cargo")
	private Integer id;
	*/
	@Id
	@Column(name="id_cargo")
	private String id;
	@Column(name="cargo_desc")
	private String descripcion;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return descripcion;
	}
}
