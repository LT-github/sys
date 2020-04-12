package com.lt.sys.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ContactsDto {

    private String phoneNumber;

    private String name;

    @Override
    public String toString() {
        return "ContactsDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
