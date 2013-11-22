package bi.colegios.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;

import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Controller;

import bi.colegios.bean.Calificacion;
import bi.colegios.data.parser.UploadDataParser;

@Controller
@ViewScoped
public class CargaController {
	private UploadedFile file;
	private Map<String, List<Calificacion>> calificaciones;
	private Map<String, String> nivelList;
	private Map<String, Map<String, String>> ofertaGradoList;
	private Map<String, Map<String, String>> areaList;
	
	String nivelSeleccionado;
	String gradoSeleccionado;
	String areaSeleccionada;
	
	Map<String, String> gradosParaNivelSeleccionado;
	Map<String, String> areasParaGradoSeleccionado;
	
	List<Calificacion> calificacionesSeleccionadas;
	
	public CargaController () {
		calificaciones = new HashMap<>();
		nivelList = new HashMap<>();
		ofertaGradoList = new HashMap<>();
		areaList = new HashMap<>();
	}
	
	public void carga () {
		if (file != null) {
			UploadDataParser udp;
			try {
				udp = new UploadDataParser(file.getInputstream());
				calificaciones = udp.parse();
				loadMetaData();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadMetaData () {
		nivelList = new HashMap<>();
		ofertaGradoList = new HashMap<>();
		areaList = new HashMap<>();
		for (String key : calificaciones.keySet()) {
			String[] data = key.split("_");
			nivelList.put(data[0], data[0]);
			Map<String, String> tmpGrados = ofertaGradoList.get(data[0]);
			if (tmpGrados == null) {
				tmpGrados = new HashMap<>();
			}
			tmpGrados.put(data[0] + " " +data[1]+" "+data[2], data[0] + " " +data[1]+" "+data[2]);
			ofertaGradoList.put(data[0], tmpGrados);
			Map<String, String> tmpAreas = areaList.get(data[0]+" "+data[1]+" "+data[2]);
			if (tmpAreas == null) {
				tmpAreas = new HashMap<>();
			}
			tmpAreas.put(data[0]+" "+data[1]+" "+data[2]+" "+data[3], data[0]+" "+data[1]+" "+data[2]+" "+data[3]);
			areaList.put(data[0]+" "+data[1]+" "+data[2], tmpAreas);
			/*
			ofertaGradoList.put(data[0]+"_"+data[1]+"_"+data[2], data[1]+" "+data[2]);
			areaList.put(data[0]+"_"+data[1]+"_"+data[2]+"_"+data[3], data[3]);
			*/
		}
	}
	
	public void onNivelChangeHandler () {
		if (nivelSeleccionado != null && !nivelSeleccionado.equals("")) {
			gradosParaNivelSeleccionado = ofertaGradoList.get(nivelSeleccionado);
		} else {
			gradosParaNivelSeleccionado = new HashMap<>();
		}
	}
	
	public void onGradoChangeHandler () {
		System.out.println("SE CARGAN LOS DATOS PARA EL GRADO: " + gradoSeleccionado);
		if (gradoSeleccionado != null && !gradoSeleccionado.equals("")) {
			areasParaGradoSeleccionado = areaList.get(gradoSeleccionado);
		} else {
			areasParaGradoSeleccionado = new HashMap<>();
		}
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Map<String, String> getAreasParaGradoSeleccionado() {
		return areasParaGradoSeleccionado;
	}

	public void setAreasParaGradoSeleccionado(
			Map<String, String> areasParaGradoSeleccionado) {
		this.areasParaGradoSeleccionado = areasParaGradoSeleccionado;
	}

	public Map<String, String> getNivelList() {
		return nivelList;
	}

	public void setNivelList(Map<String, String> nivelList) {
		this.nivelList = nivelList;
	}

	public Map<String, Map<String, String>> getOfertaGradoList() {
		return ofertaGradoList;
	}

	public void setOfertaGradoList(Map<String, Map<String, String>> ofertaGradoList) {
		this.ofertaGradoList = ofertaGradoList;
	}

	public Map<String, Map<String, String>> getAreaList() {
		return areaList;
	}

	public void setAreaList(Map<String, Map<String, String>> areaList) {
		this.areaList = areaList;
	}

	public String getNivelSeleccionado() {
		return nivelSeleccionado;
	}

	public void setNivelSeleccionado(String nivelSeleccionado) {
		this.nivelSeleccionado = nivelSeleccionado;
	}

	public String getGradoSeleccionado() {
		return gradoSeleccionado;
	}

	public void setGradoSeleccionado(String gradoSeleccionado) {
		this.gradoSeleccionado = gradoSeleccionado;
	}

	public String getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(String areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	public Map<String, String> getGradosParaNivelSeleccionado() {
		return gradosParaNivelSeleccionado;
	}

	public void setGradosParaNivelSeleccionado(
			Map<String, String> gradosParaNivelSeleccionado) {
		this.gradosParaNivelSeleccionado = gradosParaNivelSeleccionado;
	}

	public List<Calificacion> getCalificacionesSeleccionadas() {
		return calificacionesSeleccionadas;
	}

	public void setCalificacionesSeleccionadas(
			List<Calificacion> calificacionesSeleccionadas) {
		this.calificacionesSeleccionadas = calificacionesSeleccionadas;
	}
}
