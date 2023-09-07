package com.example.connectback.global.batch.json.barrier_free_certified_workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BarrierFreeCertifiedWorkplaceDeleteTasklet implements Tasklet {
    private final BarrierFreeCertifiedWorkplaceRepository barrierFreeCertifiedWorkplaceRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        barrierFreeCertifiedWorkplaceRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
