package com.kk.spring5restwebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.kk.spring5restwebflux.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}
