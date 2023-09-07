package com.example.connectback.global.batch.json.barrier_free_certified_workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BarrierFreeCertifiedWorkplaceApiWriter implements ItemWriter<BarrierFreeCertifiedWorkplace> {
    private final BarrierFreeCertifiedWorkplaceRepository barrierFreeCertifiedWorkplaceRepository;
    @Override
    public void write(List<? extends BarrierFreeCertifiedWorkplace> list) throws Exception {
        barrierFreeCertifiedWorkplaceRepository.deleteAll();
        List<BarrierFreeCertifiedWorkplace> barrierFreeCertifiedWorkplaces = new ArrayList<>();

        // dto -> entity
        list.forEach(barrierFreeCertifiedWorkplace->{
            barrierFreeCertifiedWorkplaces.add(barrierFreeCertifiedWorkplace);
        });

        barrierFreeCertifiedWorkplaceRepository.saveAll(barrierFreeCertifiedWorkplaces);
    }
}
