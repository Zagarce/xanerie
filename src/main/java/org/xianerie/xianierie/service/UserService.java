package org.xianerie.xianierie.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xianerie.xianierie.User.repository.UserRepository;
import org.xianerie.xianierie.exceptions.UserNotFoundException;
import org.xianerie.xianierie.model.User;



@Service
public class UserService {
	//Eliminamos todo lo relacionado al ArrayList que creamos para instanciar objetos 
	//Ahora vamos a traer a JPA para que me brinde los métodos para recuperar los datos
	private final UserRepository repository;
	
	//Constructor
	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;	}
	

	//Crear metodo de tipo GET para traer a todos los usuarios 
	public List<User> allUsers(){
		return repository.findAll();
	}	
	
	//Crear método de tipo get para traer un usuario por id, si no encuentra al usuario arroja una excepcion (default o personalizada)
	public User getOne (Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException (id)); //Estoy creando una exception personalizada que vivirá en otra clase con el mismo nombre de la exception)
	}
	
	//Crear método para eliminar un usuario por id.  Primero debemos comprobar que el usuario con el id realmente existe, si existe, se elimina, si no se lanza una exception por default (IllegalStateException) o personalizada 
	
	public void deleteUser(Long id) {
		if(repository.existsById(id)) {
			repository.deleteById(id);
		}else {
			//throw new IllegalStateException("El cliente con el id " + id+ " no existe"); //Esta es una exception default
			throw new UserNotFoundException(id);
		}
	}
	
	
	/*Método POST para crear un nuevo usuario. 
	 * Primero tengo que consultar la base de datos para saber si el usuario existe. ör ello, debo definir de qué manera buscaré a mi usuario. En este caso será medinate email
	 * Si el usuario ya existe, no puede crearse un nuevo usuario con el mismo email.
	 * Si no hay un usuario asociado a ese email, se crea un nuevo usuario 
	 * Tenemos qur definir la búsqueda específica por email (`findByEmail`) utilizando la consulta de tipo JPQL (Java Persistence Query Language, la cual se realiza dentro de la interface UserRepository ya que ahí se implementan todpos los metodo de JPA
	*/
	
	public User addUser(User user) {
		//Crear una variable de tipo user para revisar o buscar el email de usuario, traemos le valor de la base de datos, deespues realizo la sentencia condicional(!if), si es true se cumple, en caso contrario, arroja una exception
		User existingUser = repository.findByEmail(user.getEmail());
		if(existingUser != null) {
			throw new IllegalStateException("El email registrado ya esta asociado a una cuenta existente, intente con otro");
		}
			
			return repository.save(user);
		}
	/*
	 * Crear método PUT para actualizar la info de usuario 
	 * Debemos iterar cada key/value del objeto para observar las modificaciones y aplicarlas en el atributo especifico.Para ello nos calemos de un metodo .map
	 * */	
	
	public User replaceUser(User user, Long id) {
		return repository.findById(id)
				.map(userMap -> { //función lambda
					userMap.setNombre(user.getNombre());
					userMap.setApellido(user.getApellido());
					userMap.setEmail(user.getEmail());
					userMap.setPassword(user.getPassword());
					userMap.setTelefono(user.getTelefono());
					userMap.setRol(user.getRol());
					userMap.setUrl_perfil_imagen(user.getUrl_perfil_imagen());
					return repository.save(userMap);
				})
				.orElseGet(()->{
					user.setId(id);
					return repository.save(user);
				});
	}
	
	/*
	 * Podemos buscar un usuario por medio de su correo para recuperar información. Para elllos dependemos de JPQL en el repository y ResponseEntity que se ejecutara en el controller
	 * Vamos a crear un métidi para buscar a un uruario por email `getUserbyEmail` 
	 * */
	
	public User getUserByEmail(String email) {
		return repository.findByEmail(email);
	}	
	
	
	
	}
	
	
	

