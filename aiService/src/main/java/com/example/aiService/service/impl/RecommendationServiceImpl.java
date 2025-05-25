package com.example.aiService.service.impl;

import com.example.aiService.dto.RecommendationResponse;
import com.example.aiService.model.RecommendationEntity;
import com.example.aiService.repo.RecommendationRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService{
    public RecommendationServiceImpl(RecommendationRepo recommendationRepo, ModelMapper modelMapper) {
        this.recommendationRepo = recommendationRepo;
        this.modelMapper = modelMapper;
    }
    private final RecommendationRepo recommendationRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<RecommendationResponse> getUserRecommendation(String userID) {
        List<RecommendationEntity> allActivities=recommendationRepo.findByUserId(userID);
        List<RecommendationResponse> convertToResponse = allActivities.stream()
                .map(activity -> modelMapper.map(activity, RecommendationResponse.class))
                .collect(Collectors.toList());
        //log.info("get all activities");
        return convertToResponse;
    }

    @Override
    public RecommendationResponse getActivityRecommendation(String activityID) {
        try{
        RecommendationEntity activityById=recommendationRepo.findByActivityId(activityID);
        return modelMapper.map(activityById,RecommendationResponse.class);}
        catch (Exception e){
            throw new RuntimeException("Cannot find activity with:"+activityID);
        }
    }
}
