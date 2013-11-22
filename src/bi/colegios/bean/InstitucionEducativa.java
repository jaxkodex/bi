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
@Table(name="ie")
public class InstitucionEducativa implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id_ie")
	private Integer id;
	@Column(name="ie_nombre")
	private String nombre;
	@Column(name="ie_direccion")
	private String direccion;
	@Column(name="ie_codmodular")
	private String codigoModular;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodigoModular() {
		return codigoModular;
	}
	public void setCodigoModular(String codigoModular) {
		this.codigoModular = codigoModular;
	}
}
