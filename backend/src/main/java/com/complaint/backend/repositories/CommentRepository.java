package com.complaint.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.complaint.backend.entities.Comment;

@Repository

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTaskId(Long taskId);
    
    
}
