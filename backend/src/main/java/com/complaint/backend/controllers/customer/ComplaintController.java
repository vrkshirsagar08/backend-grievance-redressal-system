package com.complaint.backend.controllers.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.backend.entities.Complaint;
import com.complaint.backend.services.Customer.ComplaintService;


@RestController
@RequestMapping("/complaint")
public class ComplaintController {
 
    @Autowired 
    private ComplaintService complaintService; 
    
    @PostMapping("/create")
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint) 
    { 
        Complaint savedComplaint = complaintService.saveComplaint(complaint); 
        return new ResponseEntity<>(savedComplaint, HttpStatus.CREATED); 
    }


    // Get all complaints (Admin only)
    @GetMapping("/show")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
}
