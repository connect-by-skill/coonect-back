package com.example.connectback.global.batch.json.job_annoucement;

import com.example.connectback.domain.jobs.repository.JobAnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobAnnouncementDeleteTasklet implements Tasklet {
    private final JobAnnouncementRepository jobAnnouncementRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        jobAnnouncementRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
