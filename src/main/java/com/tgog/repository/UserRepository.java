package com.tgog.repository;

import com.tgog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {

    User readByEmail(String email);

    Page<User> findByRoleNotLike(String role, Pageable pageable);

    @Transactional
    @Modifying
    int updateUserStatus(String status, int id);
}
