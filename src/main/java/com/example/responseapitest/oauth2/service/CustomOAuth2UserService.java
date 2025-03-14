package com.example.responseapitest.oauth2.service;

import com.example.responseapitest.domain.user.Role;
import com.example.responseapitest.domain.user.dto.UserDTO;
import com.example.responseapitest.domain.user.entity.User;
import com.example.responseapitest.domain.user.repository.UserRepository;
import com.example.responseapitest.oauth2.dto.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User);

        //서비스가 어느 플랫폼에서 온 요청인지 확인하기 위한 아이디
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        System.out.println("registrationId = " + registrationId);

        switch (registrationId) {
            case "naver" -> oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
            case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            case "kakao" -> oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
            default -> {
                return null;
            }
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String username = oAuth2Response.getProvider()+"_"+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if(existData == null){
            User user = User.builder()
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .name(oAuth2Response.getName())
                    .profileImage(oAuth2Response.getProfileImage())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);

            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .profileImage(oAuth2Response.getProfileImage())
                    .role(Role.USER.getRoles())
                    .build();
            return new CustomOAuth2User(userDTO);
        }
        else {
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());
            existData.setProfileImage(oAuth2Response.getProfileImage());

            userRepository.save(existData);

            UserDTO userDTO = UserDTO.builder()
                    .username(existData.getUsername())
                    .name(existData.getName())
                    .profileImage(existData.getProfileImage())
                    .role(existData.getRole().getRoles())
                    .build();
            return new CustomOAuth2User(userDTO);
        }
    }
}
