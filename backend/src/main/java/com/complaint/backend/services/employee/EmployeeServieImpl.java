package com.complaint.backend.services.employee;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.complaint.backend.dtos.CommentDTO;
import com.complaint.backend.dtos.TaskDTO;
import com.complaint.backend.entities.Comment;
import com.complaint.backend.entities.Task;
import com.complaint.backend.entities.User;
import com.complaint.backend.enums.TaskStatus;
import com.complaint.backend.repositories.CommentRepository;
import com.complaint.backend.repositories.TaskRepository;
import com.complaint.backend.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class EmployeeServieImpl implements EmployeeService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<TaskDTO> getTasksByUserId(Long id) {
        return taskRepository.findAllByUserId(id).stream().map(Task::getTaskDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            TaskStatus taskStatus = mapStringToTaskStatus(String.valueOf(status));
            existingTask.setTaskStatus(taskStatus);
            return taskRepository.save(existingTask).getTaskDTO();

        }

        return null;

    }

    private TaskStatus mapStringToTaskStatus(String taskStatus) {
        return switch (taskStatus) {
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;

        };

    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id).map(Task::getTaskDTO).orElse(null);

    }

    @Override
    public CommentDTO createComment(Long taskld, Long postedBy, String content) {
        Optional<Task> optionalTask = taskRepository.findById(taskld);
        Optional<User> optionalUser = userRepository.findById(postedBy);
        if (optionalUser.isPresent() && optionalTask.isPresent()) {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedAt(new Date());
            comment.setUser(optionalUser.get());
            comment.setTask(optionalTask.get());
            return commentRepository.save(comment).getCommentDTO();

        }
        throw new EntityNotFoundException("Task or user not found! ");

    }

    @Override
    public List<CommentDTO> getCommentsByTask(Long taskId) {
       return commentRepository.findAllByTaskId(taskId).stream().map(Comment::getCommentDTO).collect(Collectors.toList());
    }
}
