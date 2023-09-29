package com.davidreisodev.springbootcrud.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.davidreisodev.springbootcrud.dtos.AddressRecordDto;
import com.davidreisodev.springbootcrud.dtos.PersonRegisterRecordDto;
import com.davidreisodev.springbootcrud.models.AddressModel;
import com.davidreisodev.springbootcrud.models.PersonModel;
import com.davidreisodev.springbootcrud.services.MainService;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
public class PersonController {
    
    private static final String PERSON_NOT_FOUND = "Pessoa não encontrada.";
    private static final String PRODUCT_REMOVED = "Pessoa removida comm sucesso.";

    @Autowired
    MainService personService;



    @PostMapping("/persons")
    public ResponseEntity<PersonModel> addPerson(@RequestBody @Valid PersonRegisterRecordDto person) {
        Optional<PersonModel> productModel = personService.addPerson(person);

        if(productModel.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(productModel.get());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/persons")
    public ResponseEntity<List<PersonModel>> getAllPersons(){
        List<PersonModel> personList = personService.getAllPersons();

        return ResponseEntity.status(HttpStatus.OK).body(personList);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Object> getPersonById(@PathVariable(value="id") @NotBlank UUID id){
        Optional<PersonModel> person = personService.getPersonById(id);

        if (person.isPresent())return ResponseEntity.status(HttpStatus.OK).body(person.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSON_NOT_FOUND);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Object> updatePersonById(@PathVariable(value="id") UUID id, @RequestBody @Valid PersonRegisterRecordDto person){
        Optional<PersonModel> personModel = personService.updatePersonById(id, person);

        if(personModel.isPresent())return ResponseEntity.status(HttpStatus.OK).body(personModel);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSON_NOT_FOUND);
        
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Object> deletePersonById(@PathVariable(value="id") UUID id){
        Optional<PersonModel> personModel = personService.deletePersonById(id);

        if(personModel.isPresent())return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_REMOVED);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSON_NOT_FOUND);
    }

    @GetMapping("/person_addresses/{id}")
    public ResponseEntity<Object> getPersonAddressesById(@PathVariable(value="id") UUID id){
        Optional<List<AddressModel>> personAddresses = personService.getAllAddressesFromPersonById(id);
        if(personAddresses.isPresent())return ResponseEntity.status(HttpStatus.OK).body(personAddresses.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSON_NOT_FOUND);
    }

    @PutMapping("/person_addresses/main/{idperson}/{idaddress}")
    public ResponseEntity<Object> setPersonMainAddress(@PathVariable(value="idperson") UUID idPerson,@PathVariable(value="idaddress") UUID idAddress){
        Optional<List<AddressModel>> personAddresses = personService.setMainAddressToPersonById(idPerson, idAddress);
        if(personAddresses.isPresent())return ResponseEntity.status(HttpStatus.OK).body(personAddresses.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSON_NOT_FOUND);
    }

    @PostMapping("/person_addresses/{id}")
    public ResponseEntity<Object> addPersonAddressById(@PathVariable(value="id") UUID id,@RequestBody @Valid AddressRecordDto address){
        Optional<List<AddressModel>> addressModel = personService.addAddressToPersonById(id, address);

        if(addressModel.isPresent())return ResponseEntity.status(HttpStatus.CREATED).body(addressModel.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PERSON_NOT_FOUND);
    }

}
