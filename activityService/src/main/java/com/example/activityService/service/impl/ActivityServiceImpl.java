package com.example.activityService.service.impl;

import com.example.activityService.dto.ActivityResponse;
import com.example.activityService.model.ActivityModel;
import com.example.activityService.repo.ActivitityRepo;
import com.example.activityService.service.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivitityRepo activitityRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ActivityResponse trackActivity(ActivityResponse activityRequest) {
        ActivityModel activityModel=new ActivityModel();
        activityModel.setUserId(activityRequest.getUserId());
        activityModel.setDuration(activityRequest.getDuration());
        activityModel.setAdditionalMetrics(activityRequest.getAdditionalMetrics());
        activityModel.setCaloriesBurned(activityRequest.getCaloriesBurned());
        activityModel.setStartTime(activityRequest.getStartTime());
        activityModel.setActivityType(activityRequest.getActivityType());

        ActivityModel savedActivity=activitityRepo.save(activityModel);
        return modelMapper.map(activityModel,ActivityResponse.class);
    }
    @Override
    public List<ActivityResponse> getAllActivities() {
        List<ActivityModel> allActivities=activitityRepo.findAll();
        List<ActivityResponse> convertToResponse = allActivities.stream()
                .map(activity -> modelMapper.map(activity, ActivityResponse.class))
                .collect(Collectors.toList());
        return convertToResponse;
    }
    @Override
    public ActivityResponse getActivityById(String activityId) {
        Optional<ActivityModel> getActivity=activitityRepo.findActivityByActivityId(activityId);
        return modelMapper.map(getActivity,ActivityResponse.class);
    }


}
