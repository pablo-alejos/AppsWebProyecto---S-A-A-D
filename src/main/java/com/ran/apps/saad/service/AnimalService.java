package com.ran.apps.saad.service;

import java.util.List;
import java.util.Optional;

import com.ran.apps.saad.model.Animal;
import com.ran.apps.saad.repository.AnimalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repo;

    /**
     * Get a List<Animal> with all the registered animal
     * 
     * @return list
     */
    public List<Animal> getAnimals() {
        return (List<Animal>) repo.findAll();
    }

    /**
     * Get an Animal entity by the given id
     * 
     * @param id 
     * @return the requested animal or none 
     */
    public Animal getAnimalById(Integer id) {
        Optional<Animal> animal = repo.findById(id);
        if (animal.isPresent()) {
            return repo.findById(id).get();
        }
        return animal.get();
    }

    /**
     * Create or update Animal
     * 
     * @param e an animal entity to be saved or updated
     * @return the entered animal entity
     */
    public Animal saveAnimal(Animal e) {
        /* Create new tuple */
        if (e.getId() == null) {
            e = repo.save(e);
            return e;
        } else {

            Optional<Animal> animal = repo.findById(e.getId());

            if (animal.isPresent()) {

                /** builds new animal entity for updating */
                Animal newAnimal = animal.get();
                newAnimal.setId(e.getId());
                newAnimal.setType(e.getType());
                newAnimal.setRace(e.getRace());
                newAnimal.setColor(e.getColor());
                newAnimal.setFur(e.getFur());
                newAnimal.setDate(e.getDate());
                newAnimal.setColor(e.getColor());
                newAnimal.setVaccinated(e.isVaccinated());
                newAnimal.setAdopted(e.isAdopted());
                newAnimal.setResponsable(e.getResponsable());
                newAnimal.setImg(e.getImg());
                /* Delete old tuple */
                repo.deleteById(e.getId());

                /* Save new tuple */
                newAnimal = repo.save(newAnimal);

                return newAnimal;
            } else {
                e = repo.save(e);
                return e;
            }
        }
    }

    /**
     * Delete an animal tuple by the given id
     * 
     * @param id
     */
    public void deleteAnimal(Integer id) {

        Optional<Animal> animal = repo.findById(id);

        if (animal.isPresent()) {
            repo.deleteById(id);
        }
    }
}