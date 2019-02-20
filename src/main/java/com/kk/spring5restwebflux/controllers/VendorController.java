package com.kk.spring5restwebflux.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kk.spring5restwebflux.domain.Vendor;
import com.kk.spring5restwebflux.repository.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {
	
	private final VendorRepository vendorRepository;

	public VendorController(VendorRepository vendorRepository) {
		super();
		this.vendorRepository = vendorRepository;
	}
	
	@GetMapping("/api/v1/vendors")
	Flux<Vendor> listVendors(){
		
		return vendorRepository.findAll();
	}
	
	@GetMapping("/api/v1/vendors/{id}")
	Mono<Vendor> getVendorById(@PathVariable String id){
		
		return vendorRepository.findById(id);
	}

}
