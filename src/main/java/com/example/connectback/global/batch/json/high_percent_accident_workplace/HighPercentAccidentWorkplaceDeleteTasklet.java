package com.example.connectback.global.batch.json.high_percent_accident_workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HighPercentAccidentWorkplaceDeleteTasklet implements Tasklet {
    private final HighPercentAccidentWorkplaceRepository highPercentAccidentWorkplaceRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        highPercentAccidentWorkplaceRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
