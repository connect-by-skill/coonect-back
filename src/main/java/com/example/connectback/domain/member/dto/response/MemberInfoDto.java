package com.example.connectback.domain.member.dto.response;

import com.example.connectback.domain.member.entity.Disability;
import com.example.connectback.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private String username;
    private int age;
    private String email;
    private String addressInfo;
    private String addressDetails;
    private Disability disabilityType;

    @Builder
    public MemberInfoDto(MemberEntity memberEntity) {
        this.username = memberEntity.getUsername();
        this.email = memberEntity.getEmail();
        this.age = memberEntity.getAge();
        this.addressInfo = memberEntity.getAddressInfo();
        this.addressDetails = memberEntity.getAddressDetails();
        this.disabilityType = memberEntity.getDisabilityType();
    }
}
