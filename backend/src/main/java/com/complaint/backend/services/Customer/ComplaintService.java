package com.complaint.backend.services.Customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.backend.entities.Complaint;
import com.complaint.backend.repositories.ComplaintRepository;

@Service
public class ComplaintService {
    
    @Autowired 
    private ComplaintRepository complaintRepository;


     public Complaint saveComplaint(Complaint complaint) {
         return complaintRepository.save(complaint);
    }

    // Get all complaints (Admin)
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

}
