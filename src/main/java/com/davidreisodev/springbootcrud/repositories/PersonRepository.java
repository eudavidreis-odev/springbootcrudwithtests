package com.davidreisodev.springbootcrud.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidreisodev.springbootcrud.models.PersonModel;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel,UUID> {
    
}
