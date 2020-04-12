package com.lt.sys.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_info")
@Data
public class Info {

    @Id
    private Long id;

    @Column
    private String referralCode;

    @Column
    private String longitude;

    @Column
    private String latitude;

    @OneToMany(mappedBy = "info",cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Contacts> contacts;

    @OneToMany(mappedBy = "info",cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Note> notes;


}
