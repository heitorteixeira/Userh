package com.userh.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.userh.dto.UserDTO;
import com.userh.entities.User;
import com.userh.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping(value="/users/findAll")
	public ResponseEntity<List<User>> findAll() {
		List<User> user = userService.findAll();
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping(value="/users/{id}")
	public ResponseEntity<User> find(@PathVariable Integer id) {
		User user = userService.find(id);
		
		return ResponseEntity.ok(user);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(value="/users/")
	public ResponseEntity<Void> insert(@RequestBody User user){
		user = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/users/{id}")
	public ResponseEntity<Void> update(@RequestBody User user, @PathVariable Integer id){
		user.setId(id);
		user = userService.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/users/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PatchMapping(value="/users/{id}")
	public ResponseEntity<Void> patch(@RequestBody UserDTO dto, @PathVariable Integer id) {
		userService.patch(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value="/users/page")
	public ResponseEntity<Page<User>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="email", defaultValue="") String email, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<User> list = userService.findPage(nome, email, page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(list);
	}
}
