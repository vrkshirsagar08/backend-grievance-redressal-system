package com.complaint.backend.services.admin;

import java.util.List;

import com.complaint.backend.dtos.CommentDTO;
import com.complaint.backend.dtos.ComplaintDTO;
import com.complaint.backend.dtos.TaskDTO;
import com.complaint.backend.dtos.UserDTO;

public interface AdminService {
    
    List<UserDTO> getUsers();

    TaskDTO postTask(TaskDTO taskDTO);


    List<TaskDTO> getTasks();


    TaskDTO getTaskById(Long  id);


    void deleteTask(Long id);

    List<TaskDTO> searchTask(String title);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    CommentDTO createComment(Long taskId, Long postedBy, String content);

    List<CommentDTO> getCommentsByTask(Long taskId);

    // ComplaintDTO getComplaint(Long id, String email, String phone, String address, String complaintTitle, String description);

    // List<ComplaintDTO> getComplaints();

}
