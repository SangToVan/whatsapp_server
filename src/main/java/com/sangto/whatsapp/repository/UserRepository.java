package com.sangto.whatsapp.repository;

import com.sangto.whatsapp.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    @Query("select u from User u where u.full_name like %:query% or u.email like %:query%")
    public List<User> searchUser(@Param("query") String query);

    @Query(value = "SELECT * FROM User u WHERE u.full_name LIKE :query AND u.full_name IS NOT NULL", nativeQuery = true)
    List<User> searchUserByFull_name(@Param("query") String query);
}
