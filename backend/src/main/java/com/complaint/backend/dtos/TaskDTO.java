package com.complaint.backend.dtos;

import java.util.Date;

import com.complaint.backend.enums.TaskStatus;

import lombok.Data;

@Data
public class TaskDTO {
    
    private Long id;
    private String title;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;
    private Long employeeId;
    private String employeeName;
    private String description;



}
