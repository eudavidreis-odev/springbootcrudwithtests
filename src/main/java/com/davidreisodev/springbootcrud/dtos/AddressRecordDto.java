package com.davidreisodev.springbootcrud.dtos;

import jakarta.validation.constraints.NotBlank;


public record AddressRecordDto(@NotBlank String logradouro, @NotBlank String cep, @NotBlank String number, @NotBlank String city, boolean mainAddress) {
    
}
