package com.project.travelExperts.data.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "agencies")
public class Agency {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "AgencyId")
    private long id;
    @Column(name = "AgncyAddress")
    private String agencyAddress;
    @Column(name = "AgncyCity")
    private String agencyCity;
    @Column(name = "AgncyProv")
    private String agencyProv;
    @Column(name = "AgncyPostal")
    private String agencyPostal;
    @Column(name = "AgncyCountry")
    private String agencyCountry;
    @Column(name = "AgncyPhone")
    private String agencyPhone;
    @Column(name = "AgncyFax")
    private String agencyFax;
    @OneToMany(mappedBy = "agency")
    private List<Agent> agents;
}
