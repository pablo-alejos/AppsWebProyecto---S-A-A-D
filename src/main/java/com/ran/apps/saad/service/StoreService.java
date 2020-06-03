package com.ran.apps.saad.service;

import java.util.List;
import java.util.Optional;

import com.ran.apps.saad.model.Store;
import com.ran.apps.saad.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreRepository repo;

    /**
     * Get a List<Store> with all the registered Store
     * 
     * @return list
     */
    public List<Store> getStores() {
        return (List<Store>) repo.findAll();
    }

    /**
     * Get an Store entity by the given id
     * 
     * @param id 
     * @return the requested Store or none 
     */
    public Store getStoreById(Integer id) {
        Optional<Store> store = repo.findById(id);
        if (store.isPresent()) {
            return repo.findById(id).get();
        }
        return store.get();
    }

    /**
     * Create or update store
     * 
     * @param e an store entity to be saved or updated
     * @return the entered store entity
     */
    public Store saveStore(Store e) {
        /* Create new tuple */
        if (e.getId() == null) {
            e = repo.save(e);
            return e;
        } else {

            Optional<Store> store = repo.findById(e.getId());

            if (store.isPresent()) {

                /** builds new animal entity for updating */
                Store newStore = store.get();
                newStore.setId(e.getId());
                newStore.setNombre(e.getNombre());
                newStore.setDireccion(e.getDireccion());
                /* Delete old tuple */
                repo.deleteById(e.getId());

                /* Save new tuple */
                newStore = repo.save(newStore);

                return newStore;
            } else {
                e = repo.save(e);
                return e;
            }
        }
    }

    /**
     * Delete an store tuple by the given id
     * 
     * @param id
     */
    public void deleteStore(Integer id) {

        Optional<Store> store = repo.findById(id);

        if (store.isPresent()) {
            repo.deleteById(id);
        }
    }
}