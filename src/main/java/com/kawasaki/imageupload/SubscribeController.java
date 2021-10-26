package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.MemberRepository;
import com.kawasaki.imageupload.file_data.Tag;
import com.kawasaki.imageupload.file_data.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping
    public ResponseEntity subscribeTag(@RequestBody String tag, Authentication authentication){
        var userData = memberRepository.findByUserNameIs(authentication.getName()).orElse(null);
        if (userData == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Tag toSubscribe = tagRepository.findByName(tag).orElse(new Tag(tag));
        userData.addSubscribedTag(toSubscribe);
        memberRepository.save(userData);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity unsubscribeTag(@RequestBody String tag, Authentication authentication){
        var userData = memberRepository.findByUserNameIs(authentication.getName()).orElse(null);
        if (userData == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Tag toUnsub = userData.getSubscribedTags().stream().filter(t -> t.getName().equals(tag)).findFirst().orElse(null);
        if (toUnsub == null){return new ResponseEntity(HttpStatus.BAD_REQUEST);}

        userData.removeSubscribedTag(toUnsub);
        memberRepository.save(userData);
        return new ResponseEntity(HttpStatus.OK);
    }
}
