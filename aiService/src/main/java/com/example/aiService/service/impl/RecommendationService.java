package com.example.aiService.service.impl;

import com.example.aiService.dto.RecommendationResponse;
import com.example.aiService.repo.RecommendationRepo;

import java.util.List;

public interface RecommendationService {
  List<RecommendationResponse> getUserRecommendation(String userID);
  RecommendationResponse getActivityRecommendation(String activityID);


}
