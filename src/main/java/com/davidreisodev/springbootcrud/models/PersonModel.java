package com.davidreisodev.springbootcrud.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * PersonModel é o modelo de dados da tabela tb_person, onde serão armazenados os dados das pessoas.
 * Esta classe é extendida da classe RepresentationModel, que é a classe que contém os métodos que facilitam a manipulação dos dados.
 * 
 * @see RepresentationModel
 */
@Entity
@Table(name = "tb_person")
public class PersonModel extends RepresentationModel<PersonModel> implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_person")
    private UUID idPerson;
    @Column(nullable = false, length = 50)
    private String personName;
    
    private Date personBirthday;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_person", referencedColumnName = "id_person")
    private List<AddressModel> addressess;

    public PersonModel(){}

    public PersonModel(String personName,Date personBirthday,List<AddressModel> addressess){
        super();
        this.personName = personName;
        this.personBirthday = personBirthday;
        this.addressess = addressess;    
    }

    
    
    /** 
     * @return long
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public UUID getIdPerson() {
        return idPerson;
    }
    public void setIdPerson(UUID idProduct) {
        this.idPerson = idProduct;
    }
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String productName) {
        this.personName = productName;
    }
    public Date getPersonBirthday() {
        return personBirthday;
    }
    public void setPersonBirthday(Date personBirthday) {
        this.personBirthday = personBirthday;
    }
    public List<AddressModel> getAddressess() {
        return addressess;
    }
    public void setAddressess(List<AddressModel> address) {
        this.addressess = address;
    }


}
