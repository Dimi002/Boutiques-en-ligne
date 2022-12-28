package com.dimsoft.clinicStackProd.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityMin {
    private Integer id;
    private String specialityName;
    private String specialistCommonName;
    private String specialityDesc;
    private String specialityImagePath;
}
