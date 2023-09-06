package com.example.connectback.domain.member.entity;


import com.example.connectback.global.error.exception.InvalidValueException;

public enum Disability {
    시각장애_중증("시각장애 중증"),
    지체장애_중증("지체장애 중증"),
    지적장애_중증("지적장애 중증"),
    뇌병변장애_중증("뇌병변장애 중증"),
    청각장애_중증("청각장애 중증"),
    뇌전증장애_중증("뇌전증장애 중증"),
    정신장애_중증("정신장애 중증"),
    자폐성장애_중증("자폐성장애 중증"),
    신장장애_중증("신장장애 중증"),
    언어장애_중증("언어장애 중증"),
    심장장애_중증("심장장애 중증"),
    안면장애_중증("안면장애 중증"),
    호흡기장애_중증("호흡기장애 중증"),
    간장애_중증("간장애 중증"),
    장루요루장애_중증("장루요루장애 중증"),
    시각장애_경증("시각장애 경증"),
    지체장애_경증("지체장애 경증"),
    지적장애_경증("지적장애 경증"),
    뇌병변장애_경증("뇌병변장애 경증"),
    청각장애_경증("청각장애 경증"),
    뇌전증장애_경증("뇌전증장애 경증"),
    정신장애_경증("정신장애 경증"),
    자폐성장애_경증("자폐성장애 경증"),
    신장장애_경증("신장장애 경증"),
    언어장애_경증("언어장애 경증"),
    심장장애_경증("심장장애 경증"),
    안면장애_경증("안면장애 경증"),
    호흡기장애_경증("호흡기장애 경증"),
    간장애_경증("간장애 경증"),
    장루요루장애_경증("장루요루장애 경증"),
    국가유공("국가유공"),
    기타("기타");

    private final String value;

    Disability(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Disability findByDisplayName(String displayName) {
        for (Disability disability : Disability.values()) {
            if (disability.getValue().equals(displayName)) {
                return disability;
            }
        }
        throw new InvalidValueException("일치하는 disability가 없음");
    }
}

