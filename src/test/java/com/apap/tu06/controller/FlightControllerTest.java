package com.apap.tu06.controller;

import java.util.Date;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.apap.tu06.model.FlightModel;
import com.apap.tu06.service.FlightService;
import com.apap.tu06.service.PilotService;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightController.class)

public class FlightControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PilotService pilotService;
	
	@MockBean
	private FlightService flightService;

	@Test
	public void givenFlightNumber_whenViewFlight_thenReturnJsonFlight() throws Exception {
		//Given
		FlightModel flightModel = new FlightModel();
		flightModel.setFlightNumber("I765");
		flightModel.setOrigin("Jakarta");
		flightModel.setDestination("Bali");
		flightModel.setTime(new Date(new java.util.Date().getTime()));

		Optional<FlightModel> flight = Optional.of(flightModel);
		Mockito.when(flightService.getFlightDetailByFlightNumber(flight.get().getFlightNumber())).thenReturn(flight);
		
		//When
		
		mvc.perform(MockMvcRequestBuilders.get("/flight/view")
				.param("flightNumber", flight.get().getFlightNumber())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber", Matchers.is(flight.get().getFlightNumber())));

	}

}
