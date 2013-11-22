package bi.colegios.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table(name="periodo_academico")
public class PeriodoAcademico implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_pacademico")
	private Integer id;
	@Column(name="pac_desc")
	private String descripcion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
