package com.example.connectback.global.security;

import com.example.connectback.domain.member.dto.MemberDto;
import com.example.connectback.domain.member.dto.RequestLogin;
import com.example.connectback.domain.member.service.MemberService;
import com.example.connectback.global.error.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final MemberService memberService;
    private final Environment env;
    private final RedisTemplate<String,Object> redisTemplate;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                MemberService memberService,
                                Environment env,
                                RedisTemplate<String,Object> redisTemplate) {
        super.setAuthenticationManager(authenticationManager);
        this.memberService = memberService;
        this.env = env;
        this.redisTemplate = redisTemplate;
    }
    /**
     * 로그인하면 제일 먼저 실행
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                            // 역할 정보
                    )
            );
            //사용자로부터 입력 받은 정보를 토큰으로 바꿔서 매니저로 넘기면
            // 아이디와 패스워드를 비교하겠다는 것
        } catch (IOException e){
            throw new EntityNotFoundException("로그인에 실패했습니다.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        String userEmail = (((User)authResult.getPrincipal()).getUsername());
        MemberDto userDetails = memberService.getUserByEmail(userEmail);
        log.debug(userDetails.toString());

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userDetails.getEmail()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.access_expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.refresh_expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("atk",accessToken);
        body.put("rtk", refreshToken);
        body.put("userEmail",userDetails.getEmail());
        body.put("role",userDetails.getDisabilityType().toString());
        body.put("expirationTime", env.getProperty("token.access_expiration_time"));

////        redis에 refresh token 저장
//        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
//        vop.set(userEmail,refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(),body);
    }
}
