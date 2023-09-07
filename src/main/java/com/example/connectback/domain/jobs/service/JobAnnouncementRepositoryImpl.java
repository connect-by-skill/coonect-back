package com.example.connectback.domain.jobs.service;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.domain.jobs.repository.JobAnnouncementRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.connectback.domain.jobs.entity.QJobAnnouncement.jobAnnouncement;


@Repository
@RequiredArgsConstructor
public class JobAnnouncementRepositoryImpl implements JobAnnouncementRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public Page<JobAnnouncement> searchJobAnnouncementsWithKeyword(Pageable pageable,
                                                                   String typeOfEmployment,
                                                                   String companyType,
                                                                   Boolean requiredExperienceExists,
                                                                   String businessAddress,
                                                                   String keyword) {
        JPQLQuery<JobAnnouncement> query = new JPAQueryFactory(entityManager)
                .selectFrom(jobAnnouncement);

        // 검색 조건 추가
        if (typeOfEmployment != null && !typeOfEmployment.isEmpty()) {
            query.where(jobAnnouncement.typeOfEmployment.eq(typeOfEmployment));
        }

        if (companyType != null && !companyType.isEmpty()) {
            query.where(jobAnnouncement.companyType.containsIgnoreCase(companyType));
        }

        // 검색 파라미터가 비어있을 때 해당 조건을 건너뛰도록 처리
        if (businessAddress != null && !businessAddress.isEmpty()) {
            query.where(jobAnnouncement.businessAddress.containsIgnoreCase(businessAddress));
        }

        if (requiredExperienceExists != null) {
            if (requiredExperienceExists) {
                query.where(jobAnnouncement.requiredExperience.ne("무관").and(jobAnnouncement.requiredExperience.ne("0년개월")));
            } else {
                query.where(jobAnnouncement.requiredExperience.eq("무관").or(jobAnnouncement.requiredExperience.eq("0년개월")));
            }
        }

        if (keyword != null && !keyword.isEmpty()) {
            query.where(jobAnnouncement.recruitmentType.containsIgnoreCase(keyword)
                    .or(jobAnnouncement.companyName.containsIgnoreCase(keyword)));
        }

        // 페이징 처리
        return PageableExecutionUtils.getPage(
                query
                        .orderBy(jobAnnouncementSort(pageable))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                query::fetchCount
        );
    }

    private OrderSpecifier<?> jobAnnouncementSort(Pageable pageable) {
        if (!pageable.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : pageable.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()){
                    case "id":
                        return new OrderSpecifier(direction, jobAnnouncement.id);
                    case "recruitmentPeriod":
                        return new OrderSpecifier(direction, jobAnnouncement.recruitmentPeriod);
                }
            }
        }
        return null;
    }
}
