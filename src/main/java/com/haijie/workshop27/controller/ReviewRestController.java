package com.haijie.workshop27.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haijie.workshop27.model.Review;
import com.haijie.workshop27.service.GameService;
import com.haijie.workshop27.service.ReviewService;
import com.haijie.workshop27.model.EditedReview;
import com.haijie.workshop27.model.Game;

@RestController
@RequestMapping("")
public class ReviewRestController {

    @Autowired
    ReviewService rsvc;

    @Autowired
    GameService gsvc;
    
    //Task A
    @PostMapping("/review")
    public String postComment(@RequestBody MultiValueMap<String, String> form, Model model){
        
        //done creating form
        Review r = Review.create(form);
        System.out.println(r);

        //check if game id exist
        List<Game> results = gsvc.getGameById(Integer.parseInt(form.getFirst("gid")));
        Game g = results.get(0);
        
        //if gid doesn't exist return to same page
        if(g.getName() == null){
            return "index";
        }

        //set the gamename from Game to gamename in review
        r.setName(g.getName());
        r.setPosted(LocalDateTime.now());

        // add into review collection
		String reviewId = rsvc.addReview(r);
		System.out.printf(">>>> reviewId: %s\n", reviewId);
        
        System.out.println(r);
        
        model.addAttribute("r", r);
		// return "redirect:/";
        return "insert";
    }

    //Task B
    @PutMapping("/review/{rid}")
    public ResponseEntity<String> updateDetails(@PathVariable String rid, @RequestBody String json) throws IOException{
        //create a new review 
        Review r = rsvc.updateReviewById(rid, json);
        EditedReview eR = r.getEdited().get(r.getEdited().size()-1);
        System.out.println(r);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(eR.toJSON().toString());
    }
    
    // Task C
    @GetMapping("/review/{_id}")
    public ResponseEntity<String> getReviewById(@PathVariable String _id){
        Review r = rsvc.getReviewById(_id);
        System.out.println(r.getEdited());
        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(r.toJSONTaskB().toString());
    }
    
    // Task D
    @GetMapping("/review/{_id}/history")
    public ResponseEntity<String> getReviewHistory(@PathVariable String _id){
        Review r = rsvc.getReviewById(_id);
        System.out.println(r.getEdited());
        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(r.toJSONTaskC().toString());
    } 

}
