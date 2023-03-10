package com.bookshop.webTest;

import com.bookshop.TestConfigurationBookApp;
import com.bookshop.model.enums.BookingStatus;
import com.bookshop.model.enums.Role;
import com.bookshop.model.Booking;
import com.bookshop.model.Product;
import com.bookshop.model.UserEntity;
import com.bookshop.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import java.sql.Time;
import java.util.Date;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {TestConfigurationBookApp.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BookingWebTest {

	private String baseUri = "/booking";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	BookingRepository bookingRepository;

	Product product = new Product(1L, "Tale1", "goodBook", "H.H.Peterson", 10.0F, "http://path");
 	UserEntity user = new UserEntity(1L, "Piotr", Role.ADMIN, "gmail", "888", "street", "login", "password");
	Booking booking = new Booking(1L, product, user, "street", new Date(), new Time(120), BookingStatus.APPROVED, 1);

	@Test
	public void getBookingById_success() throws Exception {
		when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(booking));
		mockMvc.perform(MockMvcRequestBuilders
										.get("/booking/bookingId/{bookingId}", "1")
										.contentType(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void createBooking_success() throws Exception {
		when(bookingRepository.save(booking)).thenReturn(booking);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(booking);

		mockMvc.perform(MockMvcRequestBuilders
										.post(baseUri)
										.contentType(MediaType.APPLICATION_JSON_VALUE)
										.content(requestJson))
						.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void deleteProductById_success() throws Exception {
		doNothing().when(bookingRepository).deleteById(1L);
		mockMvc.perform(MockMvcRequestBuilders
										.delete("/booking/{bookingId}", "1")
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
