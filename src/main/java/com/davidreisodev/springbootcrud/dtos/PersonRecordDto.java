package com.davidreisodev.springbootcrud.dtos;

import java.sql.Date;
import java.util.List;

import com.davidreisodev.springbootcrud.models.AddressModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * PersonRecordDto é um objeto de transferencia de dados, que contém os dados que serão armazenados
 * no PersonModel. Ele também realiza a validação básica inicial.
 *  */
public record PersonRecordDto(@NotBlank String personName, @NotNull Date personBirthday, List<AddressModel> addressess) {



    
}
