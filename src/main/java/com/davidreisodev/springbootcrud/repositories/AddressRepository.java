package com.davidreisodev.springbootcrud.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidreisodev.springbootcrud.models.AddressModel;

/**
 * AddressRepository é uma interface que extende JpaRepository, que é uma interface do Spring JPA que possui vários métodos prontos para trabalhar com o banco de dados. *
 * Esta interface é responsável por manipular os dados relacionados ao AddressModel ou tb_address.
 *
 * @see JpaRepository 
 **/
@Repository
public interface AddressRepository extends JpaRepository<AddressModel,UUID> {

    
}
