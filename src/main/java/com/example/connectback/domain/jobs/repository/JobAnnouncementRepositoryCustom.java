package com.example.connectback.domain.jobs.repository;


import com.example.connectback.domain.jobs.entity.JobAnnouncement;

import java.util.List;

public interface JobAnnouncementRepositoryCustom {
    List<JobAnnouncement> searchJobAnnouncements(String requiredEducation,
                                                 String entryForm,
                                                 String companyType,
                                                 Boolean requiredExperienceExists,
                                                 String businessAddress);
}
