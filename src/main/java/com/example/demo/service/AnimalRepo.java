package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Animal;

public interface AnimalRepo extends JpaRepository<Animal,Long> {
}
