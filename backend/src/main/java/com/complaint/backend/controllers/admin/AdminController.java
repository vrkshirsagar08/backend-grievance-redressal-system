package com.complaint.backend.controllers.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.backend.dtos.CommentDTO;
import com.complaint.backend.dtos.ComplaintDTO;
import com.complaint.backend.dtos.TaskDTO;
import com.complaint.backend.entities.Complaint;
import com.complaint.backend.services.admin.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    
    private final AdminService adminService;
    
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());

    }

    // @GetMapping("/complaints")
    // public ResponseEntity<?> getComplaints(){
    //     return ResponseEntity.ok(adminService.getComplaints());
    // }

    
    

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(){
        return ResponseEntity.ok(adminService.getTasks());

    }

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTasksById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getTaskById(id));

    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);

    }

    @PostMapping("/task")
    public ResponseEntity<?> postTask(@RequestBody TaskDTO taskDTO){
        TaskDTO createdTaskDTO = adminService.postTask(taskDTO);
        if (createdTaskDTO == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);

    }


    @GetMapping("/tasks/search/{title}")
    public ResponseEntity<?> searchTasks(@PathVariable String title){
        return ResponseEntity.ok(adminService.searchTask(title));

    }

    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        TaskDTO updatedTaskDTO = adminService.updateTask(id, taskDTO);
        if (updatedTaskDTO == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTaskDTO);

    }

    @PostMapping("/task/comment")
    public ResponseEntity<?> createComment(@RequestParam Long taskId, @RequestParam Long postedBy, @RequestBody String content ){
        CommentDTO createCommentDTO = adminService.createComment(taskId, postedBy, content);
        if (createCommentDTO == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createCommentDTO);

    }


    @GetMapping("/task/{taskId}/comments")
    public ResponseEntity<?> getCommentByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(adminService.getCommentsByTask(taskId));
    }
    
}
