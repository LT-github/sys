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
    private String registration;

    @Column
    private String referralCode;

    @Column
    private String longitude;

    @Column
    private String deviceId;

    @Column
    private String latitude;

    @Column
    private String address;

    @OneToMany(mappedBy = "info",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Contacts> contacts;

    @OneToMany(mappedBy = "info",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Note> notes;

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", registration='" + registration + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
