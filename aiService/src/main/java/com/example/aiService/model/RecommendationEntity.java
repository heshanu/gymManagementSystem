package com.example.aiService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
@Document(collection = "recommendation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationEntity{
    @Id
    @Field("id")
    private String id;

    @Field("activity_id")
    private  String activityID;

    @Field("user_id")
    private String userID;

    @Field("activity_type")
    private String activityType;

    @Field("recommendation")
    private String recommendation;

    @Field("improvements")
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

    @CreatedDate
    @Field("create_at")
    private LocalDateTime createdAt;
}
