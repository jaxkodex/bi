package bi.colegios.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bi.colegios.bean.Cargo;
import bi.colegios.bean.Desempenia;
import bi.colegios.bean.InstitucionEducativa;
import bi.colegios.bean.Persona;
import bi.colegios.bean.Rol;
import bi.colegios.bean.Usuario;
import bi.colegios.dao.InstitucionEducativaDao;
import bi.colegios.dao.PersonaDao;
import bi.colegios.dao.UsuarioDao;
import bi.colegios.ui.data.table.UsuarioLazyDataModel;

@Component
@SessionScoped
public class UsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// variables para la vista
	Usuario usuario;
	Usuario usuarioSeleccionado;
	Usuario usuarioNuevo;
	Usuario usuarioSesion;
	UsuarioLazyDataModel usuarios;
	List<Usuario> usuariosList;
	InstitucionEducativa InstitucionEducativaSeleccionada;
	Cargo cargoSeleccionado;
	Rol rolSeleccionado;
	List<Cargo> cargos;
	List<Rol> rolList;
	
	// Fuentes de datos
	UsuarioDao usuarioDao;
	PersonaDao personaDao;
	InstitucionEducativaDao institucionEducativaDao;
	
	@Autowired
	public UsuarioController (UsuarioDao usuarioDao, 
			PersonaDao personaDao, 
			InstitucionEducativaDao institucionEducativaDao) {
		this.usuarioDao = usuarioDao;
		this.personaDao = personaDao;
		this.institucionEducativaDao = institucionEducativaDao;
		init ();
	}
	
	public void init () {
		usuario = new Usuario();
		usuarioSeleccionado = new Usuario();
		usuarioNuevo = new Usuario();
		usuarioSesion = new Usuario();
		InstitucionEducativaSeleccionada = new InstitucionEducativa();
		cargoSeleccionado = new Cargo();
		rolSeleccionado = new Rol();
		rolList = new ArrayList<>();
	}
	
	public void login () throws ServletException, IOException {
		ExternalContext externalContext = FacesContext
				.getCurrentInstance()
				.getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) externalContext.getRequest())
				.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(
				(ServletRequest) externalContext.getRequest(), 
				(ServletResponse) externalContext.getResponse());
		
		Usuario tmp_usuario = usuarioDao.loadByUsername(usuario.getUsername());
		usuario = (tmp_usuario == null ? usuario : tmp_usuario);
		usuarioSesion = new Usuario (usuario);
		usuario = new Usuario ();
	}
	
	public void nuevoUsuario () {
		// Validation since simple validation was disabled
		boolean validation = true;
		if (InstitucionEducativaSeleccionada == null || InstitucionEducativaSeleccionada.getId() == null) {
			validation = false;
		}
		if (usuarioNuevo.getPersona().getApellidos().equals("") ||
				usuarioNuevo.getPersona().getNombres().equals("") || 
				usuarioNuevo.getPersona().getDni().length() != 8) {
			validation = false;
		}
		
		if (cargoSeleccionado.getId() == null) {
			validation = false;
		}
		
		if (usuarioNuevo.getUsername().equals("") || 
				usuarioNuevo.getPassword().equals("") ||
				usuarioNuevo.getRoles().size() < 1) {
			validation = false;
		}
		
		if (!validation) {
			FacesContext.getCurrentInstance().addMessage(
					null, 
					new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Sample info message", 
							"PrimeFaces rocks!"));  
			return;
		}
		
		usuarioNuevo.getPersona().setInstitucionEducativa(InstitucionEducativaSeleccionada);
		personaDao.nuevaPersona(usuarioNuevo.getPersona());
		Persona persona = personaDao.loadPersonaByDni(usuarioNuevo.getPersona().getDni());
		usuarioNuevo.setPersona(persona);
		boolean registrado = usuarioDao.nuevoUsuario(usuarioNuevo);
		Desempenia desempenia = new Desempenia();
		desempenia.setCargo(cargoSeleccionado);
		desempenia.setPersona(persona);
		desempenia.setFechaAsignacion(new Date());
		institucionEducativaDao.nuevoDesempenia(desempenia);
		RequestContext.getCurrentInstance().addCallbackParam("success", registrado);
	}
	
	public void addRole () {
		System.out.println("Adding role: "+rolSeleccionado.getId());
		usuarioNuevo.getRoles().add(rolSeleccionado);
		rolSeleccionado = new Rol();
	}

	public List<Rol> getRolList() {
		rolList = usuarioDao.listAllRoles();
		return rolList;
	}

	public void setRolList(List<Rol> rolList) {
		this.rolList = rolList;
	}

	public Rol getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(Rol rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	public Cargo getCargoSeleccionado() {
		return cargoSeleccionado;
	}

	public void setCargoSeleccionado(Cargo cargoSeleccionado) {
		this.cargoSeleccionado = cargoSeleccionado;
	}

	public List<Cargo> getCargos() {
		cargos = institucionEducativaDao.listAllCargo();
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public InstitucionEducativa getInstitucionEducativaSeleccionada() {
		return InstitucionEducativaSeleccionada;
	}

	public void setInstitucionEducativaSeleccionada(
			InstitucionEducativa institucionEducativaSeleccionada) {
		InstitucionEducativaSeleccionada = institucionEducativaSeleccionada;
	}

	public List<Usuario> getUsuariosList() {
		this.usuariosList = usuarioDao.listAllUsuario ();
		return usuariosList;
	}

	public void setUsuariosList(List<Usuario> usuariosList) {
		this.usuariosList = usuariosList;
	}

	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	public Usuario getUsuarioNuevo() {
		return usuarioNuevo;
	}

	public void setUsuarioNuevo(Usuario usuarioNuevo) {
		this.usuarioNuevo = usuarioNuevo;
	}

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public UsuarioLazyDataModel getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(UsuarioLazyDataModel usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
