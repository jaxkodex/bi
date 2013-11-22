package bi.colegios.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@ManagedBean
@Entity
@Table(name="persona")
public class Persona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id_persona")
	private Integer id;
	@OneToMany(mappedBy="persona")
	@LazyCollection(LazyCollectionOption.FALSE)
	List<Desempenia> desempeniaList;
	@ManyToOne
	@JoinColumn(name="id_ie")
	private InstitucionEducativa institucionEducativa;
	@Column(name="per_dni")
	private String dni;
	@Column(name="per_nombres")
	private String nombres;
	@Column(name="per_apellidos")
	private String apellidos;
	@Column(name="per_genero")
	private String genero;
	
	public Persona() {
		this.id = null;
		this.dni = "";
		this.nombres = "";
		this.apellidos = "";
		this.genero = "";
	}
	
	public Persona(Persona persona) {
		this.id = persona.getId();
		this.dni = persona.getDni();
		this.nombres = persona.getNombres();
		this.apellidos = persona.getApellidos();
		this.genero = persona.getGenero();
	}
	
	public InstitucionEducativa getInstitucionEducativa() {
		return institucionEducativa;
	}

	public void setInstitucionEducativa(InstitucionEducativa institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}

	public List<Desempenia> getDesempeniaList() {
		return desempeniaList;
	}

	public void setDesempeniaList(List<Desempenia> desempeniaList) {
		this.desempeniaList = desempeniaList;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Persona [nombres=" + nombres + ", apellidos=" + apellidos + "]";
	}
}
