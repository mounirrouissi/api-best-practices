package com.example.demo.service;

import com.example.demo.Handler.AnimalNotFound;
import com.example.demo.Handler.AnimalSaveException;
import com.example.demo.dto.AnimalRequest;
import com.example.demo.dto.AnimalResponse;
import com.example.demo.entity.Animal;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @InjectMocks
    private AnimalService animalService;
    @Mock
    private AnimalRepo animalRepo;


    @Test
    public void testGetAllAnimals() {
        // Given
        Animal animal1 = new Animal(1L, "Lion", "Mammal");
        Animal animal2 = new Animal(2L, "Elephant", "Mammal");
        List<Animal> animals = Arrays.asList(animal1, animal2);
        Page<Animal> animalPage = new PageImpl<>(animals);

        when(animalRepo.findAll(PageRequest.of(0, 15,
                Sort.by("name").descending())))
                .thenReturn(animalPage);

        // When
        Page<AnimalResponse> result = animalService.getAllAnimals();

        // Then
        assertEquals(2, result.getContent().size());
       // assertEquals(1L, result.getContent().get(0).id());
        System.out.println(result.getContent());

    }

    @Test
    public void testAddAnimal_Success() {
        // Arrange
        AnimalRequest animalRequest = new AnimalRequest("Lion", "Mammal");

        Animal expectedAnimal = new Animal(animalRequest.name(), animalRequest.type());
        Mockito.when(animalRepo.save(Mockito.any(Animal.class))).thenReturn(expectedAnimal);

        // Act
        Animal savedAnimal = animalService.addAnimal(animalRequest);

        // Assert
        Assertions.assertNotNull(savedAnimal);
        Assertions.assertEquals("Lion", savedAnimal.getName());
        Assertions.assertEquals("Mammal", savedAnimal.getType());
        Mockito.verify(animalRepo,times(1)).save(Mockito.any(Animal.class));
        }
    void testAddAnimal() {
        //given

        AnimalRequest animal1 = new AnimalRequest("animal","animal");
        Animal animal = new Animal(animal1.name(),animal1.type());
        //When
        when(animalRepo.save(animal)).thenReturn(animal);

        Animal animal2 = animalService.addAnimal(animal1);

        //then
        assertEquals(animal1.name(), animal2.getName());
    }
    //test for delete
    @Test
    public void testDeleteAnimal() {
        // Arrange
        Long animalId = 1L;

        // Act

        when(animalRepo.findById(animalId)).thenReturn(Optional.empty());
        // Assert
        assertThrows(AnimalNotFound.class, () -> animalService.deleteAnimal(animalId));
        Mockito.verify(animalRepo, Mockito.times(1)).findById(animalId);
    }
    //test get animal
    @Test
    public void testGetAnimal() {
        // Arrange
        Long animalId = 1L;
        Animal animal = new Animal(animalId, "Lion", "Mammal");

        // Act
        when(animalRepo.findById(animalId)).thenReturn(Optional.of(animal));


        Animal animalResponse = animalService.getAnimal(animalId);

        // Assert
        assertEquals(animalId, animalResponse.getId());

    }



}