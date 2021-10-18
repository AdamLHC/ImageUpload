package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.Member;
import com.kawasaki.imageupload.file_data.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private InMemoryUserDetailsManager userManager;

    @PostMapping("/{username}/{nickname}/{password}")
    public ResponseEntity<Member> addMember(@PathVariable String username, @PathVariable String nickname, @PathVariable String password) {
        userManager.createUser(new User(username, pwEncoder.encode(password), new ArrayList<GrantedAuthority>()));

        memberRepository.save(new Member(username, nickname, ""));

        return new ResponseEntity<Member>(memberRepository.findByUserNameIs(username).orElse(null), HttpStatus.OK); //TODO: proper database error handling
    }
}
