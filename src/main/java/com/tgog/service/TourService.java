package com.tgog.service;

import com.tgog.config.AppProporties;
import com.tgog.constants.UserStatus;
import com.tgog.model.Tour;
import com.tgog.model.User;
import com.tgog.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    AppProporties appProporties;

    public boolean createNewTour(Tour tour) {
        boolean isSaved = false;
        Tour res =  tourRepository.save(tour);
        if (null != res && res.getTourId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<Tour> findAllTours(int pageNum, String sortField, String sortDir){
        int pageSize = appProporties.getPageSize();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Tour> page = tourRepository.findAll(pageable);
        return page;
    }

    public boolean updateShowOnPageStatus(int tourId){
        boolean isUpdated = false;
        Optional<Tour> tour = tourRepository.findById(tourId);
        if (tour.isPresent()) {
            Boolean show = tour.get().getShow().equals(true) ?
                    false:true;
            tourRepository.updateTourShow(show, tourId);
            isUpdated = true;
        }
        return isUpdated;
    }
}
