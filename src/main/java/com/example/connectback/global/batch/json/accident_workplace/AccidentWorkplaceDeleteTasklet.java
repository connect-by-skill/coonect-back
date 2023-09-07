package com.example.connectback.global.batch.json.accident_workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccidentWorkplaceDeleteTasklet implements Tasklet {
    private final AccidentWorkplaceRepository accidentWorkplaceRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        accidentWorkplaceRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
