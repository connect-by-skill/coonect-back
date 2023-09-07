package com.example.connectback.global.batch.json.accident_workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AccidentWorkplaceApiWriter implements ItemWriter<AccidentWorkplace> {
    private final AccidentWorkplaceRepository accidentWorkplaceRepository;

    @Override
    public void write(List<? extends AccidentWorkplace> list) throws Exception {
        List<AccidentWorkplace> accidentWorkplaces = new ArrayList<>();

        // dto -> entity
        list.forEach(accidentWorkplace->{
            accidentWorkplaces.add(accidentWorkplace);
        });

        accidentWorkplaceRepository.saveAll(accidentWorkplaces);
    }
}
