package com.example.connectback.global.batch.json.high_percent_accident_workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class HighPercentAccidentWorkplaceApiWriter implements ItemWriter<HighPercentAccidentWorkplace> {
    private final HighPercentAccidentWorkplaceRepository highPercentAccidentWorkplaceRepository;
    @Override
    public void write(List<? extends HighPercentAccidentWorkplace> list) throws Exception {
        highPercentAccidentWorkplaceRepository.deleteAll();
        List<HighPercentAccidentWorkplace> highPercentAccidentWorkplaces = new ArrayList<>();

        // dto -> entity
        list.forEach(highPercentAccidentWorkplace->{
            highPercentAccidentWorkplaces.add(highPercentAccidentWorkplace);
        });

        highPercentAccidentWorkplaceRepository.saveAll(highPercentAccidentWorkplaces);
    }
}
