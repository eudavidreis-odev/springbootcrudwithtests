package com.davidreisodev.springbootcrud.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.davidreisodev.springbootcrud.dtos.AddressRecordDto;
import com.davidreisodev.springbootcrud.dtos.PersonRecordDto;
import com.davidreisodev.springbootcrud.models.AddressModel;
import com.davidreisodev.springbootcrud.models.PersonModel;
import com.davidreisodev.springbootcrud.repositories.AddressRepository;
import com.davidreisodev.springbootcrud.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MainServiceTest {


    @InjectMocks
    MainService service;

    @Mock
    PersonRepository personRepository;
    @Mock
    AddressRepository addressRepository;
    @Mock
    MainService serviceMock;

    PersonRecordDto personRegisterDto;
    
    AddressModel address1,address2,address3;
    
    List<AddressModel> addressModelList1;
    List<AddressModel> addressModelList2;

    PersonModel personModel1,personModel2;

    AddressRecordDto addressRecordDto;

    UUID uuid, addressUuid;
    
    @BeforeEach
    public void setUp(){
        Date dateDavid = Date.valueOf("1993-07-14");
        Date dateNatali = Date.valueOf("1998-05-03");

        uuid = UUID.fromString("27609141-8425-4fb7-bfe0-b2e561e2f685");
        addressUuid = UUID.fromString("91412760-8425-bfe0-4fb7-1e2f685b2e56");
        
        address1 = new AddressModel();
        address1.setLogradouro("Avenida Bandeirantes");
        address1.setCity("São Paulo");
        address1.setNumber("123");
        address1.setMainAddress(true);
        address1.setIdAddress(uuid);

        address2 = new AddressModel();
        address2.setLogradouro("Avenida Brasil");
        address2.setCity("Rio de Janeiro");
        address2.setNumber("321");
        address2.setMainAddress(false);
        address2.setIdAddress(addressUuid);
        

        address3 = address1;
        address3.setCep("12121-545");
        address3.setMainAddress(false);

        addressModelList1 = new ArrayList<AddressModel>();
        addressModelList2 = new ArrayList<AddressModel>();
        addressModelList1.add(address1);
        addressModelList1.add(address2); 
        addressModelList2.add(address2);
        address1.setMainAddress(false);
        addressModelList2.add(address1); 

        addressRecordDto = new AddressRecordDto("Avenida Bandeirantes","12440-457","555","Pindamonhangaba",true);
        personRegisterDto = new PersonRecordDto("David Reis",dateDavid,addressModelList1);
        

        personModel1 = new PersonModel("David Reis",dateDavid,addressModelList1);
        personModel1.setIdPerson(uuid);

        personModel2 = new PersonModel("Natali Almeida",dateNatali,addressModelList2);
        personModel2.setIdPerson(UUID.fromString("a33ee6c7-2c3e-4b66-8048-e08d1f2ff165"));



    };
    
    @Test
    void addNewPersonToDatabase(){
        var personNotCreated = personModel1;
        personNotCreated.setIdPerson(null);
        when(personRepository.save(personNotCreated)).thenReturn(personModel1);
        
        Optional<PersonModel> personOptional = service.addPerson(personRegisterDto);
    
        assertTrue(personOptional.isPresent(), "Sucesso ao cadastrar pessoa.");
    }

    @Test
    void throwsAnExceptionOnSavePersonOnDatabase(){
        when(personRepository.save(null)).thenThrow(IllegalArgumentException.class);
        
        Optional<PersonModel> personOptional = service.addPerson(personRegisterDto);
        
        assertTrue(personOptional.isEmpty(), "Falha ao cadastrar pessoa. Exception não tratada.");
    
    }

    @Test
    void returnPersonsList(){
        when(personRepository.findAll()).thenReturn(List.of(personModel1,personModel2));

        var persons = service.getAllPersons();
        
        assertTrue(persons.size() > 0, "Resultado não vazio.");
        assertEquals(2,persons.size(), "Sucesso ao buscar todas as pessoas.");
        assertTrue(!persons.get(0).getLinks().isEmpty(), "Os links HATEOAS foram adicionados com sucesso.");
    }

    @Test 
    void returnPersonById(){
        when(personRepository.findById(personModel1.getIdPerson())).thenReturn(Optional.of(personModel1));
        
        var person = service.getPersonById(personModel1.getIdPerson());

        assertTrue(person.isPresent(), "Sucesso ao buscar pessoa pelo id.");
        assertTrue(!person.get().getLinks().isEmpty(), "Os links HATEOAS foram adicionados com sucesso.");
    }


    @Test
    void updatePersonById(){
        when(personRepository.findById(personModel1.getIdPerson())).thenReturn(Optional.of(personModel1));
        when(personRepository.save(personModel1)).thenReturn(personModel1);
        
        var personOptional = service.updatePersonById(personModel1.getIdPerson(), personRegisterDto);
        
        assertTrue(personOptional.isPresent(), "Sucesso ao atualizar pessoa pelo id.");
        assertTrue(!personOptional.get().getLinks().isEmpty(),"Os links HATEOAS foram adicionados com sucesso.");
    }

    @Test
    void throwsAnExceptionOnUpdatePersonoById(){
        when(personRepository.findById(personModel1.getIdPerson())).thenThrow(IllegalArgumentException.class);

        var personOptional = service.updatePersonById(personModel1.getIdPerson(), personRegisterDto);

        assertTrue(personOptional.isEmpty(), "Falha ao atualizar pessoa pelo id. Exception tratada.");
    }    

    @Test
    void removePersonById(){
        when(personRepository.findById((personModel1.getIdPerson()))).thenReturn(Optional.of(personModel1));
        
        var deletedPersonOptional = service.deletePersonById(personModel1.getIdPerson()); 
        
        assertTrue(deletedPersonOptional.isPresent(), "Sucesso ao deletar pessoa pelo id.");
        assertEquals(personModel1, deletedPersonOptional.get(), "Pessoa deletada confere.");
    }

    @Test 
    void throwsAnExceptionOnRemovePersonById(){
        when(personRepository.findById((personModel1.getIdPerson()))).thenThrow(IllegalArgumentException.class);
        
        var deletedPersonOptional = service.deletePersonById(personModel1.getIdPerson());
        
        assertTrue(deletedPersonOptional.isEmpty(), "Erro ao deletar pessoa. Exception não tratada.");
    }

    @Test
    void addAddressToPersonById(){

        var updatedAddresses = personModel1.getAddressess();
        var updatedPerson = personModel1;
        
        updatedAddresses.add(address3);
        updatedPerson.setAddressess(updatedAddresses);

        when(personRepository.findById(personModel1.getIdPerson())).thenReturn(Optional.of(personModel1));

        when(personRepository.save(updatedPerson)).thenReturn(updatedPerson);
        
        var addressListOptional = service.addAddressToPersonById( personModel1.getIdPerson(), addressRecordDto);

        assertTrue(addressListOptional.isPresent(), "Erro ao adicionar endereço.");
        assertEquals(updatedAddresses.size(), addressListOptional.get().size() , "Quantidade de endereços errada.");
        assertTrue(!addressListOptional.get().get(0).getLinks().isEmpty(), "Os links HATEOAS foram adicionados com sucesso.");
    }

    @Test 
    void throwsAnExceptionOnAddPersonAddressById(){
        when(personRepository.findById(personModel1.getIdPerson())).thenThrow(IllegalArgumentException.class);
        
        var addressListOptional = service.addAddressToPersonById( personModel1.getIdPerson(), addressRecordDto);
    
        assertTrue(addressListOptional.isEmpty(), "Erro ao adicionar endereço. Exception não tratada.");
    }

    @Test
    void setPersonMainAddressById(){

        when(personRepository.findById(personModel1.getIdPerson())).thenReturn(Optional.of(personModel1));
        var updatedPerson = personModel1;

        updatedPerson.getAddressess().get(0).setMainAddress(false);;
        updatedPerson.getAddressess().get(1).setMainAddress(true);;
        when(personRepository.save(updatedPerson)).thenReturn(updatedPerson);
        
        var addressListOptional = service.setMainAddressToPersonById(personModel1.getIdPerson(), address2.getIdAddress());
        
        assertTrue(addressListOptional.isPresent(), "Erro ao atualizar endereço principal.");
        assertFalse(addressListOptional.get().get(0).getMainAddress(), "Endereço principal antigo, permanece como principal.");
        assertTrue(addressListOptional.get().get(1).getMainAddress(), "Endereço principal não foi atualizado.");
        assertEquals(updatedPerson.getAddressess().size(), addressListOptional.get().size(), "Quantidade de endereços errada.");
    }

    @Test
    void throwsAnExceptionOnSetPersonMainAddressById(){
        when(personRepository.findById(personModel1.getIdPerson())).thenThrow(IllegalArgumentException.class);
        
        var addressListOptional = service.setMainAddressToPersonById(personModel1.getIdPerson(), null);
        
        assertTrue(addressListOptional.isEmpty(), "Erro ao atualizar endereço principal. Exception não tratada.");
    }

    @Test
    void returnAllPersonAddressesById(){
        when(personRepository.findById(personModel1.getIdPerson())).thenReturn(Optional.of(personModel1));
        
        var addressListOptional = service.getAllAddressesFromPersonById(personModel1.getIdPerson());

        assertTrue(addressListOptional.isPresent(), "Erro ao recuperar todos os endereços da pessoa.");
        assertEquals(addressModelList1.size(), addressListOptional.get().size(), "Quantidade de endereços errada.");
    }

    @Test
    void throwsAnExceptionOnReturnAllPersonAddressesById(){
        when(personRepository.findById(personModel1.getIdPerson())).thenThrow(IllegalArgumentException.class);
        var addressListOptional = service.getAllAddressesFromPersonById(personModel1.getIdPerson());

        assertTrue(addressListOptional.isEmpty(), "Erro ao retornar endereços. Exception não tratada.");
    }


    @Test
    void configPersonMainAddress(){
        addressModelList1.get(0).setMainAddress(false);
        addressModelList1.get(1).setMainAddress(false);
        
        var expectedResultListAddress = addressModelList1;
        expectedResultListAddress.get(0).setMainAddress(true);


        var configuredAddresses = service.configMainAddress(addressModelList1);

        assertEquals(expectedResultListAddress.size(), configuredAddresses.size(), "Quantidade de endereços errada.");
        assertEquals( 
        expectedResultListAddress.get(0).getMainAddress(), configuredAddresses.get(0).getMainAddress() ,"Endereço principal não foi configurado corretamente.");
    }


}
