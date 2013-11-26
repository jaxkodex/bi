package bi.colegios.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="calificacion")
public class Calificacion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id_calificacion")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_consideracion")
	private Consideraciones consideracion;
	@ManyToOne
	@JoinColumn(name="id_percalifica")
	private PeriodoCalifica periodoCalifica;
	@ManyToOne
	@JoinColumn(name="id_matricula")
	private Matricula matricula;
	@ManyToOne
	@JoinColumn(name="id_acargo")
	private ACargo aCargo;
	@Column(name="cal_valor")
	private Float valor;
	@Column(name="cal_fecha")
	private Date fecha;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Consideraciones getConsideracion() {
		return consideracion;
	}
	public void setConsideracion(Consideraciones consideracion) {
		this.consideracion = consideracion;
	}
	public PeriodoCalifica getPeriodoCalifica() {
		return periodoCalifica;
	}
	public void setPeriodoCalifica(PeriodoCalifica periodoCalifica) {
		this.periodoCalifica = periodoCalifica;
	}
	public Matricula getMatricula() {
		return matricula;
	}
	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
	public ACargo getaCargo() {
		return aCargo;
	}
	public void setaCargo(ACargo aCargo) {
		this.aCargo = aCargo;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Calificacion [matricula=" + matricula + ", valor=" + valor
				+ "]";
	}
	
}
