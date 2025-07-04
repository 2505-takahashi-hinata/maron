package com.example.maron.repository;

public class CommentRepository {

import com.example.maron.dto.UserComment;
import com.example.maron.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query("SELECT c.id,c.userId,c.messageId,c.text,c.createdDate,c.updatedDate," +
            "u.account,u.name,u.branchId,u.departmentId " +
            "FROM Comment c INNER JOIN User u ON c.userId = u.id " +
            "ORDER BY c.createdDate ASC LIMIT :limit")
    public List<Object[]>findAllOrderByUpdatedDateAsc(@Param("limit")Integer limit);

}
