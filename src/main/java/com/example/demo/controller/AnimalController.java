package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.AnimalResponse;
import com.example.demo.dto.GlobalPageableResponse;
import com.example.demo.dto.GlobalResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.example.demo.dto.AnimalRequest;
import com.example.demo.entity.Animal;
import com.example.demo.service.AnimalService;

@RestController
@RequestMapping("v1/api/animals")
@CrossOrigin("*")
public class AnimalController {
    
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<GlobalPageableResponse> getAnimals() {
        Page<AnimalResponse> allAnimals = animalService.getAllAnimals();
        GlobalPageableResponse response = new GlobalPageableResponse(allAnimals.getNumber(), allAnimals.getTotalPages(), allAnimals.getTotalElements(),allAnimals.getContent());
         return  ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Animal> addAnimal(@RequestBody  AnimalRequest animal) {
        return  ResponseEntity.ok().body(animalService.addAnimal(animal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody AnimalRequest animal) {
        return ResponseEntity.ok().body(animalService.updateAnimal(id, animal));
    }

    @GetMapping("/{id}")
    ResponseEntity<GlobalResponse> getAnimal(@PathVariable Long id){
        Animal animal = animalService.getAnimal(id);
        GlobalResponse response = new GlobalResponse("SUCCESS", "Animal fetched successfully", animal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
     
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok("Animal deleted successfully");
    }


}
