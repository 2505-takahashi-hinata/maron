package com.example.maron.repository;

public class MessageRepository {
import com.example.maron.dto.UserMessage;
import com.example.maron.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query("SELECT m.id, m.text, m.userId, m.title, m.category, " +
            "u.name, u.account, u.branchId, u.departmentId, m.createdDate, m.updatedDate " +
            "FROM Message m INNER JOIN User u ON m.userId = u.id " +
            "WHERE m.createdDate BETWEEN :start AND :end " +
            "AND m.category LIKE %:category% ORDER BY m.updatedDate DESC LIMIT :limit")
    public List<Object[]> findByUpdatedDateBetweenAndCategoryOrderByUpdatedDateDesc(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("category") String category, @Param("limit")Integer limit);
    @Query("SELECT m.id, m.text, m.userId, m.title, m.category, " +
            "u.name, u.account, u.branchId, u.departmentId, m.createdDate, m.updatedDate " +
            "FROM Message m INNER JOIN User u ON m.userId = u.id " +
            "WHERE m.createdDate BETWEEN :start AND :end " +
            "ORDER BY m.updatedDate DESC LIMIT :limit")
    public List<Object[]> findByUpdatedDateBetweenOrderByUpdatedDateDesc(@Param("start") LocalDateTime start,@Param("end") LocalDateTime end,@Param("limit")Integer limit);

}
