package com.davidreisodev.springbootcrud.dtos;

import jakarta.validation.constraints.NotBlank;


/**
 * AddressRecordDto é um objeto de transferencia de dados, que contém os dados que serão armazenados
 * no AddressModel. Ele também realiza a validação básica inicial.
 */
public record AddressRecordDto(@NotBlank String logradouro, @NotBlank String cep, @NotBlank String number, @NotBlank String city, boolean mainAddress) {
    
}
