package com.kk.spring5restwebflux.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/vendors")
	Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream){
		
		return vendorRepository.saveAll(vendorStream).then();
	}
	
	@PutMapping("/api/v1/vendors/{id}")
	Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor){
		vendor.setId(id);
		return vendorRepository.save(vendor);
		
	}
	
	@PatchMapping("/api/v1/vendors/{id}")
	Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor){
		Vendor foundVendor = vendorRepository.findById(id).block();
		
		if(foundVendor.getFirstName() != vendor.getFirstName()) {
			
			foundVendor.setId(vendor.getId());
			
			return vendorRepository.save(foundVendor);
			
		}
		return Mono.just(foundVendor);
		
	}
	
	

}
