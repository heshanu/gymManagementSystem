package com.example.activityService.repo;

import com.example.activityService.model.ActivityModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivitityRepo extends MongoRepository<ActivityModel,String> {
        @Query("SELECT u FROM ActivityModel u WHERE u.id = :activityId")
        Optional<ActivityModel> findActivityByActivityId(@Param("activityId") String activityId);
}
