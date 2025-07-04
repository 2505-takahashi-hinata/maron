package com.example.maron.repository;

import java.util.Optional;
import com.example.maron.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //ユーザーの全件取得
//    @Query(value = "SELECT" +
//            "users.id," +
//            "users.account," +
//            "users.name," +
//            "users.branch_id," +
//            "users.department_id," +
//            "users.is_stopped," +
//            "branches.name," +
//            "departments.name," +
//            "FROM users" +
//            "INNER JOIN branches" + "ON users.branch_id = branches.id" +
//            "INNER JOIN departments" + "ON users.department_id = departments.id;", nativeQuery = true)
//    public List<User> findAllUser();

    //ステータスの変更処理
    @Modifying
    @Query(value = "UPDATE users SET is_stopped = :is_stopped, updated_date = CURRENT_TIMESTAMP WHERE id = :id", nativeQuery = true)
    void updateStatusById(@Param("id") Integer id, @Param("is_stopped") Short status);
    List<User> findByAccountAndPassword(String account, String password);
}
