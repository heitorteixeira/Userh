package com.userh.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.userh.entity.User;
import com.userh.exception.UserNotFoundException;
import com.userh.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
    public List<User> findAll() {
       return userRepository.findAll();
    }

	public User insert(User user) {
		return userRepository.insert(user);
	}

	public @Valid User update(User userNew) {
		User user = find(userNew.getId());
		user.setName(userNew.getName());
		user.setEmail(userNew.getEmail());
		user.setPassword(userNew.getPassword());
		user.setAddress(userNew.getAddress());
		user.setPhone(userNew.getPhone());
		user.setProfile(userNew.getProfile());
		
		return userRepository.save(user);
	}


	public void delete(Integer id) {
		userRepository.findById(id);
		
		userRepository.deleteById(id);
	}  

	public User find(Integer id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado! Id: " + id));
	}

	public Page<User> findPage(String nome, String email, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) , orderBy);
		
		return userRepository.findByNomeOrEmailPage(nome, email, pageRequest);
	}

	public void patch(Integer id) {
		// TODO: path
		
	}
}
