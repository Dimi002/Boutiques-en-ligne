package com.dimsoft.clinicStackProd.beans;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Maestros
 * @email roslyn.temateu@dimsoft.eu
 */

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Getter
@Setter
public class ReachText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "long_description", nullable = true)
    private String longDescription;
}
