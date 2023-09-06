package com.example.connectback.domain.jobs.repository;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobAnnouncementRepository extends JpaRepository<JobAnnouncement, Long> {
    Page<JobAnnouncement> findAll(Pageable pageable);
    @Query("SELECT ja FROM JobAnnouncement ja ORDER BY SIZE(ja.members) DESC")
    Page<JobAnnouncement> findAllOrderByMembersCount(Pageable pageable);
}
