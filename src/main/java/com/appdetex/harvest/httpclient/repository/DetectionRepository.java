package com.appdetex.harvest.httpclient.repository;

import com.appdetex.harvest.httpclient.entity.Detection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectionRepository extends JpaRepository<Detection, Long> {
}
