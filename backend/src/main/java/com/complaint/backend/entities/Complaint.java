package com.complaint.backend.entities;

import com.complaint.backend.dtos.ComplaintDTO;
import com.complaint.backend.dtos.TaskDTO;
import com.complaint.backend.enums.TaskStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String address;
    private String phone;
    private String complaintTitle;
    private String description;


    private TaskStatus taskStatus;

    public ComplaintDTO getComplaintDTO(){
        ComplaintDTO complaintDTO = new ComplaintDTO();
        complaintDTO.setId(id);
        complaintDTO.setName(name);
        complaintDTO.setEmail(email);
        complaintDTO.setAddress(address);
        complaintDTO.setPhone(phone);
        complaintDTO.setComplaintTitle(complaintTitle);
        complaintDTO.setDescription(description);
       
        return complaintDTO;

    }

   
}
