package bi.colegios.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rol")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_rol")
	private String id;
	@Column(name="rol_desc")
	private String descripcion;
	
	public Rol () {
		id = "";
		descripcion = "";
	}
	
	public Rol(Rol r) {
		this.id = r.getId();
		this.descripcion = r.getDescripcion();
	}
	
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
		return "Rol [id=" + id + "]";
	}
}
