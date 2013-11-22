package bi.colegios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
	
	@Autowired
	public InstitucionEducativaController (InstitucionEducativaDao institucionEducativaDao) {
		this.institucionEducativaDao = institucionEducativaDao;
		this.nuevaInstitucionEducativa = new InstitucionEducativa();
		this.institucionEducativaList = new ArrayList<InstitucionEducativa>();
		this.searchQuery = "";
		this.searchResults = new ArrayList<InstitucionEducativa>();
	}
	
	public void registrarInstitucionEducativa () {
		institucionEducativaDao.nuevaInstitucioneducativa(nuevaInstitucionEducativa);
		nuevaInstitucionEducativa = new InstitucionEducativa();
	}
	
	public void doSearch () {
		searchResults = institucionEducativaDao.search(searchQuery);
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
