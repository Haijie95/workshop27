package com.haijie.workshop27.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haijie.workshop27.model.EditedReview;
import com.haijie.workshop27.model.Review;
import com.haijie.workshop27.repository.ReviewRepo;

@Service
public class ReviewService {

    @Autowired
    ReviewRepo rrepo;

    public String addReview(Review review) {
        String reviewId = UUID.randomUUID().toString().substring(0, 8);
        review.setRid(reviewId);
        rrepo.insertReview(review);
        return reviewId;
    }

    public Review updateReviewById(String rid, String json) throws IOException{
        return rrepo.updateReviewById(rid, json);
    }

    public Review getReviewById(String _id){
    
        Review r = rrepo.getReviewById(_id);
        
        if(r.getEdited() != null){
            List<EditedReview> ll = (List<EditedReview>) r.getEdited();
            System.out.println(ll.size());
            // if there is an edited review in list, then Boolean = true
            if (ll.size() > 0)
                r.setIsEdited(Boolean.valueOf(true));
            else
                r.setIsEdited(Boolean.valueOf(false));
        }
        r.setTimestamp(LocalDateTime.now());

        return r;
    }
    
}
