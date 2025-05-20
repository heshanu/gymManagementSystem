package com.example.activityService.service;

import com.example.activityService.dto.ActivityRequest;
import com.example.activityService.dto.ActivityResponse;

import java.util.List;

public interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest activityRequest);
    List<ActivityResponse> getAllActivities();
    ActivityResponse getActivityById(String activityId);
}
