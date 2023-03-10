package com.bookshop.controller;

import com.bookshop.helpers.BookingInfo;
import com.bookshop.model.Booking;
import com.bookshop.helpers.BookingPerCustomer;
import com.bookshop.model.enums.BookingStatus;
import com.bookshop.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@GetMapping()
	List<Booking> getAllBookings() {
		return bookingService.getBookings();
	}

	@GetMapping("/bookingId/{bookingId}")
	Optional<Booking> getBookingById(@PathVariable Long bookingId) {
		return bookingService.getBookingById(bookingId);
	}

	@GetMapping("/userId/{userId}")
	List<Booking> getBookingPerUser(@PathVariable Long userId) {
		return bookingService.getBookingPerUser(userId);
	}

	@PostMapping
	Booking createBooking(@RequestBody Booking booking) {
		return bookingService.createBooking(booking);
	}

	@PostMapping("/bookProduct")
	Booking bookProductCustomer(@RequestBody BookingPerCustomer info) {
		return bookingService.bookProductCustomer(info);
	}

	@PutMapping("/{bookingId}")
	Booking editBooking(@RequestBody Booking booking, @PathVariable Long bookingId) {
		return bookingService.editBooking(booking, bookingId);
	}

	@PutMapping("/customer/{bookingId}")
	Booking editBookingCustomer(@RequestBody BookingInfo info, @PathVariable Long bookingId) {
		return bookingService.editBookingCustomer(info, bookingId);
	}

	@PutMapping("/status/{bookingId}")
	Booking setStatus(@RequestBody BookingStatus status, @PathVariable Long bookingId) {
		return bookingService.setStatus(status, bookingId);
	}

	@DeleteMapping("/{bookingId}")
	void cancelBooking(@PathVariable Long bookingId) {
		bookingService.cancelBooking(bookingId);
	}


}
