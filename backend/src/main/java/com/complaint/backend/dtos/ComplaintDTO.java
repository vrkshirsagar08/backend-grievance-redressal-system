package com.complaint.backend.dtos;

import lombok.Data;

@Data
public class ComplaintDTO {
    private Long id;

    private String name;
    private String email;
    private String address;
    private String phone;
    private String complaintTitle;
    private String description;
}
