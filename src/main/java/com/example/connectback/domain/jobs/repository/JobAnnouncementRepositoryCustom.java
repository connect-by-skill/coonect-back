package com.example.connectback.domain.jobs.repository;


import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobAnnouncementRepositoryCustom {

    Page<JobAnnouncement> searchJobAnnouncementsWithKeyword(Pageable pageable,
                                                            String typeOfEmployment,
                                                            String companyType,
                                                            Boolean requiredExperienceExists,
                                                            String businessAddress,
                                                            String keyword);
}
