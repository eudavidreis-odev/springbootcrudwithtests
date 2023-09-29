package com.davidreisodev.springbootcrud.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidreisodev.springbootcrud.models.AddressModel;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel,UUID> {

    
}
