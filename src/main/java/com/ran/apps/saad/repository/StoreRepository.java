package com.ran.apps.saad.repository;

import com.ran.apps.saad.model.Store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer> {
}
