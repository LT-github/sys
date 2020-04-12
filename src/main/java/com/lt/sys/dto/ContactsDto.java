package com.lt.sys.dto;

import lombok.Data;


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
