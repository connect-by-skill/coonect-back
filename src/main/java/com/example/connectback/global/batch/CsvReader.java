package com.example.connectback.global.batch;

import com.example.connectback.domain.jobs.dto.JobAnnouncementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class CsvReader {
    @Bean
    public ItemReader<? extends JobAnnouncementDto> csvFileItemReader() {
        String[] headers = new String[]{"id","applicationDate", "recruitmentPeriod", "companyName", "recruitmentType", "typeOfEmployment", "formOfWages", "wage", "entryForm", "requiredExperience", "requiredEducation", "majorField", "requiredCredentials", "businessAddress", "companyType", "responsibleAgency", "registrationDate", "contactNumber"};
        return getFileItemReaderFromCsvFile("csv/JobAnnoucement(20230101~20230131).csv",headers, JobAnnouncementDto.class);
    }

    private static <T> FlatFileItemReader<T> getFileItemReaderFromCsvFile(String path, String[] headers, Class<T> targetType) {
        /* file read */
        FlatFileItemReader<T> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(path));
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding

        /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
        DefaultLineMapper<T> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames(headers);
        delimitedLineTokenizer.setStrict(true);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<T> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(targetType);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}