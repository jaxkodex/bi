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
@Table(name="a_cargo")
public class ACargo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_acargo")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_oferta_grado")
	private OfertaGrado ofertaGrado;
	@ManyToOne
	@JoinColumn(name="id_area")
	private Area area;
	@ManyToOne
	@JoinColumn(name="id_desempenia")
	private Desempenia desempenia;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public OfertaGrado getOfertaGrado() {
		return ofertaGrado;
	}
	public void setOfertaGrado(OfertaGrado ofertaGrado) {
		this.ofertaGrado = ofertaGrado;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Desempenia getDesempenia() {
		return desempenia;
	}
	public void setDesempenia(Desempenia desempenia) {
		this.desempenia = desempenia;
	}
}
