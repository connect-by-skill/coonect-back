package com.example.connectback.global.batch;

import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ApiReader {
    private String serviceKey = "vdGJEuz%2B3h6ABcRGeQGy8ZFlf9w4QYyvY4pCW3PD4YrofQFod1ylz6kX870R98FprfaItjyPu1Y1V%2BCNjGkn6Q%3D%3D";

    @Bean
    public ItemReader<JobAnnouncement> jobAnnouncementItemReader() throws URISyntaxException, JsonProcessingException {
        List<JobAnnouncement> jobAnnouncements = getFileItemReaderFromJobAnnouncementApi(JobAnnouncement.class);
        return new IteratorItemReader<>(jobAnnouncements);
    }

    private List<JobAnnouncement> getFileItemReaderFromJobAnnouncementApi(Class<JobAnnouncement> jobAnnouncementClass) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        int currentPage = 0;
        int currentCount = 1000;
        int totalCount = 1001;

        List<JobAnnouncement> result = new ArrayList<>();

        while (currentCount!=0){
            currentPage++;
            log.info("job announcement info totalCount : {}, currentPage : {}, currentCount : {}",totalCount, currentPage, currentCount);
            String uri = "https://api.odcloud.kr/api/3072637/v1/uddi:0b4f66ca-3427-466c-8488-b9a302cfa0d8?"
                    + "page=" + currentPage + "&perPage=" + 1000 + "&serviceKey=" + serviceKey;
            URI uri1 = new URI(uri);

            //log.info("Fetching data from an external API by using the url: {}", uri);

            ResponseEntity<String> response = restTemplate.exchange(uri1, HttpMethod.GET,
                    new HttpEntity<>(headers), String.class);

            // Json parsing
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseObject = objectMapper.readValue(response.getBody(),
                    new TypeReference<Map<String, Object>>() {});

            ArrayList<Map<String,?>> dataProperty = (ArrayList<Map<String, ?>>) responseObject.get("data");
            List<JobAnnouncement> accidentWorkplaces = dataProperty.stream().map((Map<String, ?> t) -> new JobAnnouncement(t)).collect(Collectors.toList());

            currentCount = Integer.parseInt(responseObject.get("currentCount").toString());
            currentPage = Integer.parseInt(responseObject.get("page").toString());
            totalCount = Integer.parseInt(responseObject.get("totalCount").toString());
            result.addAll(accidentWorkplaces);
        }

        return result;
    }
}
