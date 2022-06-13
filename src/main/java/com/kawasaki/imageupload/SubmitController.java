package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.*;
import com.kawasaki.imageupload.file_data.model.Submission;
import com.kawasaki.imageupload.subscribe.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/submission")
public class SubmitController {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @GetMapping
    public Iterable<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @GetMapping(params = {"page","size"})
    public Iterable<Submission> getAllSubmissionPaged(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
        return submissionRepository.findAll(PageRequest.of(page, size, Sort.by("uploadDate").descending()));
    }

    @GetMapping("/userid/{userId}")
    public Iterable<Submission> getUserSubmissionsById(@PathVariable Integer userId){
        return submissionRepository.findByuploaderId(userId);
    }

    @GetMapping("/owned")
    public ResponseEntity<Iterable<Submission>> getCurrentUserSubmission(Authentication authentication) {
        var member = memberRepository.findByUserNameIs(authentication.getName()).orElse(null); // TODO: shrink null check into one line
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(member.getSubmissions(), HttpStatus.OK);
    }

    @GetMapping("/subscribed")
    public ResponseEntity<Iterable<Submission>> getSubscribedFeed(Authentication authentication){
        var subscribeProfile = subscriberRepository.findByUsername(authentication.getName()).orElse(null); // TODO: shrink null check into one line
        if (subscribeProfile == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(submissionRepository.findByRelatedTags(subscribeProfile.getSubscribedTags()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Submission> addSubmission(@RequestParam MultipartFile file, @RequestParam String title, @RequestParam String description, @RequestParam(required = false) Set<String> tags, Authentication authentication) throws IOException {
        var member = memberRepository.findByUserNameIs(authentication.getName()).orElse(null);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Submission submission = new Submission(
                title,
                description,
                member,
                new Date(System.currentTimeMillis()),
                gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), null).toHexString(),
                tags
        );

        submissionRepository.save(submission);
        return new ResponseEntity<>(submissionRepository.findByFileKey(submission.getFileKey()).orElse(null), HttpStatus.OK);
    }
}
