package com.example.demo.controller;

import com.example.demo.dto.AnimalRequest;
import com.example.demo.dto.AnimalResponse;
import com.example.demo.entity.Animal;
import com.example.demo.service.AnimalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @MockBean
    private AnimalService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void getAnimals() throws Exception {
        List<AnimalResponse> animals = new ArrayList<>();
        animals.add(new AnimalResponse(1L, "dog", "breed"));
        animals.add(new AnimalResponse(2L, "cat", "breed"));
        Page<AnimalResponse> page = new PageImpl<>(animals);
        when(service.getAllAnimals()).thenReturn(page);
        mvc.perform(get("http://localhost:8080/v1/api/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("dog"))
//                .andExpect(jsonPath("$.content[0].type").value("breed"))
//                .andExpect(jsonPath("$.content[1].name").value("cat"))
//                .andExpect(jsonPath("$.content[1].type").value("breed"))
                .andDo(print());

        //given

        //then
    }

    @Test
    void addAnimal() throws Exception {
       var animalR =  new AnimalRequest("Dod","Dog");
       var animal =  new Animal(animalR.name(),animalR.type());

       when(service.addAnimal(animalR)).thenReturn(animal);

mvc.perform(post("http://localhost:8080/v1/api/animals")
                .content(mapper.writeValueAsString(animalR))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dod"))
                .andExpect(jsonPath("$.type").value("Dog"))
                .andDo(print());
    }


    @Test
    void updateAnimal() throws Exception {
        var id =1L;
        var animalR =  new AnimalRequest("Dod1","Dog");


        var animal =  new Animal(1L,"Dod","Dog");
        when(service.updateAnimal(id,animalR)).thenReturn(animal);

        mvc.perform(   put("http://localhost:8080/v1/api/animals/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(animalR))
        ).andExpect(status().isOk());
    }

    @Test
    void testUpdateAnimal_InvalidId() throws Exception {
        // Arrange
        Long id = 100L;
        AnimalRequest animalRequest = new AnimalRequest("Updated Name", "Dog");
        when(service.updateAnimal(eq(id), any(AnimalRequest.class))).thenReturn(null);

        // Act
        mvc.perform(   put("http://localhost:8080/v1/api/animals/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(animalRequest))
        ).andExpect(status().isNotFound());
    }

    @Test
    void getAnimal() {

    }

    @Test
    void deleteAnimal() {
    }
}