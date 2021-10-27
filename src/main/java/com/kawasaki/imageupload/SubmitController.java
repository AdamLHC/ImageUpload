package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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
    private TagRepository tagRepository;

    @GetMapping
    public Iterable<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @GetMapping("/owned")
    public ResponseEntity<Iterable<Submission>> getCurrentUserSubmission(Authentication authentication) {
        var member = memberRepository.findByUserNameIs(authentication.getName()).orElse(null); // TODO: shrink null check into one line
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Iterable<Submission>>(member.getSubmissions(), HttpStatus.OK);
    }

    @GetMapping("/subscribed")
    public ResponseEntity<Iterable<Submission>> getSubscribedFeed(Authentication authentication){
        var member = memberRepository.findByUserNameIs(authentication.getName()).orElse(null); // TODO: shrink null check into one line
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Iterable<Submission>>(submissionRepository.findByByRelatedTags(member.getSubscribedTags().stream().map(t -> t.getName()).collect(Collectors.toList())/*, (Pageable)PageRequest.of(0,10)*/),HttpStatus.OK);
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
                tags.stream().map(t -> tagRepository.findByName(t).orElse(new Tag(t))).collect(Collectors.toList()) //TODO: Batch processing and not repeating call to database
        );

        submissionRepository.save(submission);
        return new ResponseEntity<Submission>(submissionRepository.findByFileKey(submission.getFileKey()).orElse(null), HttpStatus.OK);
    }
}
