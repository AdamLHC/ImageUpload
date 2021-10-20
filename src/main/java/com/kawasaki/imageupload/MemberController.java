package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.Member;
import com.kawasaki.imageupload.file_data.MemberRepository;
import com.kawasaki.imageupload.security.User;
import com.kawasaki.imageupload.security.UserDTO;
import com.kawasaki.imageupload.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody UserDTO userData) {
        //userManager.createUser(new User(userData.getUserName(), pwEncoder.encode(userData.getPassWord()), new ArrayList<GrantedAuthority>()));
        userRepository.save(new User(userData.getUserName(), pwEncoder.encode(userData.getPassWord())));
        memberRepository.save(new Member(userData.getUserName(), userData.getNickName(), ""));

        return new ResponseEntity<Member>(memberRepository.findByUserNameIs(userData.getUserName()).orElse(null), HttpStatus.OK); //TODO: proper database error handling
    }
}
