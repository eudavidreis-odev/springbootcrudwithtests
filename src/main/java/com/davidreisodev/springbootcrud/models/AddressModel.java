package com.davidreisodev.springbootcrud.models;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

/**
 * AddressModel é o modelo de dados da tabela tb_address, onde serão armazenados os dados dos endereços.
 * Esta classe é extendida da classe RepresentationModel, que é a classe que contém os métodos que facilitam a manipulação dos dados.
 * 
 * @see RepresentationModel
 */
@Entity
@Table(name = "tb_address")
public class AddressModel extends RepresentationModel<AddressModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID idAddress;
    @Column( nullable = false, length = 60)
    private String logradouro;
    @Column( nullable = false, length = 10)
    private String cep;
    @Column( nullable = false)
    private String number;
    @Column( nullable = false, length = 60)
    private String city;
    
    private boolean mainAddress = false;

    
    
    /** 
     * @return long
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getIdAddress() {
        return idAddress;
    }
    public void setIdAddress(UUID idAddress) {
        this.idAddress = idAddress;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String numero) {
        this.number = numero;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String cidade) {
        this.city = cidade;
    }

    public boolean getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    
}
