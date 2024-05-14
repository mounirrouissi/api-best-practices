package com.example.demo.service;

import java.util.logging.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.Handler.AnimalNotFound;
import com.example.demo.Handler.AnimalSaveException;
import com.example.demo.dto.AnimalRequest;
import com.example.demo.dto.AnimalResponse;
import com.example.demo.entity.Animal;


@Service
public class AnimalService {
    private final AnimalRepo animalRepo;
    private final Logger logger = Logger.getLogger(String.valueOf(AnimalService.class));

    public AnimalService(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    public Page<AnimalResponse> getAllAnimals() {
        logger.info("retrieving all animals");
        return animalRepo.findAll(PageRequest.of(0, 15, Sort.by("name").descending()))
        .map(
            animal -> new AnimalResponse(animal.getId(), animal.getName(), animal.getType())
        );
    }

        public Animal addAnimal(AnimalRequest animal) {
        logger.info("saving animal: " + animal);
        var animal1 = new Animal(animal.name(), animal.type());
        try {
            return animalRepo.save(animal1); 
        } catch (Exception e) {
            throw new AnimalSaveException("Error saving animal", e);
        }
    }


    public void deleteAnimal(Long id) {
        animalRepo.findById(id).ifPresentOrElse(animalRepo::delete,
         () -> {throw new AnimalNotFound("No animal found with id " + id);});
    }

    public Animal getAnimal(Long id) {
        return animalRepo.findById(id)
                .orElseThrow(() -> new AnimalNotFound("No animal found with id " + id));
    }

    public Animal updateAnimal(Long id, AnimalRequest animal) {
        return animalRepo.findById(id)
                .map(existingAnimal -> {
                    existingAnimal.setName(animal.name());
                    existingAnimal.setType(animal.type());
                    return animalRepo.save(existingAnimal);
                })
                .orElseThrow(() -> new AnimalNotFound("No animal found with id " + id));
    }
}
