package com.kawasaki.imageupload;

import com.kawasaki.imageupload.file_data.MemberRepository;
import com.kawasaki.imageupload.file_data.Submission;
import com.kawasaki.imageupload.file_data.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/submission")
public class SubmitController {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubmissionRepository submissionRepository;


    @GetMapping
    public Iterable<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @GetMapping("/owned")
    public ResponseEntity<Iterable<Submission>> getCurrentUserSubmission(Authentication authentication) {
        var member = memberRepository.findByUserNameIs(authentication.getName()).orElse(null);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Iterable<Submission>>(member.getSubmissions(), HttpStatus.OK);
    }

    @PostMapping("/{title}/{description}")  //TODO: refactor variables to better variable format
    public ResponseEntity<Submission> addSubmission(
            @PathVariable String title, @PathVariable String description, @RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        var member = memberRepository.findByUserNameIs(authentication.getName()).orElse(null);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Submission submission = new Submission();
        submission.setTitle(title);
        submission.setDescriptive(description);
        submission.setUploader(member);
        submission.setUploadDate(new Date(System.currentTimeMillis()));
        submission.setFileKey(gridFsTemplate.store(file.getInputStream(),file.getOriginalFilename(),file.getContentType(),null).toHexString()); // Upload here
        submissionRepository.save(submission);

        return new ResponseEntity<Submission>(submissionRepository.findByFileKey(submission.getFileKey()).orElse(null), HttpStatus.OK);
    }
}
