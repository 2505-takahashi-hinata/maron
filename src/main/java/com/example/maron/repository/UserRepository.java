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
    @Query("SELECT u.id, u.account, u.name, u.branchId, u.departmentId, u.isStopped, " +
            "b.name, " +
            "d.name " +
            "FROM User u INNER JOIN Branch b ON u.branchId = b.id " +
            "INNER JOIN Department d ON u.departmentId = d.id " +
            "ORDER BY u.isStopped ASC, u.id ASC")
    public List<Object[]> findAllUser();

    //ステータスの変更処理
    @Modifying
    @Query(value = "UPDATE users SET is_stopped = :is_stopped, updated_date = CURRENT_TIMESTAMP WHERE id = :id", nativeQuery = true)
    void updateStatusById(@Param("id") Integer id, @Param("is_stopped") Short status);
    List<User> findByAccountAndPassword(String account, String password);
}
