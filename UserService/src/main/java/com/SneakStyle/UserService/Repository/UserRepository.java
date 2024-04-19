package com.SneakStyle.UserService.Repository;

import com.SneakStyle.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndStatusIsTrue(String email);
    List<User> findAllByStatusOrderByIdDesc(Boolean status);

    @Modifying
    @Query("UPDATE User us SET us.status = :status WHERE us.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}
