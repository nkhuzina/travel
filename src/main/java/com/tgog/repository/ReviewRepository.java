package com.tgog.repository;

import com.tgog.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByShowIsTrueOrderByReviewIdDesc();
    Page<Review> findAll(Pageable pageable);
    @Transactional
    @Modifying
    int updateReviewShow(Boolean show, int id);
}
