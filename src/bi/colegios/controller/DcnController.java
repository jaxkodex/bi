package bi.colegios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bi.colegios.bean.Area;
import bi.colegios.bean.Ciclo;
import bi.colegios.bean.Dcn;
import bi.colegios.bean.Grado;
import bi.colegios.bean.Nivel;
import bi.colegios.dao.DcnDao;

@Controller
@ViewScoped
public class DcnController {
	private Dcn dcnNuevo;
	private Dcn dcnSeleccionado;
	private Nivel nivelNuevo;
	private Ciclo cicloNuevo;
	private Grado gradoNuevo;
	private Area areaNueva;
	private List<Dcn> dcnList;
	private List<Dcn> dcnListForGrado;
	private List<Dcn> dcnListForArea;
	private List<Nivel> nivelList;
	private List<Nivel> nivelListForCiclo;
	private List<Nivel> nivelListForGrado;
	private List<Nivel> nivelListForArea;
	private List<Ciclo> cicloList;
	private List<Ciclo> cicloListForGrado;
	private List<Ciclo> cicloListForArea;
	private List<Grado> gradoList;
	private List<Grado> gradoListForArea;
	private List<Area> areaList;

	private Integer idDcn;
	private Integer idDcnForCiclo;
	private Integer idDcnForGrado;
	private Integer idDcnForArea;
	private Integer idNivel;
	private Integer idNivelForGrado;
	private Integer idNivelForArea;
	private Integer idCiclo;
	private Integer idCicloForGrado;
	private Integer idCicloForArea;
	private Integer idGradoForArea;
	
	
	private DcnDao dcnDao;
	
	@Autowired
	public DcnController (DcnDao dcnDao) {
		this.dcnDao = dcnDao;
		
		dcnNuevo = new Dcn();
		dcnSeleccionado = new Dcn();
		
		nivelNuevo = new Nivel();
		cicloNuevo = new Ciclo();
		gradoNuevo = new Grado();
		areaNueva = new Area();
		
		dcnList = new ArrayList<Dcn>();
	}
	
	public void registrarDcn () {
		dcnDao.nuevaDcn(dcnNuevo);
		dcnNuevo = new Dcn();
	}
	
	public void registrarNivel () {
		Dcn dcn = dcnDao.loadDcnById(idDcn);
		if (dcn != null) {
			nivelNuevo.setDcn(dcn);
			dcnDao.nuevoNivel(nivelNuevo);
		}
		nivelNuevo = new Nivel();
	}
	
	public void registraCiclo () {
		Nivel nivel = dcnDao.loadNivelById (idNivel);
		if (nivel != null) {
			cicloNuevo.setNivel(nivel);
			dcnDao.nuevoCiclo(cicloNuevo);
		}
		cicloNuevo = new Ciclo();
	}
	
	public void registraGrado () {
		Ciclo ciclo = dcnDao.loadCicloById (idCicloForGrado);
		if (ciclo != null) {
			gradoNuevo.setCiclo(ciclo);
			dcnDao.nuevoGrado(gradoNuevo);
		}
		gradoNuevo = new Grado();
	}
	
	public void registraArea () {
		Grado grado = dcnDao.loadGradoById (idGradoForArea);
		if (grado != null) {
			areaNueva.setGrado(grado);
			dcnDao.nuevoArea(areaNueva);
		}
		areaNueva = new Area();
	}
	
	public void updateNivelListOnDcnChange () {
		this.nivelListForCiclo = dcnDao.listAllNivelByDcn(idDcnForCiclo);
	}
	
	public void updateNivelListOnDcnChangeForGrado () {
		this.nivelListForGrado = dcnDao.listAllNivelByDcn(idDcnForGrado);
	}
	
	public void updateCicloListOnDcnChange () {
		this.cicloListForGrado = dcnDao.listAllCicloByNivel(idNivelForGrado);
	}
	
	public void updateDataForArea () {
		this.nivelListForArea = dcnDao.listAllNivelByDcn(idDcnForArea);
		this.cicloListForArea = dcnDao.listAllCicloByNivel(idNivelForArea);
		this.gradoListForArea = dcnDao.listAllGradoByCiclo(idCicloForArea);
	}

	public List<Dcn> getDcnListForArea() {
		this.dcnListForArea = dcnDao.listAllDcn();
		return dcnListForArea;
	}

	public void setDcnListForArea(List<Dcn> dcnListForArea) {
		this.dcnListForArea = dcnListForArea;
	}

	public List<Nivel> getNivelListForArea() {
		return nivelListForArea;
	}

	public void setNivelListForArea(List<Nivel> nivelListForArea) {
		this.nivelListForArea = nivelListForArea;
	}

	public List<Ciclo> getCicloListForArea() {
		return cicloListForArea;
	}

	public void setCicloListForArea(List<Ciclo> cicloListForArea) {
		this.cicloListForArea = cicloListForArea;
	}

	public List<Grado> getGradoListForArea() {
		return gradoListForArea;
	}

	public void setGradoListForArea(List<Grado> gradoListForArea) {
		this.gradoListForArea = gradoListForArea;
	}

	public List<Area> getAreaList() {
		this.areaList = dcnDao.listAllAreaByDcn(idDcn);
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public Integer getIdDcnForArea() {
		return idDcnForArea;
	}

	public void setIdDcnForArea(Integer idDcnForArea) {
		this.idDcnForArea = idDcnForArea;
	}

	public Integer getIdNivelForArea() {
		return idNivelForArea;
	}

	public void setIdNivelForArea(Integer idNivelForArea) {
		this.idNivelForArea = idNivelForArea;
	}

	public Integer getIdCicloForArea() {
		return idCicloForArea;
	}

	public void setIdCicloForArea(Integer idCicloForArea) {
		this.idCicloForArea = idCicloForArea;
	}

	public Integer getIdGradoForArea() {
		return idGradoForArea;
	}

	public void setIdGradoForArea(Integer idGradoForArea) {
		this.idGradoForArea = idGradoForArea;
	}

	public Integer getIdCicloForGrado() {
		return idCicloForGrado;
	}

	public void setIdCicloForGrado(Integer idCicloForGrado) {
		this.idCicloForGrado = idCicloForGrado;
	}

	public List<Dcn> getDcnListForGrado() {
		this.dcnListForGrado = dcnDao.listAllDcn();
		return dcnListForGrado;
	}

	public Integer getIdCiclo() {
		return idCiclo;
	}

	public void setIdCiclo(Integer idCiclo) {
		this.idCiclo = idCiclo;
	}

	public void setDcnListForGrado(List<Dcn> dcnListForGrado) {
		this.dcnListForGrado = dcnListForGrado;
	}

	public List<Nivel> getNivelListForGrado() {
		return nivelListForGrado;
	}

	public void setNivelListForGrado(List<Nivel> nivelListForGrado) {
		this.nivelListForGrado = nivelListForGrado;
	}

	public List<Ciclo> getCicloListForGrado() {
		return cicloListForGrado;
	}

	public void setCicloListForGrado(List<Ciclo> cicloListForGrado) {
		this.cicloListForGrado = cicloListForGrado;
	}

	public List<Grado> getGradoList() {
		this.gradoList = dcnDao.listAllGradoByDcn(idDcn);
		return gradoList;
	}

	public void setGradoList(List<Grado> gradoList) {
		this.gradoList = gradoList;
	}

	public Integer getIdDcnForGrado() {
		return idDcnForGrado;
	}

	public void setIdDcnForGrado(Integer idDcnForGrado) {
		this.idDcnForGrado = idDcnForGrado;
	}

	public Integer getIdNivelForGrado() {
		return idNivelForGrado;
	}

	public void setIdNivelForGrado(Integer idNivelForGrado) {
		this.idNivelForGrado = idNivelForGrado;
	}

	public List<Nivel> getNivelListForCiclo() {
		return nivelListForCiclo;
	}

	public void setNivelListForCiclo(List<Nivel> nivelListForCiclo) {
		this.nivelListForCiclo = nivelListForCiclo;
	}

	public Integer getIdDcnForCiclo() {
		return idDcnForCiclo;
	}

	public void setIdDcnForCiclo(Integer idDcnForCiclo) {
		this.idDcnForCiclo = idDcnForCiclo;
	}

	public Integer getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
	}

	public List<Nivel> getNivelList() {
		System.out.println("Loading for DCN: "+idDcn);
		nivelList = dcnDao.listAllNivelByDcn(idDcn);
		return nivelList;
	}

	public List<Ciclo> getCicloList() {
		cicloList = dcnDao.listAllCicloByDcn(idDcn);
		return cicloList;
	}

	public void setCicloList(List<Ciclo> cicloList) {
		this.cicloList = cicloList;
	}

	public void setNivelList(List<Nivel> nivelList) {
		this.nivelList = nivelList;
	}

	public Nivel getNivelNuevo() {
		return nivelNuevo;
	}

	public void setNivelNuevo(Nivel nivelNuevo) {
		this.nivelNuevo = nivelNuevo;
	}

	public Integer getIdDcn() {
		return idDcn;
	}

	public void setIdDcn(Integer idDcn) {
		this.idDcn = idDcn;
	}

	public Ciclo getCicloNuevo() {
		return cicloNuevo;
	}

	public void setCicloNuevo(Ciclo cicloNuevo) {
		this.cicloNuevo = cicloNuevo;
	}

	public Grado getGradoNuevo() {
		return gradoNuevo;
	}

	public void setGradoNuevo(Grado gradoNuevo) {
		this.gradoNuevo = gradoNuevo;
	}

	public Area getAreaNueva() {
		return areaNueva;
	}

	public void setAreaNueva(Area areaNueva) {
		this.areaNueva = areaNueva;
	}

	public List<Dcn> getDcnList() {
		this.dcnList = dcnDao.listAllDcn();
		if (dcnList.size() != 0) {
			idDcn = dcnList.get(0).getId();
		}
		return dcnList;
	}

	public void setDcnList(List<Dcn> dcnList) {
		this.dcnList = dcnList;
	}

	public Dcn getDcnNuevo() {
		return dcnNuevo;
	}

	public void setDcnNuevo(Dcn dcnNuevo) {
		this.dcnNuevo = dcnNuevo;
	}

	public Dcn getDcnSeleccionado() {
		return dcnSeleccionado;
	}

	public void setDcnSeleccionado(Dcn dcnSeleccionado) {
		this.dcnSeleccionado = dcnSeleccionado;
	}
}
