package com.example.connectback.domain.resume.dto;

import com.example.connectback.domain.resume.entity.Career;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerRequestDto {

    private int period;
    private String category;

    public CareerRequestDto(Career career) {
        this.period = career.getPeriod();
        this.category = career.getCategory().getDisplayName();
    }
}
