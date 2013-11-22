package bi.colegios.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

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

import bi.colegios.bean.InstitucionEducativa;
import bi.colegios.bean.Persona;
import bi.colegios.bean.Usuario;
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
	
	// Fuentes de datos
	UsuarioDao usuarioDao;
	PersonaDao personaDao;
	
	@Autowired
	public UsuarioController (UsuarioDao usuarioDao, PersonaDao personaDao) {
		this.usuarioDao = usuarioDao;
		this.personaDao = personaDao;
		init ();
	}
	
	public void init () {
		usuario = new Usuario();
		usuarioSeleccionado = new Usuario();
		usuarioNuevo = new Usuario();
		usuarioSesion = new Usuario();
		InstitucionEducativaSeleccionada = new InstitucionEducativa();
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
		personaDao.nuevaPersona(usuarioNuevo.getPersona());
		Persona persona = personaDao.loadPersonaByDni(usuarioNuevo.getPersona().getDni());
		usuarioNuevo.setPersona(persona);
		boolean registrado = usuarioDao.nuevoUsuario(usuarioNuevo);
		RequestContext.getCurrentInstance().addCallbackParam("success", registrado);
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
