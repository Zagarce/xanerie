package org.xianerie.xianierie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xianerie.xianierie.model.User;
import org.xianerie.xianierie.service.UserService;



@RestController 
@RequestMapping("admin") 
@CrossOrigin(origins = {"http://127.0.0.1:5501"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE})
public class UserController {
	
	private UserService userService;
	
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;	
	}
	

	@GetMapping("users")
	public List <User> getUser(){
		return userService.allUsers();
		}
 	
	//path variable ya que buscamos por id, y los id son diferentes, entonces necesito definir el endpoint entre llaves, y dentro del metodo debo crear una notación @PathVariable
	@GetMapping("users/{id}")
	public User getOne(@PathVariable (name = "id") Long id) {
		return userService.getOne(id);
	}
	
	//Delete, de tipo void y define el path variable 
	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable(name= "id")Long id) {
		userService.deleteUser(id);
	}
	
	//POST, para crear un nuevo usuario
	@PostMapping("users")
		public User newUser(@RequestBody User newUser) {
			return userService.addUser(newUser);
	}
	
	//Put, modificar/actualizar a un usuario existente
		@PutMapping("users/{id}")
		public User replaceUser(@RequestBody User user, @PathVariable(name = "id") Long id) {
			return userService.replaceUser(user, id);
		}
	
	
		/*
		 * -- ResponseEntity<> clase de SpringFramework que representa una respuesta HTTP personalizable. Permite controlar el body de la respuesta. Posee dos parámetros:
		 * 		1. Especifica el tipo de datos
		 * 		2. Especifica el código de estado HTTP `HttpStaus.method`
		 * -- @RequestParam anotación de springFramework que se utiliza para vincular parámetros de solicitud HTTP que se enviará a la respuesta. Es decir, permite controlar las llaves-valores dentro del parámetro  
		 * */
		@GetMapping("/users/byEmail")
		public ResponseEntity <User> getUserByEmail (@RequestParam(name = "email") String email){
			User user = userService.getUserByEmail(email);
			return new ResponseEntity<>(user, HttpStatus.OK);		
		}
}