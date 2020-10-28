package com.userh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userh.entity.User;
import com.userh.enums.ProfileEnum;
import com.userh.exception.UserNotFoundException;
import com.userh.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    
	private static final String URL_BASE = "/users/";
	private static final Integer ID_USER_INSERT = 10;
	private static final String NAME_USER_INSERT = "Heitor Elias Teixeira";
	private static final String EMAIL_USER = "heitor.teixeira@hotmail";
	
	private static final Integer ID_USER_UPDATE = 5;
	private static final String NAME_USER_UPDATE = "Roberto Silva";
	
	private static final Integer ID_USER_DELETE = 99;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
    
	
	@Before
    public void setup() {
		addUser();
	}
	
	@Test
	public void testFindAllUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/findAll"))
				.andExpect(status().isOk());
	}
	
	@Test
    public void testFindUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + ID_USER_INSERT)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles= {"ADMIN"})
    public void testInsertUser() throws Exception {
		User user = new User();
		user.setId(12);
		user.setName(NAME_USER_INSERT);
		user.setEmail("heitor@hotmail.com");
		user.setPassword("1234");
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(getJsonFromPost(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
		deleteUser(12);
	}
	
	@Test
	@WithMockUser(roles= {"ADMIN"})
    public void testUpdateUser() throws Exception {
		
		User user = new User();
		user.setId(ID_USER_UPDATE);
		user.setName(NAME_USER_UPDATE);
		
		this.userService.insert(user);
		
		mockMvc.perform(MockMvcRequestBuilders.put(URL_BASE + ID_USER_UPDATE)
				.content(this.getJsonFromPost(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + ID_USER_UPDATE))
		.andExpect(status().isOk())        
		.andExpect(jsonPath("$.name", equalTo(NAME_USER_UPDATE)));
		
		this.userService.delete(user.getId());
	}
	
	@Test
	@WithMockUser(roles= {"ADMIN"})
    public void testDeleteUser() throws Exception {
		User user = new User();
		user.setId(ID_USER_DELETE);
		user.setName("User to Delete");
		
		this.userService.insert(user);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_USER_DELETE)
				.content(this.getJsonFromPost(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

		Assertions.assertThatThrownBy(() -> mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + ID_USER_DELETE))
				.andExpect(status().isOk()))
				.hasCause(new UserNotFoundException("Usuário não encontrado! Id: " + ID_USER_DELETE));
				
	}
	
	@Test
	public void testFindPageUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "page?name=He&email="+ EMAIL_USER + "&orderBy=name&direction=DESC"))
				.andExpect(status().isOk());
	}

	@After
    public void tearDown() {
		deleteUser(ID_USER_INSERT);
	}
	
	
	private void deleteUser(Integer id) {
		this.userService.delete(id);
	}
	
	private void addUser() {
		this.userService.insert(getUser());
	}
	
	private String getJsonFromPost(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

	private User getUser() {
		User user = new User();
		user.setId(ID_USER_INSERT);
		user.setName(NAME_USER_INSERT);
		user.setEmail(EMAIL_USER);
		user.setPassword("1234");
		user.setAddress("Street 1");
		user.setPhone("48991168851");
		Set<Integer> profiles = new HashSet<>();
		profiles.add(ProfileEnum.ADMIN.getCode());
		user.setProfile(profiles);
		
		return user;
	}
	
	
}
