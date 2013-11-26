package bi.colegios.bean;

import java.io.Serializable;

/*
@ManagedBean
@Entity
@Table(name="ciclo")
*/
public class Ciclo implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	@Id
	@GeneratedValue
	@Column(name="id_ciclo")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_nivel")
	private Nivel nivel;
	@Column(name="ciclo_descripcion")
	private String descripcion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	*/
}
