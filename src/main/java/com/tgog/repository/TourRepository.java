package com.tgog.repository;

import com.tgog.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
    Page<Tour> findAll(Pageable pageable);

    @Transactional
    @Modifying
    int updateTourShow(Boolean show, int id);
}
