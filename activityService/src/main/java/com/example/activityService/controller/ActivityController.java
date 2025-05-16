package com.example.activityService.controller;

import com.example.activityService.dto.ActivityResponse;
import com.example.activityService.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController{
    @Autowired
    private ActivityService activityService;
    @PostMapping ()
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityResponse activityRequest){
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }

    @GetMapping()
    public ResponseEntity<List<ActivityResponse>> allActivities(){
        List<ActivityResponse> allList=activityService.getAllActivities();
        return new ResponseEntity<>(allList,HttpStatus.OK);
    }

    @GetMapping("/activity")
    public ResponseEntity<ActivityResponse> getActivity(@RequestParam String activityId){
        ActivityResponse selectedActivity=activityService.getActivityById(activityId);
        return new ResponseEntity<>(selectedActivity,HttpStatus.OK);
    }
}
