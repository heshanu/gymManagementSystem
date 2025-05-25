package com.example.aiService.repo;

import com.example.aiService.model.RecommendationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RecommendationRepo extends  MongoRepository<RecommendationEntity,String> {
    @Query("SELECT u FROM RecommendationEntity u WHERE u.userID = :userID")
    List<RecommendationEntity> findByUserId(String userID);

    @Query("SELECT u FROM RecommendationEntity u WHERE u. activityID = :activityID")
    RecommendationEntity findByActivityId(String activityID);
}
