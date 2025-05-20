package com.example.activityService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;
@Document(collection = "activities")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityModel {
    @Id
    @Field("id")
    private String id;
    @Field("user_id") // Explicit field name mapping
    private String userId;

    private ActivityType activityType;
    private Integer duration;

    @Field("calories_burned") // Snake case for MongoDB field names
    private Integer caloriesBurned;

    @Field("start_time")
    private LocalDateTime startTime;

    @Field("metrics")
    private Map<String, Object> additionalMetrics;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}