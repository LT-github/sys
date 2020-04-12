package com.lt.sys.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_contacts")
@Data
public class Contacts {

    @Id
    private Long id;

    @Column
    private String phoneNumber;

    @Column
    private String name;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="info_id")
    private Info info;

}
