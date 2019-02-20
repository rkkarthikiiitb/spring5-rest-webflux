package com.kk.spring5restwebflux.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kk.spring5restwebflux.domain.Category;
import com.kk.spring5restwebflux.domain.Vendor;
import com.kk.spring5restwebflux.repository.CategoryRepository;
import com.kk.spring5restwebflux.repository.VendorRepository;

@Component
public class BootStrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final VendorRepository vendorRepository;

	public BootStrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {

		this.categoryRepository = categoryRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		if (categoryRepository.count().block() == 0) {
			// load data

			System.out.println("####### LOADING DATA #########");

			categoryRepository.save(Category.builder().description("nuts").build()).block();
			categoryRepository.save(Category.builder().description("fruits").build()).block();
			categoryRepository.save(Category.builder().description("candies").build()).block();
			categoryRepository.save(Category.builder().description("breads").build()).block();
			categoryRepository.save(Category.builder().description("eggs").build()).block();

			System.out.println("...Loaded categories = " + categoryRepository.count().block());

			vendorRepository.save(Vendor.builder().firstName("Harry").lastName("Potter").build()).block();
			vendorRepository.save(Vendor.builder().firstName("Albus").lastName("Dumbledore").build()).block();
			vendorRepository.save(Vendor.builder().firstName("Lucious").lastName("Malfoy").build()).block();
			vendorRepository.save(Vendor.builder().firstName("Ron").lastName("Weasley").build()).block();
			vendorRepository.save(Vendor.builder().firstName("Hermione").lastName("Grangier").build()).block();
			
			System.out.println("...Loaded Vendors = "+ vendorRepository.count().block());

		}

	}

}
