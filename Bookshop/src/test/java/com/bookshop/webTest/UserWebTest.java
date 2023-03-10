package com.bookshop.webTest;

import com.bookshop.TestConfigurationBookApp;
import com.bookshop.model.enums.Role;
import com.bookshop.model.UserEntity;
import com.bookshop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {TestConfigurationBookApp.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserWebTest {

	private String baseUri = "/user";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;
 	UserEntity user = new UserEntity(1L, "Piotr", Role.ADMIN, "gmail", "888", "street", "login", "password");

	@Test
	public void getUserById_success() throws Exception {
		when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
		mockMvc.perform(MockMvcRequestBuilders
										.get("/user/userId/{userId}", "1")
										.contentType(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void createNewUser_success() throws Exception {
		when(userRepository.save(user)).thenReturn(user);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(user);

		mockMvc.perform(MockMvcRequestBuilders
										.post(baseUri)
										.contentType(MediaType.APPLICATION_JSON_VALUE)
										.content(requestJson))
						.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void deleteUser_success() throws Exception {
		doNothing().when(userRepository).deleteById(1L);
		mockMvc.perform(MockMvcRequestBuilders
										.delete("/user/{userId}", "1")
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
