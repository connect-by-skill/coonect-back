package com.example.connectback.domain.resume.entity;


import com.example.connectback.domain.resume.exception.NotMatchJobCategory;
import com.example.connectback.global.error.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum Certification {
    간호사("간호사"),
    사회복지사("사회복지사"),
    물류관리사("물류관리사"),
    무역영어("무역영어"),
    요양보호사("요양보호사"),
    보일러기능사("보일러기능사"),
    공조냉동기계기능사("공조냉동기계기능사"),
    자동차운전면허("자동차운전면허"),
    바리스타_2급("바리스타 2급"),
    바리스타_1급("바리스타 1급");

    private final String displayName;

    Certification(String displayName) {
        this.displayName = displayName;
    }
    public static Certification findByDisplayName(String displayName) {
        for (Certification certification : Certification.values()) {
            if (certification.getDisplayName().equals(displayName)) {
                return certification;
            }
        }
        throw new NotMatchJobCategory(ErrorCode.JOB_CATEGORY_NOT_FOUND);
    }

    public static List<Certification> findByDisplayNameList(List<String> certifications){
        List<Certification> certificationList = new ArrayList<>();
        for (String value : certifications){
            Certification certification = findByDisplayName(value);
            certificationList.add(certification);
        }
        return certificationList;
    }

    public static List<String> getDisplayNameList(List<Certification> certifications) {
        return certifications.stream()
                .map(Certification::getDisplayName)
                .collect(Collectors.toList());
    }

    public String getDisplayName() {
        return displayName;
    }
}
