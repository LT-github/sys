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

    @Column(length = 255)
    private String msg;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="info_id")
    private Info info;
}
