package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.MemberRepository;
import com.kawasaki.imageupload.subscribe.Subscriber;
import com.kawasaki.imageupload.subscribe.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @PostMapping
    public ResponseEntity subscribeTag(@RequestBody String tag, Authentication authentication){
        var subscribeData = subscriberRepository.findByUsername(authentication.getName())
                .map(s -> { s.addSubscribedTag(tag); return s; })
                .orElse(new Subscriber(authentication.getName(),Set.of(tag)));

        subscriberRepository.save(subscribeData);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity unsubscribeTag(@RequestBody String tag, Authentication authentication){
        var subscribeData = subscriberRepository.findByUsername(authentication.getName())
                .orElse(new Subscriber(authentication.getName()));

        // TODO: data entry exits check

        subscribeData.removeSubscribedTag(tag);
        subscriberRepository.save(subscribeData);

        return new ResponseEntity(HttpStatus.OK);
    }
}
