package bi.colegios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bi.colegios.bean.Cargo;
import bi.colegios.bean.InstitucionEducativa;
import bi.colegios.dao.InstitucionEducativaDao;

@Controller
@ViewScoped
public class InstitucionEducativaController {
	private InstitucionEducativa nuevaInstitucionEducativa;
	private List<InstitucionEducativa> institucionEducativaList;
	private InstitucionEducativaDao institucionEducativaDao;
	private String searchQuery;
	private List<InstitucionEducativa> searchResults;
	private List<Cargo> cargos;
	private Cargo cargoNuevo;
	
	@Autowired
	public InstitucionEducativaController (InstitucionEducativaDao institucionEducativaDao) {
		this.institucionEducativaDao = institucionEducativaDao;
		this.nuevaInstitucionEducativa = new InstitucionEducativa();
		this.institucionEducativaList = new ArrayList<InstitucionEducativa>();
		this.searchQuery = "";
		this.searchResults = new ArrayList<InstitucionEducativa>();
		cargoNuevo = new Cargo();
	}
	
	public void registrarInstitucionEducativa () {
		institucionEducativaDao.nuevaInstitucioneducativa(nuevaInstitucionEducativa);
		nuevaInstitucionEducativa = new InstitucionEducativa();
	}
	
	public void doSearch () {
		searchResults = institucionEducativaDao.search(searchQuery);
	}
	
	public void nuevoCargo () {
		String[] data = cargoNuevo.getDescripcion().split(" ");
		if (data.length != 1) {
			return;
		}
		cargoNuevo.setId(cargoNuevo.getDescripcion());
		institucionEducativaDao.nuevoCargo(cargoNuevo);
		cargoNuevo = new Cargo();
	}
	
	public List<Cargo> getCargos() {
		cargos = institucionEducativaDao.listAllCargo();
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo getCargoNuevo() {
		return cargoNuevo;
	}

	public void setCargoNuevo(Cargo cargoNuevo) {
		this.cargoNuevo = cargoNuevo;
	}

	public List<InstitucionEducativa> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<InstitucionEducativa> searchResults) {
		this.searchResults = searchResults;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public InstitucionEducativa getNuevaInstitucionEducativa() {
		return nuevaInstitucionEducativa;
	}
	public void setNuevaInstitucionEducativa(
			InstitucionEducativa nuevaInstitucionEducativa) {
		this.nuevaInstitucionEducativa = nuevaInstitucionEducativa;
	}
	public List<InstitucionEducativa> getInstitucionEducativaList() {
		this.institucionEducativaList = institucionEducativaDao.listAllInstitucionEducativa();
		return institucionEducativaList;
	}
	public void setInstitucionEducativaList(
			List<InstitucionEducativa> institucionEducativaList) {
		this.institucionEducativaList = institucionEducativaList;
	}
}
