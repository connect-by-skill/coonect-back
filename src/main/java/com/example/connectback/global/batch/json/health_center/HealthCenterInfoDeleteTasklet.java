package com.example.connectback.global.batch.json.health_center;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HealthCenterInfoDeleteTasklet implements Tasklet {
    private final HealthCenterInfoRepository healthCenterInfoRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        healthCenterInfoRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
