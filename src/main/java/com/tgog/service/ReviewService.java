package com.tgog.service;

import com.tgog.config.AppProporties;
import com.tgog.constants.ApplicationStatus;
import com.tgog.constants.Roles;
import com.tgog.model.Contact;
import com.tgog.model.Review;
import com.tgog.model.Tour;
import com.tgog.repository.ReviewRepository;
import com.tgog.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    AppProporties appProporties;


    public Page<Review> findAllReviews(int pageNum, String sortField, String sortDir){
        int pageSize = appProporties.getPageSize();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Review> page = reviewRepository.findAll(pageable);
        return page;
    }

    public List<Review> findAllReviews(){
        List<Review> list = reviewRepository.findByShowIsTrueOrderByReviewIdDesc();
        return list;
    }
    public Review saveReview(Review review) {
        review.setCreatedBy(Roles.ANONYMOUS);
        review.setShow(Boolean.TRUE);
        return reviewRepository.save(review);
    }

    public boolean updateShowOnPageStatus(int reviewId){
        boolean isUpdated = false;
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            Boolean show = review.get().getShow().equals(true) ?
                    false:true;
            reviewRepository.updateReviewShow(show, reviewId);
            isUpdated = true;
        }
        return isUpdated;
    }
}
