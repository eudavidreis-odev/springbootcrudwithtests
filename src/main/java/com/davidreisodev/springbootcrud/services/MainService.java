package com.davidreisodev.springbootcrud.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.davidreisodev.springbootcrud.controllers.MainController;
import com.davidreisodev.springbootcrud.dtos.AddressRecordDto;
import com.davidreisodev.springbootcrud.dtos.PersonRecordDto;
import com.davidreisodev.springbootcrud.models.AddressModel;
import com.davidreisodev.springbootcrud.models.PersonModel;
import com.davidreisodev.springbootcrud.repositories.AddressRepository;
import com.davidreisodev.springbootcrud.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * MainService é a classe que executa a lógica de negócio, ela recebe a anotação @Service, e também é responsável por fazer 
 * o intermédio entre a camada do MainController e da camada de Repository (PersonRepository e AddressRepository).
 *
 * @see Service
 * @see MainController
 * @see PersonRepository
 * @see AddressRepository 
 */
@Service
public class MainService {

    private static final String PERSON_LIST = "Lista de pessoas";
    private static final String PERSON_DATA = "Dados da pessoa";

    @Autowired
    PersonRepository personRepository;
    @Autowired
    AddressRepository addressRepository;

    /**
     * Adiciona uma pessoa ao banco de dados.
     * @param personRegisterDto DTO da pessoa.
     * @return Optional
     */
    public Optional<PersonModel> addPerson(PersonRecordDto personRegisterDto){
        var personModel = new PersonModel();
        BeanUtils.copyProperties(personRegisterDto, personModel);

        personModel.setAddressess(configMainAddress(personModel.getAddressess()));

        try {
                
            Optional<PersonModel> personOptional = Optional.of(personRepository.save(personModel));

            if(personOptional.isPresent()) personOptional.get().add(linkTo(methodOn(MainController.class).getAllPersons()).withRel(PERSON_LIST));
            

            return personOptional;
        } catch (Exception e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    /**
     * Retorna todas as pessoas do banco de dados, uma lista vazia caso não seja encontrada nenhuma ocorrência.
     * @return List
     */
    public List<PersonModel> getAllPersons(){
        List<PersonModel> personsList = personRepository.findAll();

        if(!personsList.isEmpty()){
            for(PersonModel person:personsList){
                UUID id = person.getIdPerson();
                person.add(linkTo(methodOn(MainController.class).getPersonById(id)).withSelfRel());
            }
        }

        return personsList;
    }

    /**
     * Busca e retorna uma pessoa com base no seu UUID.
     * @param id UUID da pessoa.
     * @return Optional.
     */
    public Optional<PersonModel> getPersonById(UUID id){        
        Optional<PersonModel> person = personRepository.findById(id);
        
        if(person.isEmpty()){
            return person;
        }
        person.get().add(linkTo(methodOn(MainController.class).getAllPersons()).withRel(PERSON_LIST));
        return person;
    }

    /**
     * Atualiza o cadastro de uma pessoa, com base no seu UUID.
     * @param id UUID da pessoa.
     * @param person DTO da pessoa.
     * @return Optional.
     */
    public Optional<PersonModel> updatePersonById(UUID id, PersonRecordDto person){
    Optional<PersonModel> optionalPersonModel;
        try {
            optionalPersonModel = personRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        
        if(optionalPersonModel.isEmpty()){
            return optionalPersonModel;
        }

        var personModel = optionalPersonModel.get();
        var list = personModel.getAddressess();

        BeanUtils.copyProperties(person, personModel);
        personModel.setAddressess(configMainAddress(personModel.getAddressess()));

        List<UUID> uuids = new ArrayList<>();
        list.forEach(address -> uuids.add(address.getIdAddress()));

        addressRepository.deleteAllByIdInBatch(uuids);

        Optional<PersonModel> optionalPersonUpdated = Optional.of(personRepository.save(personModel));
        if(optionalPersonUpdated.isPresent()){
            optionalPersonUpdated.get().add(linkTo(methodOn(MainController.class).getAllPersons()).withRel(PERSON_LIST));
            
            optionalPersonUpdated.get().getAddressess().forEach(addressModel -> addressModel.add(linkTo(methodOn(MainController.class).getPersonAddressesById(id)).withRel(PERSON_DATA)));

        } 

        return optionalPersonUpdated;

    }

    /**
     * Exclui uma pessoa do banco de dados com base no seu UUID.
     * @param id UUID da pessoa.
     * @return Optional.
     */
    public Optional<PersonModel> deletePersonById(UUID id){
        try{
        Optional<PersonModel> person = personRepository.findById(id);
        
        if(person.isEmpty()){
            return person;
        }
        personRepository.delete(person.get());

        return person;
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Adiciona um endereço a uma pessoa, com base no seu UUID.
     * @param id UUID da pessoa;
     * @param addressRecordDto DTO do endereço.
     * @return Optional.
     */
    public Optional<List<AddressModel>>  addAddressToPersonById(UUID id, AddressRecordDto addressRecordDto){
        Optional<PersonModel> personOptional;
        try {
            personOptional = personRepository.findById(id);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }


        if(personOptional.isEmpty()){
            return Optional.empty();
        }

        var addressModel = new AddressModel();
        BeanUtils.copyProperties(addressRecordDto, addressModel);

        if(addressModel.getMainAddress()){
            personOptional.get().getAddressess().add(0,addressModel);
            personOptional.get().setAddressess(configMainAddress(personOptional.get().getAddressess()));
        } else personOptional.get().getAddressess().add(addressModel);


        try {
            personOptional = Optional.of(personRepository.save(personOptional.get()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

        if(personOptional.isEmpty()) return Optional.empty();

        Optional<List<AddressModel>> addressOptional = Optional.of(personOptional.get().getAddressess());
        
        List<AddressModel> resultAddressMoodels = new ArrayList<>();
        for(AddressModel am : addressOptional.get()){
            am.add(linkTo(methodOn(MainController.class).getPersonById(id)).withRel(PERSON_DATA));
            resultAddressMoodels.add(am);
        }

    
        

        return Optional.of(resultAddressMoodels);
    

    }

    /**
     * Define um endereço como principal, com base no UUID da pessoa que possui o endereço, e no UUID do endereço.
     * @param idPerson UUID da pessoa. 
     * @param idAddress UUID do endereço.
     * @return Optional.
     */
    public Optional<List<AddressModel>> setMainAddressToPersonById(UUID idPerson, UUID idAddress){
        Optional<PersonModel> personOptional ;
        try {
           personOptional = personRepository.findById(idPerson);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        
        if(personOptional.isEmpty()) return Optional.empty();
        PersonModel personModel = personOptional.get();

        List<AddressModel> tempAddressess = new ArrayList<>();
        for(AddressModel am : personOptional.get().getAddressess()){
            am.setMainAddress(am.getIdAddress().compareTo(idAddress) == 0);    
            tempAddressess.add(am);   
        }
        personModel.setAddressess(tempAddressess);

        try {
            personOptional = Optional.of(personRepository.save(personOptional.get()));

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        if(personOptional.isEmpty()) return Optional.empty();

        personModel = personOptional.get();


        Optional<List<AddressModel>> addressOptional = Optional.empty();
        
        addressOptional = Optional.of(personOptional.get().getAddressess());

        tempAddressess = new ArrayList<>();
        for(AddressModel am : addressOptional.get()){
            am.add(linkTo(methodOn(MainController.class).getPersonById(idPerson)).withRel(PERSON_DATA));
            tempAddressess.add(am);
        }



        return Optional.of(tempAddressess);
    }

    /**
     * Retorna todos os endereços de uma pessoa com base e seu UUID.
     * @param id UUID da pessoa.
     * @return Optional.
     */
    public Optional<List<AddressModel>> getAllAddressesFromPersonById(UUID id){
        Optional<PersonModel> personOptional;
        try {
            personOptional = personRepository.findById(id);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

        if(personOptional.isEmpty()) return Optional.empty();

        Optional<List<AddressModel>> addressOptional = Optional.empty();
        
        addressOptional = Optional.of(personOptional.get().getAddressess());

        List<AddressModel> tempAddressess = new ArrayList<>();
        for(AddressModel am : addressOptional.get()){
            am.add(linkTo(methodOn(MainController.class).getPersonById(id)).withRel(PERSON_DATA));
            tempAddressess.add(am);
        }
        personOptional.get().setAddressess(tempAddressess); 

        return addressOptional;

    }

    /**
     * Configura um endereço como principal, caso ele não esteja configurado.
     *  Caso haja mais de um endereço principal, todos os outros endereços são alterados para não serem principais, e apenas o primeiro é mantido.
     * @param addresses Lista de endereços.
     * @return List.
     */
    public List<AddressModel> configMainAddress(List<AddressModel> addresses){
        int mainAddressesSize = (int) addresses.stream().filter(AddressModel::getMainAddress).count();

        if(mainAddressesSize == 1)return addresses;
        else if(mainAddressesSize == 0){
            addresses.get(0).setMainAddress(true);
        }else{
            addresses.forEach(address -> address.setMainAddress(false));
            addresses.get(0).setMainAddress(true);
        }

        return addresses;
    }
}
