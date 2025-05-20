package com.example.activityService.dto;

import com.example.activityService.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityResponse {
        private String id;
        private String userId;
        private ActivityType activityType;
        private Integer duration;
        private Integer caloriesBurned;
        private LocalDateTime startTime;
        private Map<String, Object> additionalMetrics;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

}
