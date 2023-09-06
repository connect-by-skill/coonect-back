package com.example.connectback.global.batch.json.job_annoucement;


import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.domain.jobs.repository.JobAnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JobAnnouncementApiWriter implements ItemWriter<JobAnnouncement> {
    private final JobAnnouncementRepository jobAnnoucementRepository;

    @Override
    public void write(List<? extends JobAnnouncement> list) throws Exception {
        List<JobAnnouncement> jobAnnoucements = new ArrayList<>();

        // dto -> entity
        list.forEach(jobAnnoucement->{
            jobAnnoucements.add(jobAnnoucement);
        });

        jobAnnoucementRepository.saveAll(jobAnnoucements);
    }
}