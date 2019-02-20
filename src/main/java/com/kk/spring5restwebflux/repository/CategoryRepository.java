package com.kk.spring5restwebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.kk.spring5restwebflux.domain.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
