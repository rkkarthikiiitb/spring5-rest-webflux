package com.kk.spring5restwebflux.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.kk.spring5restwebflux.domain.Category;
import com.kk.spring5restwebflux.repository.CategoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;

public class CategoryControllerTest {

	WebTestClient webTestClient;
	CategoryController categoryController;
	CategoryRepository categoryRepository;

	@Before
	public void setUp() throws Exception {

		categoryRepository = Mockito.mock(CategoryRepository.class);
		categoryController = new CategoryController(categoryRepository);
		webTestClient = WebTestClient.bindToController(categoryController).build();
	}

	@Test
	public void testList() {
		BDDMockito.given(categoryRepository.findAll()).willReturn(Flux.just(Category.builder().description("cat1").build(),
				Category.builder().description("Cat2").build()));

		webTestClient.get().uri("/api/v1/categories").exchange().expectBodyList(Category.class).hasSize(2);
	}

	@Test
	public void testGetCategoryById() {
		BDDMockito.given(categoryRepository.findById("id"))
				.willReturn(Mono.just(Category.builder().description("cat").build()));

		webTestClient.get().uri("/api/v1/categories/id").exchange().expectBody(Category.class);
	}

	@Test
	public void testCreateCategory() {

		BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(Category.builder().build()));
		Mono<Category> cat1 = Mono.just(Category.builder().description("cat1").build());
		
		webTestClient.post()
					.uri("/api/v1/categories")
					.body(cat1,Category.class)
					.exchange()
					.expectStatus()
					.isCreated();

	}

}
