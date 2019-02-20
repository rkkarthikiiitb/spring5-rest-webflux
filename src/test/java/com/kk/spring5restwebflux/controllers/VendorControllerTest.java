package com.kk.spring5restwebflux.controllers;

import org.junit.Before;
import static org.mockito.ArgumentMatchers.any;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
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

		BDDMockito.given(vendorRepository.findAll())
				.willReturn(Flux.just(Vendor.builder().firstName("Max").lastName("Meyer").build(),
						Vendor.builder().firstName("Carl").lastName("Jenkinson").build()));

		testClient.get().uri("/api/v1/vendors").exchange().expectBodyList(Vendor.class).hasSize(2);
	}

	@Test
	public void testGetVendorById() {

		BDDMockito.given(vendorRepository.findById("id"))
				.willReturn(Mono.just(Vendor.builder().firstName("Max").lastName("M").build()));

		testClient.get().uri("/api/v1/vendors/id").exchange().expectBody(Vendor.class);

	}

	@Test
	public void testCreatVendor() {

		BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(Vendor.builder().build()));

		Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Manny").build());

		testClient.post().uri("/api/v1/vendors").body(vendorMono, Vendor.class).exchange().expectStatus().isCreated();

	}

	public void testUpdateVendor() {

		BDDMockito.given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Leo").build());
		testClient.put().uri("/api/v1/vendors/id").body(vendorMono,Vendor.class).exchange().expectStatus().isOk();
	}

}
