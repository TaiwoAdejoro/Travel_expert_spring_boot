package com.project.travelExperts.data.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Affiliation")
public class Affilitation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "AffilitationId")
    private String  id;
    @Column(name = "AffName")
    private String affilitationName;
    @Column(name = "AffDesc")
    private String affilitationDesc;
}
