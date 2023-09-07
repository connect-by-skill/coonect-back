package com.example.connectback.domain.resume.entity;

public enum PreferKeyword {
    무관("무관"),
    신입("신입"),
    경력("경력"),
    연봉("연봉"),
    대기업("대기업"),
    중소("중소"),
    공사("공사"),
    공공("공공"),
    협회("협회"),
    단체("단체"),
    개인("개인"),
    외국계("외국계"),
    계약직("계약직"),
    상용직("상용직"),
    안전사업장("안전사업장"),
    배리어프리("배리어프리"),
    건강센터("건강센터"),
    서울("서울"),
    경기도("경기도"),
    경상북도("경상북도"),
    경상남도("경상남도"),
    충청북도("충청북도"),
    충청남도("충청남도"),
    전라북도("전라북도"),
    전라남도("전라남도"),
    강원도("강원도"),
    구미("구미"),
    대구("대구"),
    부산("부산"),
    인천("인천");



    private final String displayName;

    PreferKeyword(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
