package com.example.connectback.global.batch.json.employment_information;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class EmploymentInformationApiWriter implements ItemWriter<EmploymentInformation> {
    private final EmploymentInformationRepository employmentInformationRepository;
    @Override
    public void write(List<? extends EmploymentInformation> list) throws Exception {
        employmentInformationRepository.deleteAll();
        List<EmploymentInformation> employmentInformations = new ArrayList<>();

        // dto -> entity
        list.forEach(employmentInformation->{
            employmentInformations.add(employmentInformation);
        });

        employmentInformationRepository.saveAll(employmentInformations);
    }
}
