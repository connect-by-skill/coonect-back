package com.example.connectback.global.batch.json.health_center;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCenterInfoRepository extends JpaRepository<HealthCenterInfo,Long> {
}
