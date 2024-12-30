package com.complaint.backend.controllers.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.backend.dtos.CommentDTO;
import com.complaint.backend.dtos.TaskDTO;
import com.complaint.backend.services.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTasksByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getTasksByUserId(id));
    }
    
     @PutMapping("/task/{id}/{status}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @PathVariable String status){
        TaskDTO updatedTaskDTO = employeeService.updateTask(id, status);
        if (updatedTaskDTO == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTaskDTO);

    }


    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTasksById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getTaskById(id));
    }


    @PostMapping("/task/comment")
    public ResponseEntity<?> createComment(@RequestParam Long taskId, @RequestParam Long postedBy, @RequestBody String content ){
        CommentDTO createCommentDTO = employeeService.createComment(taskId, postedBy, content);
        if (createCommentDTO == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createCommentDTO);

    }


    @GetMapping("/task/{taskId}/comments")
    public ResponseEntity<?> getCommentByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(employeeService.getCommentsByTask(taskId));
    }
}
