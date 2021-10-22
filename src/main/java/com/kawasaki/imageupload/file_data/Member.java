package com.kawasaki.imageupload.file_data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;

    @Nullable
    private String nickName;

    @Nullable
    private String introduction;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Collection<MemberAttribute> attributes;
    // Gender, Orientations, Age, Occupations etc...

    @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Submission> submissions;

    @ManyToMany
    private Collection<SubmissionTag> subscribedTags;

    public Member(String userName,String nickName, String introduction) {
        this.nickName = nickName;
        this.userName = userName;
        this.introduction = introduction;
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }
}
