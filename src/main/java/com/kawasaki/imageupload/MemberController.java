package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.model.Member;
import com.kawasaki.imageupload.file_data.MemberRepository;
import com.kawasaki.imageupload.security.model.User;
import com.kawasaki.imageupload.security.model.UserDTO;
import com.kawasaki.imageupload.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody UserDTO userData) {
        if (memberRepository.findByUserNameIs(userData.getUserName()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userRepository.save(new User(userData.getUserName(), pwEncoder.encode(userData.getPassWord())));
        memberRepository.save(new Member(userData.getUserName(), userData.getNickName(), ""));

        return new ResponseEntity<Member>(memberRepository.findByUserNameIs(userData.getUserName()).orElse(null), HttpStatus.OK); //TODO: proper database error handling
    }

    @GetMapping
    public ResponseEntity<Member> getProfile(Authentication authentication){
        var userData = memberRepository.findByUserNameIs(authentication.getName()).orElse(null);
        if (userData == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Member>(memberRepository.findByUserNameIs(userData.getUserName()).orElse(null),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Member> updateProfile(@RequestBody Member member, Authentication authentication){ //TODO: make memberDTO that don't have ID or submissions
        var oldData = memberRepository.findByUserNameIs(authentication.getName()).orElse(null);
        if (oldData == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        member.setId(oldData.getId()); // Temp solution, to prevent modifying other people's profile before memberDTO is ready.
        memberRepository.save(member);

        return new ResponseEntity<Member>(memberRepository.findByUserNameIs(member.getUserName()).orElse(null), HttpStatus.OK);
    }
}
