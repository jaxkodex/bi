package bi.colegios.data.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import bi.colegios.bean.ACargo;
import bi.colegios.bean.Area;
import bi.colegios.bean.Calificacion;
import bi.colegios.bean.Ciclo;
import bi.colegios.bean.Consideraciones;
import bi.colegios.bean.Estudiante;
import bi.colegios.bean.Grado;
import bi.colegios.bean.Matricula;
import bi.colegios.bean.Nivel;
import bi.colegios.bean.PeriodoCalifica;
import bi.colegios.bean.Persona;

public class UploadDataParser {
	//UploadedFile archivo;
	private InputStream is;
	private Map<String, Estudiante> estudiantes;
	private Map<String, Matricula> matriculas;
	private Map<String, Consideraciones> consideraciones;
	private Map<String, List<Calificacion>> calificacionesMap;
	private List<Calificacion> calificaciones;
	private String nivel, grado, seccion, area;
	
	//public UploadDataParser (UploadedFile archivo) {
	public UploadDataParser (InputStream is) {
		this.is = is;
		
		estudiantes = new HashMap<String, Estudiante>();
		consideraciones = new HashMap<String, Consideraciones>();
		matriculas = new HashMap<String, Matricula>();
		calificacionesMap = new HashMap<String, List<Calificacion>>();
		
		calificaciones = new ArrayList<Calificacion>();
		
		nivel = "";
		grado = "";
		seccion = "";
		area = "";
	}
	
	public Map<String, List<Calificacion>> parse () {
		try {
			//ZipInputStream zis = new ZipInputStream(archiperiodoCalificavo.getInputstream());
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry ze;
			while ((ze = zis.getNextEntry()) != null) {
				if (!ze.isDirectory() && ze.getName().endsWith(".csv")) {
					// Procesar Archivo
					processFileName(ze.getName());
					readFileContents(zis);
				}
			}
			zis.close();
			for (String key : calificacionesMap.keySet()) {
				System.out.format("%-50s%d\n", key, calificacionesMap.get(key).size());
				/*
				for (Calificacion c																																			alificacion : calificacionesMap.get(key)) {
					System.out.format("%s\t%s\t%s\t%s\n", calificacion.getMatricula().getEstudiante().getPersona().getApellidos(),
							calificacion.getPeriodoCalifica().getId(),
							calificacion.getConsideracion().getId(),
							calificacion.getValor());
				}
				break;
				*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return calificacionesMap;
	}
	
	private void processFileName (String complete_name) {
		String filename = complete_name.substring(complete_name.lastIndexOf('/')+1,
				complete_name.lastIndexOf('.'));
		String[] parametros = filename.split("_");
		System.out.println("Archivo: " + complete_name);
		System.out.println(filename);
		//System.out.println("NIVEL: "+parametros[0]+" GRADO: "+parametros[1]+" SECCION: "+parametros[2]);
		/*
		System.out.format("NIVEL: %s GRADO: %s SECCION: %s AREA: %s\n", 
				parametros[0], 
				parametros[1], 
				parametros[2], 
				parametros[3]);
		System.out.println("-------------------------------------------------------");
		*/
		nivel = parametros[0];
		grado = parametros[1];
		seccion = parametros[2];
		area = parametros[3];
	}
	
	private void readFileContents (InputStream contents) {
		try {
			InputStreamReader in = new InputStreamReader(contents);
			BufferedReader br = new BufferedReader(in);
			String line = "";
			int count = 1, count_consideraciones = 1;
			calificaciones = new ArrayList<Calificacion>();
			while ((line = br.readLine()) != null && line.length() != 0) {
				// Cogera solo los datos de los alumnos
				if (count == 1) {
					// ignorar la primera linea
					count++;
					continue;
				}
				Matricula m = getMatricula(line);
				getCalificacion(line, m);
				count++;
			}
			while ((line = br.readLine()) != null && line.length() != 0) {
				if (count_consideraciones == 1) { // Ignorar la primera linea
					count_consideraciones++;
					continue;
				}
				String[] data = line.split(",", -1);
				String descripcion = "", codigo = data[1].trim().substring(0, data[1].indexOf(" "));
				for (int i = 2; i < data.length; i++) {
					descripcion += data[i];
				}
				Consideraciones consideracion = new Consideraciones();
				consideracion.setDescripcion(descripcion);
				consideracion.setId(codigo);
				consideraciones.put(nivel+grado+seccion+area+codigo, consideracion);
				count_consideraciones++;
			}
			calificacionesMap.put(nivel+"_"+grado+"_"+seccion+"_"+area, calificaciones);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Matricula getMatricula (String line) {
		String[] data = line.split(",", -1);
		//Estudiante e = estudiantes.get(data[1].trim());
		Matricula m = matriculas.get(data[1].trim());
		
		/*
		if (e != null) {
			return e;
		}
		*/
		if (m != null) {
			return m;
		}
		
		Persona p = new Persona ();
		p.setNombres(data[2].substring(1).trim());
		p.setApellidos(data[3].substring(0, data[3].length()-1).trim());
		p.setGenero(data[4].trim());
		
		Estudiante e = new Estudiante();
		e.setCodigo(data[1].trim());
		e.setPersona(p);
		
		m = new Matricula();
		m.setEstudiante(e);
		
		estudiantes.put(e.getCodigo(), e);
		matriculas.put(e.getCodigo(), m);
		
		return m;
	}
	
	private void getCalificacion (String line, Matricula m) {
		String[] data = line.split(",", -1);
		Calificacion calificacion = new Calificacion();
		Consideraciones consideracion = new Consideraciones();
		PeriodoCalifica periodoCalifica = new PeriodoCalifica();
		Nivel nivel = new Nivel();
		Grado grado = new Grado();
		Area area = new Area();
		ACargo aCargo = new ACargo();
		Ciclo ciclo = new Ciclo();
		
		nivel.setId(this.nivel.trim());
		nivel.setDescripcion(this.nivel.trim());
		
		grado.setId(this.grado.trim());
		grado.setDescripcion(this.grado.trim());
		grado.setNivel(nivel);
		
		consideracion.setId(data[6].trim());
		
		periodoCalifica.setId(data[5]);
		periodoCalifica.setDescripcion(data[5]);
		
		area.setId(this.nivel+this.grado+this.area);
		area.setDescripcion(this.area);
		
		area.setGrado(grado);
		aCargo.setArea(area);
		/*
		ciclo.setNivel(nivel);
		*/
		
		calificacion.setMatricula(m);
		calificacion.setConsideracion(consideracion);
		calificacion.setPeriodoCalifica(periodoCalifica);
		calificacion.setaCargo(aCargo);
		try {
			calificacion.setValor((float) CalificacionPrimaria.valueOf(data[7]).getVal());
		} catch (IllegalArgumentException iae) {
			calificacion.setValor(null);
		}
		
		calificaciones.add(calificacion);
	}
}
