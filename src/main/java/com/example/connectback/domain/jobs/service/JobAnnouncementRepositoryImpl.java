package com.example.connectback.domain.jobs.service;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.domain.jobs.repository.JobAnnouncementRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.connectback.domain.jobs.entity.QJobAnnouncement.jobAnnouncement;


@Repository
@RequiredArgsConstructor
public class JobAnnouncementRepositoryImpl implements JobAnnouncementRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<JobAnnouncement> searchJobAnnouncements(String requiredEducation,
                                                        String entryForm,
                                                        String companyType,
                                                        Boolean requiredExperienceExists,
                                                        String businessAddress) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        BooleanExpression predicate = jobAnnouncement.isNotNull();

        if (requiredEducation != null && !requiredEducation.isEmpty()) {
            predicate = predicate.and(jobAnnouncement.requiredEducation.eq(requiredEducation));
        }

        if (entryForm != null && !entryForm.isEmpty()) {
            predicate = predicate.and(jobAnnouncement.entryForm.eq(entryForm));
        }

        if (companyType != null && !companyType.isEmpty()) {
            predicate = predicate.and(jobAnnouncement.companyType.eq(companyType));
        }

        if (requiredExperienceExists != null) {
            if (requiredExperienceExists) {
                predicate = predicate.and(jobAnnouncement.requiredExperience.isNotNull());
            } else {
                predicate = predicate.and(jobAnnouncement.requiredExperience.isNull());
            }
        }

        if (businessAddress != null && !businessAddress.isEmpty()) {
            predicate = predicate.and(jobAnnouncement.businessAddress.containsIgnoreCase(businessAddress));
        }

        return queryFactory.selectFrom(jobAnnouncement)
                .where(predicate)
                .fetch();
    }
}
