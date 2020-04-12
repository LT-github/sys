package com.lt.sys.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_note")
public class Note {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column(length = 255)
    private String msg;

    @ManyToOne
    @JoinColumn(name="info_id")
    private Info info;
}
