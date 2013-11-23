package bi.colegios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bi.colegios.bean.Rol;
import bi.colegios.dao.UsuarioDao;

@Controller
@ViewScoped
public class RolController {
	private Rol rolNuevo;
	private List<Rol> roles;
	
	private UsuarioDao usuarioDao;
	
	@Autowired
	public RolController(UsuarioDao usuarioDao) {
		rolNuevo = new Rol();
		roles = new ArrayList<>();
		
		this.usuarioDao = usuarioDao;
	}
	
	public void registrarNuevoRol () {
		usuarioDao.nuevoRol(rolNuevo);
		rolNuevo = new Rol();
	}

	public Rol getRolNuevo() {
		return rolNuevo;
	}

	public void setRolNuevo(Rol rolNuevo) {
		this.rolNuevo = rolNuevo;
	}

	public List<Rol> getRoles() {
		this.roles = usuarioDao.listAllRoles ();
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
}
