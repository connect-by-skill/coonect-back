package com.example.connectback.global.batch.json.employment_information;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmploymentInformationDeleteTasklet implements Tasklet {
    private final EmploymentInformationRepository employmentInformationRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        employmentInformationRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
