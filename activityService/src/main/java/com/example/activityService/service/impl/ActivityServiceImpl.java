package com.example.activityService.service.impl;

import com.example.activityService.dto.ActivityRequest;
import com.example.activityService.dto.ActivityResponse;
import com.example.activityService.model.ActivityModel;
import com.example.activityService.repo.ActivitityRepo;
import com.example.activityService.service.ActivityService;
import com.example.activityService.service.UserValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivitityRepo activitityRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    public AmqpTemplate template;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    @Override
    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
    log.info("Inside the track activity method");

    boolean isValidUser = userValidation.validateUser(activityRequest.getUserId());
    log.info("User validation result for user ID {}: {}", activityRequest.getUserId(), isValidUser);

    if (isValidUser) {
        ActivityModel activityModel = new ActivityModel();
        activityModel.setUserId(activityRequest.getUserId());
        activityModel.setDuration(activityRequest.getDuration());
        activityModel.setAdditionalMetrics(activityRequest.getAdditionalMetrics());
        activityModel.setCaloriesBurned(activityRequest.getCaloriesBurned());
        activityModel.setStartTime(activityRequest.getStartTime());
        activityModel.setActivityType(activityRequest.getActivityType());

        ActivityModel savedActivity =  activitityRepo.save(activityModel);
        log.info("Saved activity successfully for user ID: {}", activityModel.toString());

        // Publish to message queue for AI service
        try {
            template.convertAndSend(exchange,routingKey,savedActivity);
           // rabbitTemplate.convertAndSend(exchange, routingKey,savedActivity);
            log.info("Published activity to message queue successfully",savedActivity.toString());
        } catch (Exception e) {
            log.error("Failed to publish activity to message queue: {}", e.getMessage());
        }

        return modelMapper.map(activityModel, ActivityResponse.class);
    } else {
        log.error("Unable to save activity for invalid user ID: {}", activityRequest.getUserId());
        throw new RuntimeException("User not found with ID: " + activityRequest.getUserId());
    }
}
    @Override
    public List<ActivityResponse> getAllActivities() {
        log.info("Inside the get all activity");
        List<ActivityModel> allActivities=activitityRepo.findAll();
        List<ActivityResponse> convertToResponse = allActivities.stream()
                .map(activity -> modelMapper.map(activity, ActivityResponse.class))
                .collect(Collectors.toList());
        log.info("get all activities",allActivities);
        return convertToResponse;
    }
    @Override
    public ActivityResponse getActivityById(String activityId) {
        log.info("Inside the get Activity by Id");
        Optional<ActivityModel> getActivity=activitityRepo.findActivityByActivityId(activityId);
        log.info("get activity by act Id",getActivity);
        return modelMapper.map(getActivity,ActivityResponse.class);
    }


}
