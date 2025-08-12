package com.flintinfotech.Moorapan.service.impl;

import com.flintinfotech.Moorapan.dto.ContactDTO;
import com.flintinfotech.Moorapan.entity.Contact;
import com.flintinfotech.Moorapan.mapper.ContactMapper;
import com.flintinfotech.Moorapan.repository.ContactRepository;
import com.flintinfotech.Moorapan.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ContactDTO saveContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.toEntity(contactDTO);
        Contact saved = contactRepository.save(contact);
        return ContactMapper.toDTO(saved);
    }
}