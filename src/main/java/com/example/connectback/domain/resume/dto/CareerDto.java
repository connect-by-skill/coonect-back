package com.example.connectback.domain.resume.dto;

import com.example.connectback.domain.resume.entity.Career;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerDto {

    private int period;
    private String category;

    public CareerDto(Career career) {
        this.period = career.getPeriod();
        this.category = career.getCategory().getDisplayName();
    }
}
