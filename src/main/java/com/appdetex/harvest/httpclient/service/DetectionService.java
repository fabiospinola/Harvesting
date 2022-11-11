package com.appdetex.harvest.httpclient.service;


import com.appdetex.harvest.httpclient.entity.Detection;
import com.appdetex.harvest.httpclient.repository.DetectionRepository;
import com.appdetex.harvest.httpclient.request.CreateDetectionRequest;
import com.appdetex.harvest.httpclient.request.UpdateDetectionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectionService {

    @Autowired
    DetectionRepository detectionRepository;

    public List<Detection> getAllDetections() {
        return detectionRepository.findAll();
    }

    public Detection createDetection(CreateDetectionRequest createDetectionRequest) {
        Detection detection = new Detection(createDetectionRequest);

        detection = detectionRepository.save(detection);
        return detection;
    }

    public Detection updateDetection(UpdateDetectionRequest updateDetectionRequest) {
        Detection detection = detectionRepository.findById(updateDetectionRequest.getId()).get();

        if (updateDetectionRequest.getTitle() != null && !updateDetectionRequest.getTitle().isEmpty()) { //Let's check in the repo if the name are not null
            detection.setTitle(updateDetectionRequest.getTitle());
        }

        if (updateDetectionRequest.getDescription() != null && !updateDetectionRequest.getDescription().isEmpty()) { //Let's check in the repo if the name are not null
            detection.setDescription(updateDetectionRequest.getDescription());
        }

        if (updateDetectionRequest.getUrl() != null && !updateDetectionRequest.getUrl().isEmpty()) { //Let's check in the repo if the name are not null
            detection.setUrl(updateDetectionRequest.getUrl());
        }

        if (updateDetectionRequest.getImageUrl() != null && !updateDetectionRequest.getImageUrl().isEmpty()) { //Let's check in the repo if the name are not null
            detection.setImageUrl(updateDetectionRequest.getImageUrl());
        }

        if (updateDetectionRequest.getOrder() != null && !updateDetectionRequest.getOrder().isEmpty()) { //Let's check in the repo if the name are not null
            detection.setOrder(updateDetectionRequest.getOrder());
        }

        if (updateDetectionRequest.getSponsored() != null && !updateDetectionRequest.getSponsored().isEmpty()) { //Let's check in the repo if the name are not null
            detection.setSponsored(updateDetectionRequest.getSponsored());
        }

        if (updateDetectionRequest.getPrice() != null && !updateDetectionRequest.getPrice().isEmpty()) { //Let's check in the repo if the name are not null
            detection.setPrice(updateDetectionRequest.getPrice());
        }

        detection = detectionRepository.save(detection);
        return detection;
    }

    public String deleteDetection(long id) {
        detectionRepository.deleteById(id);
        return "Student has been deleted successfully";
    }
}
