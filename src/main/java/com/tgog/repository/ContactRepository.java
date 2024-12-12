package com.tgog.repository;

import com.tgog.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findByStatus(String status);

    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    Page<Contact> findByStatusWithQuery(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);
}
