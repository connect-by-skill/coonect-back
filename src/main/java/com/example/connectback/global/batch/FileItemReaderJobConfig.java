package com.example.connectback.global.batch;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.global.batch.json.accident_workplace.AccidentWorkplace;
import com.example.connectback.global.batch.json.accident_workplace.AccidentWorkplaceApiWriter;
import com.example.connectback.global.batch.json.accident_workplace.AccidentWorkplaceDeleteTasklet;
import com.example.connectback.global.batch.json.barrier_free_certified_workplace.BarrierFreeCertifiedWorkplace;
import com.example.connectback.global.batch.json.barrier_free_certified_workplace.BarrierFreeCertifiedWorkplaceApiWriter;
import com.example.connectback.global.batch.json.barrier_free_certified_workplace.BarrierFreeCertifiedWorkplaceDeleteTasklet;
import com.example.connectback.global.batch.json.employment_information.EmploymentInformation;
import com.example.connectback.global.batch.json.employment_information.EmploymentInformationApiWriter;
import com.example.connectback.global.batch.json.employment_information.EmploymentInformationDeleteTasklet;
import com.example.connectback.global.batch.json.health_center.HealthCenterApiWriter;
import com.example.connectback.global.batch.json.health_center.HealthCenterInfo;
import com.example.connectback.global.batch.json.health_center.HealthCenterInfoDeleteTasklet;
import com.example.connectback.global.batch.json.high_percent_accident_workplace.HighPercentAccidentWorkplace;
import com.example.connectback.global.batch.json.high_percent_accident_workplace.HighPercentAccidentWorkplaceApiWriter;
import com.example.connectback.global.batch.json.high_percent_accident_workplace.HighPercentAccidentWorkplaceDeleteTasklet;
import com.example.connectback.global.batch.json.job_annoucement.JobAnnouncementApiWriter;
import com.example.connectback.global.batch.json.job_annoucement.JobAnnouncementDeleteTasklet;
import com.example.connectback.global.batch.json.risk_assessment_certified_workplace.RiskAssessmentCertifiedWorkplace;
import com.example.connectback.global.batch.json.risk_assessment_certified_workplace.RiskAssessmentCertifiedWorkplaceApiWriter;
import com.example.connectback.global.batch.json.risk_assessment_certified_workplace.RiskAssessmentCertifiedWorkplaceDeleteTasklet;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
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

    // 공고 정보
    private final JobAnnouncementApiWriter jobAnnouncementApiWriter;
    private final JobAnnouncementDeleteTasklet jobAnnouncementDeleteTasklet;

    private static final int chunkSize = 1000;
    // 건강센터 정보
    private final HealthCenterApiWriter healthCenterApiWriter;
    private final HealthCenterInfoDeleteTasklet healthCenterInfoDeleteTasklet;
    // 위험성평가 인정사업장 정보
    private final RiskAssessmentCertifiedWorkplaceApiWriter riskAssessmentCertifiedWorkplaceApiWriter;
    private final RiskAssessmentCertifiedWorkplaceDeleteTasklet riskAssessmentCertifiedWorkplaceDeleteTasklet;

    // 배리어프리 인증사업장 정보
    private final BarrierFreeCertifiedWorkplaceApiWriter barrierFreeCertifiedWorkplaceApiWriter;
    private final BarrierFreeCertifiedWorkplaceDeleteTasklet barrierFreeCertifiedWorkplaceDeleteTasklet;

    // 산업재해 중대산업사고 발생 사업장
    private final AccidentWorkplaceApiWriter accidentWorkplaceApiWriter;
    private final AccidentWorkplaceDeleteTasklet accidentWorkplaceDeleteTasklet;

    // 중대재해 발생이 규모별 동종업종 평균재해율 이상인 사업장
    private final HighPercentAccidentWorkplaceApiWriter highPercentAccidentWorkplaceApiWriter;
    private final HighPercentAccidentWorkplaceDeleteTasklet highPercentAccidentWorkplaceDeleteTasklet;

    // 장애인 고용정보
    private final EmploymentInformationApiWriter employmentInformationApiWriter;
    private final EmploymentInformationDeleteTasklet employmentInformationDeleteTasklet;

    @Scheduled(fixedDelay = 3456000000L) // 40일(밀리초 단위) 40 * 24 * 60 * 60 * 1000
    public void runJob() throws Exception {
        // Job 실행. 실행한 시간을 파라미터로 하여 언제 발생한 job인지 확인 가능
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
                .start(jobAnnouncementDeleteStep())
                .next(jobAnnouncementApiFileItemReaderStep())
                .build();
    }

    @Bean
    public Step jobAnnouncementDeleteStep() {
        return stepBuilderFactory.get("jobAnnouncementDeleteStep")
                .tasklet(jobAnnouncementDeleteTasklet)
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
                .start(healthCenterInfoDeleteStep())
                .next(healthCenterApiReaderStep())
                .build();
    }
    @Bean
    public Step healthCenterInfoDeleteStep() {
        return stepBuilderFactory.get("healthCenterInfoDeleteStep")
                .tasklet(healthCenterInfoDeleteTasklet)
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
                .start(riskAssessmentCertifiedWorkplaceDeleteStep())
                .next(riskAssessmentCertifiedWorkplaceApiReaderStep())
                .build();
    }
    @Bean
    public Step riskAssessmentCertifiedWorkplaceDeleteStep() {
        return stepBuilderFactory.get("riskAssessmentCertifiedWorkplaceDeleteStep")
                .tasklet(riskAssessmentCertifiedWorkplaceDeleteTasklet)
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
                .start(barrierFreeCertifiedWorkplaceDeleteStep())
                .next(barrierFreeCertifiedWorkplaceApiReaderStep())
                .build();
    }
    @Bean
    public Step barrierFreeCertifiedWorkplaceDeleteStep() {
        return stepBuilderFactory.get("barrierFreeCertifiedWorkplaceDeleteStep")
                .tasklet(barrierFreeCertifiedWorkplaceDeleteTasklet)
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
                .start(accidentWorkplaceDeleteStep())
                .next(accidentWorkplaceApiReaderStep())
                .build();
    }

    @Bean
    public Step accidentWorkplaceDeleteStep() {
        return stepBuilderFactory.get("accidentWorkplaceDeleteStep")
                .tasklet(accidentWorkplaceDeleteTasklet)
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
                .start(highPercentAccidentWorkplaceDeleteStep())
                .next(highPercentAccidentWorkplaceApiReaderStep())
                .build();
    }

    @Bean
    public Step highPercentAccidentWorkplaceDeleteStep() {
        return stepBuilderFactory.get("highPercentAccidentWorkplaceDeleteStep")
                .tasklet(highPercentAccidentWorkplaceDeleteTasklet)
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
                .start(employmentInformationDeleteStep())
                .next(employmentInformationApiReaderStep())
                .build();
    }
    @Bean
    public Step employmentInformationDeleteStep() {
        return stepBuilderFactory.get("employmentInformationDeleteStep")
                .tasklet(employmentInformationDeleteTasklet)
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