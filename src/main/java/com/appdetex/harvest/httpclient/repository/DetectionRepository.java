package com.appdetex.harvest.httpclient.repository;

import com.appdetex.harvest.httpclient.entity.Detection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DetectionRepository extends JpaRepository<Detection, Long> {
}
