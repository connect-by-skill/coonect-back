package com.example.connectback.domain.member.service;

import com.example.connectback.domain.member.dto.JoinMemberDto;
import com.example.connectback.domain.member.dto.MemberDto;
import com.example.connectback.domain.member.dto.response.MemberInfoDto;
import com.example.connectback.domain.member.entity.Disability;
import com.example.connectback.domain.member.entity.MemberEntity;
import com.example.connectback.domain.member.repository.MemberRepository;
import com.example.connectback.global.error.exception.EntityNotFoundException;
import com.example.connectback.global.error.exception.InvalidValueException;
import com.example.connectback.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public MemberDto getUserByEmail(String userEmail) {
        MemberEntity member = memberRepository.findByEmail(userEmail)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        MemberDto dto = Optional.ofNullable(member).map(MemberDto::new)
                .orElseThrow(()-> new InvalidValueException("dto 변환 실패"));

        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(username)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        return new User(memberEntity.getEmail(), memberEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Transactional
    public MemberDto createUser(JoinMemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            log.info("이미 가입된 유저");
            throw new InvalidValueException("이미 가입한 유저입니다.");
        }

        MemberEntity member = MemberEntity.builder()
                .username(memberDto.getUsername())
                .age(memberDto.getAge())
                .email(memberDto.getEmail())
                .encryptedPwd(passwordEncoder.encode(memberDto.getEncryptedPwd()))
                .addressInfo(memberDto.getAddressInfo())
                .addressDetails(memberDto.getAddressDetails())
                .disabilityType(Disability.findByDisplayName(memberDto.getDisabilityType()))
                .build();

        MemberEntity registeredMember = memberRepository.save(member);

        MemberDto response = Optional.ofNullable(registeredMember)
                .map(MemberDto::new)
                .orElseThrow(()->new InvalidValueException("entity -> dto 변환 에러"));

        return response;
    }

    public MemberInfoDto getMyInfo(String token) {
        String userEmail = tokenProvider.validateJwtAndGetUserEmail(token);

        MemberEntity member = memberRepository.findByEmail(userEmail)
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        MemberInfoDto dto = Optional.ofNullable(member).map(MemberInfoDto::new)
                .orElseThrow(()-> new InvalidValueException("dto 변환 실패"));

        return dto;
    }
}
