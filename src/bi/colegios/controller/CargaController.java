package bi.colegios.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bi.colegios.bean.Calificacion;
import bi.colegios.bean.Dcn;
import bi.colegios.bean.InstitucionEducativa;
import bi.colegios.bean.PeriodoAcademico;
import bi.colegios.dao.ImportDataDao;
import bi.colegios.data.parser.UploadDataParser;

@Controller
@ViewScoped
public class CargaController {
	private UploadedFile file;
	private Map<String, List<Calificacion>> calificaciones;
	private Map<String, String> nivelList;
	private Map<String, Map<String, String>> ofertaGradoList;
	private Map<String, Map<String, String>> areaList;
	private List<Calificacion> calificacionesPorArea;
	
	String nivelSeleccionado;
	String gradoSeleccionado;
	String areaSeleccionada;
	
	Map<String, String> gradosParaNivelSeleccionado;
	Map<String, String> areasParaGradoSeleccionado;
	
	List<Calificacion> calificacionesSeleccionadas;
	
	List<String> columnas;
	List<String[]> columnaData;
	Map<String, String> periodosCalificacion;
	Map<String, String> consideracionCalificacion;
	Map<String, Map<String, String>> consideracionCalificacionIndex;
	Map<String, String> estudiantes;
	Map<String, Map<String, Map<String, Float>>> calificacionesHolder;
	
	ImportDataDao importDataDao;
	
	@Autowired
	public CargaController (ImportDataDao importDataDao) {
		calificaciones = new HashMap<>();
		nivelList = new HashMap<>();
		ofertaGradoList = new HashMap<>();
		areaList = new HashMap<>();
		
		this.importDataDao = importDataDao;
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
	
	public void save () {
		PeriodoAcademico periodoAcademico = new PeriodoAcademico();
		periodoAcademico.setId(2013);
		periodoAcademico.setDescripcion("2013");
		InstitucionEducativa ie = new InstitucionEducativa();
		ie.setId(1);
		importDataDao.registrarAll(calificaciones, periodoAcademico, ie, new Dcn());
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
		if (gradoSeleccionado != null && !gradoSeleccionado.equals("")) {
			areasParaGradoSeleccionado = areaList.get(gradoSeleccionado);
		} else {
			areasParaGradoSeleccionado = new HashMap<>();
		}
	}
	
	public void onAreaChangeHandler () {
		if (areaSeleccionada != null && !areaSeleccionada.equals("")) {
			String areaKey = areaSeleccionada.replace(" ", "_");
			String nivel = areaKey.split("_")[0];
			calificacionesPorArea = calificaciones.get(areaKey);
			
			// Cargar los datos para el modelo de
			//- la tabla que se va a dibujar
			periodosCalificacion = new HashMap<>();
			estudiantes = new HashMap<>();
			consideracionCalificacion = new HashMap<>();
			calificacionesHolder = new HashMap<>();
			columnas = new ArrayList<>();
			columnaData = new ArrayList<>();
			consideracionCalificacionIndex = new HashMap<>();
			
			for (Calificacion calificacion : calificacionesPorArea) {
				periodosCalificacion.put(calificacion.getPeriodoCalifica().getId(), 
						calificacion.getPeriodoCalifica().getId());
				estudiantes.put(calificacion.getMatricula().getEstudiante().getCodigo(), 
						calificacion.getMatricula().getEstudiante().getPersona().getNombres()
						+", "+calificacion.getMatricula().getEstudiante().getPersona().getApellidos());
				/*
				consideracionCalificacion.put(calificacion.getPeriodoCalifica().getId()+
						calificacion.getConsideracion().getId(), 
						calificacion.getConsideracion().getId());
				*/
				//consideracionCalificacionIndex.put(calificacion.getPeriodoCalifica().getId(), value)
				
				Map<String, String> tmpPeriodoCalifica = consideracionCalificacionIndex.get(calificacion.getPeriodoCalifica().getId());
				
				if (tmpPeriodoCalifica == null) {
					tmpPeriodoCalifica = new HashMap<>();
				}
				tmpPeriodoCalifica.put(calificacion.getConsideracion().getId(), 
						calificacion.getPeriodoCalifica().getId()+"-"+
						calificacion.getConsideracion().getId());
				consideracionCalificacionIndex.put(calificacion.getPeriodoCalifica().getId(), tmpPeriodoCalifica);
				
				Map<String, Map<String, Float>> record = calificacionesHolder.get(
						calificacion.getMatricula().getEstudiante().getCodigo());
				if (record == null) {
					record = new HashMap<>();
				}
				Map<String, Float> nota = record.get(calificacion.getPeriodoCalifica().getId());
				if (nota == null) {
					nota = new HashMap<>();
				}
				nota.put(calificacion.getConsideracion().getId(), calificacion.getValor());
				record.put(calificacion.getPeriodoCalifica().getId(), nota);
				calificacionesHolder.put(calificacion.getMatricula().getEstudiante().getCodigo(), record);
			}
			columnas = new ArrayList<>();
			columnas.add("N.");
			columnas.add("ESTUDIANTE");
			/*
			for (String keyPeriodo : periodosCalificacion.keySet()) {
				for (String keyConsideracion : consideracionCalificacion.keySet()) {
					columnas.add(keyConsideracion);
				}
			}
			*/
			for (String keyPeriodo : periodosCalificacion.keySet()) {
				for (String keyConsideracion : consideracionCalificacionIndex.get(keyPeriodo).keySet()) {
					columnas.add(keyPeriodo+" - "+keyConsideracion);
				}
			}
			
			int studentCount = 1;
			for (String keyReporteNotas : calificacionesHolder.keySet()) {
				ArrayList<String> data = new ArrayList<String>();
				data.add(studentCount+"");
				data.add(estudiantes.get(keyReporteNotas));
				Map<String, Map<String, Float>> record = calificacionesHolder.get(keyReporteNotas);
				for (int i = 2; i < columnas.size(); i++) {
					String[] ref = columnas.get(i).split("-");
					Float val = record.get(ref[0].trim()).get(ref[1].trim());
					if (nivel.equals("PRIMARIA")) {
						int intVal = (val == null ? -1 : val.intValue());
						switch (intVal) {
						case 2:
							data.add("A");
							break;
						case 1:
							data.add("B");
							break;
						case 0:
							data.add("C");
							break;
						default:
							data.add("-");
							break;
						}
					}else{
						if (val != null)
							data.add(String.format("%.2f", val));
						else
							data.add("-");
					}
				}
				/*
				for (String keyPeriodo : periodosCalificacion.keySet()) {
					Map<String, Float> nota = record.get(keyPeriodo);
					for (String keyConsideracion : consideracionCalificacionIndex.get(keyPeriodo).keySet()) {
						if (nivel.equals("PRIMARIA")) {
							int val = (nota.get(keyConsideracion) == null ? -1 : nota.get(keyConsideracion).intValue());
							switch (val) {
							case 2:
								data.add("A");
								break;
							case 1:
								data.add("B");
								break;
							case 0:
								data.add("C");
								break;
							default:
								data.add("-");
								break;
							}
						} else {
							data.add(String.format("%.2f", nota.get(keyConsideracion)));
						}
					}
				}
				*/
				String[] d = new String[data.size()];
				for (int i = 0; i < data.size(); i++) {
					d[i] = data.get(i);
				}
				columnaData.add(d);
				studentCount++;
			}

			System.out.format("%-3s", columnas.get(0));
			System.out.format("%-45s", columnas.get(1));
			for (int i=2; i < columnas.size(); i++) {
				System.out.format("%-5s", columnas.get(i));
			}
			System.out.println();

			for (String[] data : columnaData) {
				System.out.format("%-3s%-45s", data[0], (data[1].length() > 45 ? data[1].substring(0, 45) : data[1]));
				for (int i = 2; i < data.length; i++) {
					System.out.format("%-5s", data[i]);
				}
				System.out.println();
			}
		} else {
			calificacionesPorArea = new ArrayList<>();
		}
	}

	public List<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<String> columnas) {
		this.columnas = columnas;
	}

	public List<String[]> getColumnaData() {
		return columnaData;
	}

	public void setColumnaData(List<String[]> columnaData) {
		this.columnaData = columnaData;
	}

	public List<Calificacion> getCalificacionesPorArea() {
		return calificacionesPorArea;
	}

	public void setCalificacionesPorArea(List<Calificacion> calificacionesPorArea) {
		this.calificacionesPorArea = calificacionesPorArea;
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
