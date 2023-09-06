package com.example.connectback.global.batch;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.global.batch.json.job_annoucement.JobAnnouncementApiWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final ApiReader apiReader;

    // 발전소 정보
    private final JobAnnouncementApiWriter jobAnnouncementApiWriter;
    
    private static final int chunkSize = 1000;

    @Bean
    public Job jobAnnouncementApiFileItemReaderJob() throws URISyntaxException, JsonProcessingException {
        return jobBuilderFactory.get("jobAnnouncementApiReaderJob")
                .start(jobAnnouncementApiFileItemReaderStep())
                .build();
    }

    @Bean
    public Step jobAnnouncementApiFileItemReaderStep() throws URISyntaxException, JsonProcessingException {
        return stepBuilderFactory.get("jobAnnouncementApiReaderStep")
                .<JobAnnouncement, JobAnnouncement>chunk(chunkSize)
                .reader(apiReader.jobAnnouncementItemReader())
                .writer(jobAnnouncementApiWriter)
                .build();
    }
}
