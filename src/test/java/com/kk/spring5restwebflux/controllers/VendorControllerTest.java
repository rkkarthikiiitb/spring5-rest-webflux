package com.kk.spring5restwebflux.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.kk.spring5restwebflux.domain.Vendor;
import com.kk.spring5restwebflux.repository.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {

	VendorController controller;
	VendorRepository vendorRepository;
	WebTestClient testClient;

	@Before
	public void setUp() throws Exception {

		vendorRepository = Mockito.mock(VendorRepository.class);
		controller = new VendorController(vendorRepository);
		testClient = WebTestClient.bindToController(controller).build();
	}

	@Test
	public void testListVendors() {

		BDDMockito.given(controller.listVendors())
				.willReturn(Flux.just(Vendor.builder().firstName("Max").lastName("Meyer").build(),
						Vendor.builder().firstName("Carl").lastName("Jenkinson").build()));

		testClient.get().uri("/api/v1/vendors").exchange().expectBodyList(Vendor.class).hasSize(2);
	}

	@Test
	public void testGetVendorById() {

		BDDMockito.given(controller.getVendorById("id"))
				.willReturn(Mono.just(Vendor.builder().firstName("Max").lastName("M").build()));
		
		testClient.get()
					.uri("/api/v1/vendors/id")
					.exchange()
					.expectBody(Vendor.class);
					
	}

}
