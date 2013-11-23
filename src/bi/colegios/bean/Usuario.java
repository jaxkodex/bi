package bi.colegios.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ManagedBean
@Entity
@Table(name="usuario")
public class Usuario implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;
	@ManyToMany
	@JoinTable(name="usuario_has_rol", joinColumns = {
			@JoinColumn(name="username")
	}, inverseJoinColumns = {
			@JoinColumn(name="id_rol")
	})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Rol> roles;
	
	public Usuario() {
		this.username = "";
		this.password = "";
		this.persona = new Persona();
		this.roles = new ArrayList<Rol>(); 
	}
	
	public Usuario (Usuario usuario) {
		if (usuario == null) {
			this.username = "";
			this.password = "";
			this.persona = new Persona();
			this.roles = new ArrayList<Rol>();
			return;
		}
		this.username = usuario.getUsername();
		this.password = usuario.getPassword();
		this.persona = new Persona (usuario.getPersona());
		
		for (Rol r : usuario.getRoles()) {
			this.roles.add(new Rol (r));
		}
	}
	
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		for (Rol rol : roles) {
			auth.add(new SimpleGrantedAuthority(rol.getId().trim()));
		}
		return auth;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
