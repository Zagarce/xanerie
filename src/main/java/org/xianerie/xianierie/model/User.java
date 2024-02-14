package org.xianerie.xianierie.model;

/*
 * En la clase donde se establece la lógica del negocio (model) voy a hacer que coincida con DB.
 * Es decir, la clase se convierta en una Entity, con la notación @Entity
 * Para establecer la tabla de la DB a la que corresponde esta entidad, utilizamos la anotación @Table y el nombre de la tabla
 * Indicar a JPA mi punto de entrada (PK), usando la anotación @Id
 * Como queremos que el Id sea autoincrementable usamos la anotacion @GeneratedValue(strategy = GenerationType.IDENTITY)
 * Y si queremos, podemos establecer la columna a la que corresponde cada atributo con la anotación @Column(name="name"), pero SI TENEMOS QUE DEFINIR el nombre de la primera columna para que pueda hacer match
 * */
 

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;




@Entity
@Table(name="usuarios") //nombre de la tabla de la DB
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuarios")
	private Long id;
	@Column(name="nombre")
	private String nombre;
	@Column(name="apellido")
	private String apellido;
	@Column(name="correo")
	private String email;
	@Column(name="contrasenia")
	private String password;
	@Column(name="telefono")
	private String telefono;
	@Column(name="rol")
	private String rol;
	@Column(name="url_perfil_imagen")
	private String url_perfil_imagen;
	
	//En este caso le agregamos el column para indicar a que columna corresponde cada una, podíamos evitarnos el problema si solo ponemos los mismo nombres en mysql y en eclipse
	
	//JPA necesita un método que le permita construir cualquier objeto sin tomar en cueta sus atributos. Este método se conoce como contructor vacío
	public User() {
	}

	//constructor 
	public User(Long id, String nombre, String apellido, String email, String password, String telefono, String direccion, String rol, String url_perfil_imagen) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.telefono= telefono;
		this.rol=rol;
		this.url_perfil_imagen=url_perfil_imagen;
		

	}
	
	
	//getters y setters

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUrl_perfil_imagen() {
		return url_perfil_imagen;
	}

	public void setUrl_perfil_imagen(String url_perfil_imagen) {
		this.url_perfil_imagen = url_perfil_imagen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	//to string 
	@Override
	public String toString() {
		return "User [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", password="
				+ password + ", telefono=" + telefono + ", direccion=" + ", rol=" + rol
				+ ", url_perfil_imagen=" + url_perfil_imagen + "]";
	}

	
	//Tenemos que generar dos metodos adicionales: hashCode() y equals(). Se generan de la misma manera que generamos los demas metodos: click derecho> source> generate hashCode() y equals()
	
	@Override
	public int hashCode() {
		return Objects.hash(apellido, email, id, nombre, password, rol, telefono, url_perfil_imagen);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(apellido, other.apellido)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password)
				&& Objects.equals(rol, other.rol) && Objects.equals(telefono, other.telefono)
				&& Objects.equals(url_perfil_imagen, other.url_perfil_imagen);
	}

	
	
	
	
	




	
	
}
