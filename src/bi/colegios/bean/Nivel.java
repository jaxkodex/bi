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
@Table(name="nivel")
public class Nivel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	@Id
	@GeneratedValue
	@Column(name="id_nivel")
	private Integer id;
	*/
	@Id
	@Column(name="id_nivel")
	private String id;
	@ManyToOne
	@JoinColumn(name="id_dcn")
	private Dcn dcn;
	@Column(name="nivel_descripcion")
	private String descripcion;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Dcn getDcn() {
		return dcn;
	}
	public void setDcn(Dcn dcn) {
		this.dcn = dcn;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
