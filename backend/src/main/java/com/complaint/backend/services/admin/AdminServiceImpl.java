package com.complaint.backend.services.admin;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.complaint.backend.dtos.CommentDTO;
import com.complaint.backend.dtos.ComplaintDTO;
import com.complaint.backend.dtos.TaskDTO;
import com.complaint.backend.dtos.UserDTO;
import com.complaint.backend.entities.Comment;
import com.complaint.backend.entities.Complaint;
import com.complaint.backend.entities.Task;
import com.complaint.backend.entities.User;
import com.complaint.backend.enums.TaskStatus;
import com.complaint.backend.enums.UserRole;
import com.complaint.backend.repositories.CommentRepository;
import com.complaint.backend.repositories.ComplaintRepository;
import com.complaint.backend.repositories.TaskRepository;
import com.complaint.backend.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
   private final UserRepository userRepository;

   private final TaskRepository taskRepository;

   private final CommentRepository commentRepository;
   private final ComplaintRepository complaintRepository;


   @Override
   public List<UserDTO> getUsers() {
      return userRepository.findAll()
            .stream()
            .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
            .map(User::getUserDTO)
            .collect(Collectors.toList());

   }

   @Override
   public TaskDTO postTask(TaskDTO taskDTO) {
      Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());

      if (optionalUser.isPresent()) {
         Task task = new Task();
         task.setTitle(taskDTO.getTitle());
         task.setDescription(taskDTO.getDescription());
         task.setPriority(taskDTO.getPriority());
         task.setDueDate(taskDTO.getDueDate());
         task.setUser(optionalUser.get());
         task.setTaskStatus(TaskStatus.PENDING);
         return taskRepository.save(task).getTaskDTO();

      }
      return taskDTO;

   }

   @Override
   public List<TaskDTO> getTasks() {
      return taskRepository.findAll().stream().map(Task::getTaskDTO).collect(Collectors.toList());
   }

   @Override
   public TaskDTO getTaskById(Long id) {
      Optional<Task> optionalTask = taskRepository.findById(id);
      return optionalTask.map(Task::getTaskDTO).orElse(null);
   }

   @Override
   public void deleteTask(Long id) {
      taskRepository.deleteById(id);
   }

   @Override
   public List<TaskDTO> searchTask(String title) {
      return taskRepository.findAllByTitleContaining(title).stream().map(Task::getTaskDTO).collect(Collectors.toList());
   }

   @Override
   public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
      Optional<Task> optionalTask = taskRepository.findById(id);
      Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());
      if (optionalTask.isPresent() && optionalUser.isPresent()) {

         Task existingTask = optionalTask.get();
         existingTask.setTitle(taskDTO.getTitle());
         existingTask.setDescription(taskDTO.getDescription());
         existingTask.setDueDate(taskDTO.getDueDate());
         existingTask.setPriority(taskDTO.getPriority());
         existingTask.setUser(optionalUser.get());
         TaskStatus taskStatus = mapStringToTaskStatus(String.valueOf(taskDTO.getTaskStatus()));
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
   public CommentDTO createComment(Long taskld, Long postedBy, String content) {
      Optional<Task> optionalTask =  taskRepository.findById(taskld) ;
      Optional<User> optionalUser = userRepository. findById(postedBy);
      if (optionalUser.isPresent() && optionalTask.isPresent()){
      Comment comment = new Comment();
      comment.setContent(content);
      comment.setCreatedAt(new Date());
      comment.setUser(optionalUser.get()) ;
      comment.setTask(optionalTask.get()) ;
   return commentRepository.save(comment).getCommentDTO();

      }
   throw new EntityNotFoundException("Task or user not found! " ) ;
   }

   @Override
   public List<CommentDTO> getCommentsByTask(Long taskId) {
      return commentRepository.findAllByTaskId(taskId).stream().map(Comment::getCommentDTO).collect(Collectors.toList());
   }

   // @Override
   // public List<ComplaintDTO> getComplaints() {
   //    return complaintRepository.findAll().stream().map(Complaint::getComplaintDTO).collect(Collectors.toList());

   // }

//    @Override
//    public List<ComplaintDTO> getComplaints() {
//       // Fetch all complaints and map them to DTOs
//       return complaintRepository.findAll()
//               .stream()
//               .map(Complaint::getComplaintDTO)
//               .collect(Collectors.toList());
//   }


   
}