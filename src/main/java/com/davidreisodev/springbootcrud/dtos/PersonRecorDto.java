package com.davidreisodev.springbootcrud.dtos;

import java.sql.Date;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;   


public record PersonRecorDto(@NotBlank String personName, @NotNull Date personBirthday,@NotNull AddressRecordDto address) {
    
}
