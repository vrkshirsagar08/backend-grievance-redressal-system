package com.complaint.backend.entities;

import java.util.Date;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.complaint.backend.dtos.TaskDTO;
import com.complaint.backend.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private Date dueDate;
    private String priority;
    private String description;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false) 
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private TaskStatus taskStatus;

    public TaskDTO getTaskDTO(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        taskDTO.setTitle(title);
        taskDTO.setTaskStatus(taskStatus);
        taskDTO.setEmployeeName(user.getName());
        taskDTO.setEmployeeId(user.getId());
        taskDTO.setDueDate(dueDate);
        taskDTO.setPriority(priority);
        taskDTO.setDescription(description);
        return taskDTO;

    }
    
}
