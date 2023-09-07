package com.example.connectback.global.batch;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.global.batch.json.accident_workplace.AccidentWorkplace;
import com.example.connectback.global.batch.json.accident_workplace.AccidentWorkplaceApiWriter;
import com.example.connectback.global.batch.json.barrier_free_certified_workplace.BarrierFreeCertifiedWorkplace;
import com.example.connectback.global.batch.json.barrier_free_certified_workplace.BarrierFreeCertifiedWorkplaceApiWriter;
import com.example.connectback.global.batch.json.employment_information.EmploymentInformation;
import com.example.connectback.global.batch.json.employment_information.EmploymentInformationApiWriter;
import com.example.connectback.global.batch.json.health_center.HealthCenterApiWriter;
import com.example.connectback.global.batch.json.health_center.HealthCenterInfo;
import com.example.connectback.global.batch.json.high_percent_accident_workplace.HighPercentAccidentWorkplace;
import com.example.connectback.global.batch.json.high_percent_accident_workplace.HighPercentAccidentWorkplaceApiWriter;
import com.example.connectback.global.batch.json.job_annoucement.JobAnnouncementApiWriter;
import com.example.connectback.global.batch.json.risk_assessment_certified_workplace.RiskAssessmentCertifiedWorkplace;
import com.example.connectback.global.batch.json.risk_assessment_certified_workplace.RiskAssessmentCertifiedWorkplaceApiWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URISyntaxException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class FileItemReaderJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ApiReader apiReader;

    // job 재실행을 위한 도구
    private final BatchJobLauncher batchJobLauncher;

    // 발전소 정보
    private final JobAnnouncementApiWriter jobAnnouncementApiWriter;

    private static final int chunkSize = 1000;
    // 건강센터 정보
    private final HealthCenterApiWriter healthCenterApiWriter;
    // 위험성평가 인정사업장 정보
    private final RiskAssessmentCertifiedWorkplaceApiWriter riskAssessmentCertifiedWorkplaceApiWriter;

    // 배리어프리 인증사업장 정보
    private final BarrierFreeCertifiedWorkplaceApiWriter barrierFreeCertifiedWorkplaceApiWriter;

    // 산업재해 중대산업사고 발생 사업장
    private final AccidentWorkplaceApiWriter accidentWorkplaceApiWriter;

    // 중대재해 발생이 규모별 동종업종 평균재해율 이상인 사업장
    private final HighPercentAccidentWorkplaceApiWriter highPercentAccidentWorkplaceApiWriter;

    // 장애인 고용정보
    private final EmploymentInformationApiWriter employmentInformationApiWriter;

    @Scheduled(fixedRate = 3888000000L) // 약 45일(3,888,000,000 밀리초)마다 실행
    public void scheduleJob() throws Exception {
        // 배치 잡을 스케줄링하기 위한 메서드
        batchJobLauncher.run(jobAnnouncementApiFileItemReaderJob());
        batchJobLauncher.run(healthCenterApiReaderJob());
        batchJobLauncher.run(riskAssessmentCertifiedWorkplaceApiReaderJob());
        batchJobLauncher.run(barrierFreeCertifiedWorkplaceApiReaderJob());
        batchJobLauncher.run(accidentWorkplaceApiReaderJob());
        batchJobLauncher.run(highPercentAccidentWorkplaceApiReaderJob());
        batchJobLauncher.run(employmentInformationApiReaderJob());
    }

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

    @Bean
    public Job healthCenterApiReaderJob() throws JsonProcessingException, URISyntaxException {
        return jobBuilderFactory.get("healthCenterApiReaderJob")
                .start(healthCenterApiReaderStep())
                .build();
    }

    @Bean
    public Step healthCenterApiReaderStep() throws JsonProcessingException, URISyntaxException {
        return stepBuilderFactory.get("healthCenterApiReaderStep")
                .<HealthCenterInfo, HealthCenterInfo>chunk(chunkSize)
                .reader(apiReader.apiItemReader())
                .writer(healthCenterApiWriter)
                .build();
    }

    @Bean
    public Job riskAssessmentCertifiedWorkplaceApiReaderJob() throws JsonProcessingException, URISyntaxException {
        return jobBuilderFactory.get("riskAssessmentCertifiedWorkplaceApiReaderJob")
                .start(riskAssessmentCertifiedWorkplaceApiReaderStep())
                .build();
    }

    @Bean
    public Step riskAssessmentCertifiedWorkplaceApiReaderStep() throws JsonProcessingException, URISyntaxException {
        return stepBuilderFactory.get("riskAssessmentCertifiedWorkplaceApiReaderStep")
                .<RiskAssessmentCertifiedWorkplace, RiskAssessmentCertifiedWorkplace>chunk(chunkSize)
                .reader(apiReader.riskAssessmentCertifiedWorkplaceItemReader())
                .writer(riskAssessmentCertifiedWorkplaceApiWriter)
                .build();
    }

    @Bean
    public Job barrierFreeCertifiedWorkplaceApiReaderJob() throws JsonProcessingException, URISyntaxException {
        return jobBuilderFactory.get("barrierFreeCertifiedWorkplaceApiReaderJob")
                .start(barrierFreeCertifiedWorkplaceApiReaderStep())
                .build();
    }

    @Bean
    public Step barrierFreeCertifiedWorkplaceApiReaderStep() throws JsonProcessingException, URISyntaxException {
        return stepBuilderFactory.get("barrierFreeCertifiedWorkplaceApiReaderStep")
                .<BarrierFreeCertifiedWorkplace, BarrierFreeCertifiedWorkplace>chunk(chunkSize)
                .reader(apiReader.barrierFreeCertifiedWorkplaceItemReader())
                .writer(barrierFreeCertifiedWorkplaceApiWriter)
                .build();
    }

    @Bean
    public Job accidentWorkplaceApiReaderJob() throws JsonProcessingException, URISyntaxException {
        return jobBuilderFactory.get("accidentWorkplaceApiReaderJob")
                .start(accidentWorkplaceApiReaderStep())
                .build();
    }

    @Bean
    public Step accidentWorkplaceApiReaderStep() throws JsonProcessingException, URISyntaxException {
        return stepBuilderFactory.get("accidentWorkplaceApiReaderStep")
                .<AccidentWorkplace, AccidentWorkplace>chunk(chunkSize)
                .reader(apiReader.accidentWorkplaceItemReader())
                .writer(accidentWorkplaceApiWriter)
                .build();
    }

    @Bean
    public Job highPercentAccidentWorkplaceApiReaderJob() throws JsonProcessingException, URISyntaxException {
        return jobBuilderFactory.get("highPercentAccidentWorkplaceApiReaderJob")
                .start(highPercentAccidentWorkplaceApiReaderStep())
                .build();
    }

    @Bean
    public Step highPercentAccidentWorkplaceApiReaderStep() throws JsonProcessingException, URISyntaxException {
        return stepBuilderFactory.get("highPercentAccidentWorkplaceApiReaderStep")
                .<HighPercentAccidentWorkplace, HighPercentAccidentWorkplace>chunk(chunkSize)
                .reader(apiReader.highPercentAccidentWorkplaceItemReader())
                .writer(highPercentAccidentWorkplaceApiWriter)
                .build();
    }

    @Bean
    public Job employmentInformationApiReaderJob() throws JsonProcessingException, URISyntaxException {
        return jobBuilderFactory.get("employmentInformationApiReaderJob")
                .start(employmentInformationApiReaderStep())
                .build();
    }

    @Bean
    public Step employmentInformationApiReaderStep() throws JsonProcessingException, URISyntaxException {
        return stepBuilderFactory.get("employmentInformationApiReaderStep")
                .<EmploymentInformation, EmploymentInformation>chunk(chunkSize)
                .reader(apiReader.employmentInformationItemReader())
                .writer(employmentInformationApiWriter)
                .build();
    }
}