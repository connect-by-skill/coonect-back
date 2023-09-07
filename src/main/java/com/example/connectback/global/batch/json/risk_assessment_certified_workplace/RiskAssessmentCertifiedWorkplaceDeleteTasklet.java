package com.example.connectback.global.batch.json.risk_assessment_certified_workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RiskAssessmentCertifiedWorkplaceDeleteTasklet implements Tasklet {
    private final RiskAssessmentCertifiedWorkplaceRepository riskAssessmentCertifiedWorkplaceRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        riskAssessmentCertifiedWorkplaceRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
