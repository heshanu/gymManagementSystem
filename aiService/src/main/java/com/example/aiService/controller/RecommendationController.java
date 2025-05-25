package com.example.aiService.controller;

import com.example.aiService.dto.RecommendationResponse;
import com.example.aiService.service.impl.RecommendationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {
    public RecommendationController(RecommendationServiceImpl recommendationService) {
        this.recommendationService = recommendationService;
    }
    private final RecommendationServiceImpl recommendationService;
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<RecommendationResponse>> getUserRecommendation(@PathVariable String userID){
        List<RecommendationResponse> result=recommendationService.getUserRecommendation(userID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/activity/act/{activityID}")
    public ResponseEntity<RecommendationResponse> getActivityRecommendation(@PathVariable String activityID){
        RecommendationResponse result=recommendationService.getActivityRecommendation(activityID);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
