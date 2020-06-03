package com.ran.apps.saad.repository;

import com.ran.apps.saad.model.Animal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal,Integer> {
}