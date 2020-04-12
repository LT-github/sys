package com.lt.sys.dto;

import lombok.Data;


@Data
public class NoteDto {

    private String name;

    private String msg;

    private String phoneNumber;

    @Override
    public String toString() {
        return "NoteDto{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
