package com.complaint.backend.services.employee;

import java.util.List;

import com.complaint.backend.dtos.CommentDTO;
import com.complaint.backend.dtos.TaskDTO;

public interface EmployeeService {
    
    List<TaskDTO> getTasksByUserId(Long id);

    TaskDTO updateTask(Long id, String status);

    TaskDTO getTaskById(Long id);

    CommentDTO createComment(Long taskId, Long postedBy, String content);

    List<CommentDTO> getCommentsByTask(Long taskId);

}
