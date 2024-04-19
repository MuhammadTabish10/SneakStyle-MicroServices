package com.SneakStyle.UserService.Repository;

import com.SneakStyle.UserService.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByNameAndUser_IdAndStatusIsTrue(String name, Long userId);
    List<Address> findByUserIdAndStatusIsTrue(Long userId);
    @Modifying
    @Query("UPDATE Address a SET a.status = :status WHERE a.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}
