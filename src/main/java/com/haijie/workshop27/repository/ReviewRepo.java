package com.haijie.workshop27.repository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.haijie.workshop27.model.EditedReview;
import com.haijie.workshop27.model.Review;
import static com.haijie.workshop27.Constants.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    // Task a
    public void insertReview(Review review){
        //give a randomly generated id
        String reviewId = UUID.randomUUID().toString().substring(0, 8);
        review.setRid(reviewId);
        Document doc = review.toDocument();
        mongoTemplate.insert(doc, COLLECTION_REVIEW);
    }

    // Task b
    public Review updateReviewById(String rid, String json) throws IOException{
        /* 
        ObjectId docId = new ObjectId(_id);
        Review r = template.findById(docId, Review.class, COLLECTION_REVIEWS);
        */

        // Finds the review which the rid match
        Criteria c = Criteria.where(FIELD_RID).regex(rid,"i");
        //Query for the criteria
        Query q = Query.query(c);
        
        // Map data straight to Review.Class instead
        List<Review> list = mongoTemplate.find(q, Review.class, COLLECTION_REVIEW);
        // List<Review> rList = list.stream()   
        //         .map(r -> Review.createB(r))
        //         .toList();

        //Get 0 because there should only be 1 so get he first
        Review r = list.get(0);
        
        //If the review is found
        if (r != null){
            EditedReview er = EditedReview.createEditedReview(json);
            // if List<EditedReview> not null, add EditedReview in;
            if(r.getEdited() != null){
            r.getEdited().add(er);
            // else create new list
            }else {
                List<EditedReview> ll = new LinkedList<>();
                ll.add(er);
                r.setEdited(ll);
            }

        Update updateOps = new Update()
            .push("edited").each(er);
            mongoTemplate.updateFirst(q,updateOps, COLLECTION_REVIEW);

        }
        return r;
        
    }

    // Task C : use ObjectId to extract info
    public Review getReviewById(String _id){
        ObjectId docId = new ObjectId(_id);
        return mongoTemplate.findById(docId, Review.class, COLLECTION_REVIEW);
    }
    
    
}
