package com.example.connectback.global.batch.json.health_center;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class HealthCenterApiWriter implements ItemWriter<HealthCenterInfo> {

    private final HealthCenterInfoRepository healthCenterInfoRepository;

    @Override
    public void write(List<? extends HealthCenterInfo> list) throws Exception {
        healthCenterInfoRepository.deleteAll();
        List<HealthCenterInfo> healthCenterInfos = new ArrayList<>();

        // dto -> entity
        list.forEach(healthCenterInfo->{
            healthCenterInfos.add(healthCenterInfo);
        });

        healthCenterInfoRepository.saveAll(healthCenterInfos);
    }
}
