package com.flintinfotech.Moorapan.mapper;

import com.flintinfotech.Moorapan.dto.ContactDTO;
import com.flintinfotech.Moorapan.entity.Contact;

public class ContactMapper {
    public static Contact toEntity(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setMobileNo(dto.getMobileNo());
        contact.setEmailAddress(dto.getEmailAddress());
        contact.setMessage(dto.getMessage());
        return contact;
    }

    public static ContactDTO toDTO(Contact entity) {
        ContactDTO dto = new ContactDTO();
        dto.setName(entity.getName());
        dto.setMobileNo(entity.getMobileNo());
        dto.setEmailAddress(entity.getEmailAddress());
        dto.setMessage(entity.getMessage());
        return dto;
    }
}