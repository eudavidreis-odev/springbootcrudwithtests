package com.davidreisodev.springbootcrud.dtos;

import java.sql.Date;
import java.util.List;

import com.davidreisodev.springbootcrud.models.AddressModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PersonRegisterRecordDto(@NotBlank String personName, @NotNull Date personBirthday, List<AddressModel> addressess) {



    
}
