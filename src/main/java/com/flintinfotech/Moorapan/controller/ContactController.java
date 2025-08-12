package com.flintinfotech.Moorapan.controller;

import com.flintinfotech.Moorapan.dto.ContactDTO;
import com.flintinfotech.Moorapan.service.ContactService;
import com.flintinfotech.Moorapan.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveContact(@RequestBody ContactDTO contactDTO) {
        try {
            ContactDTO saved = contactService.saveContact(contactDTO);
            Response response = new Response(saved, "Success", "Contact saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response error = new Response(null, "Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}