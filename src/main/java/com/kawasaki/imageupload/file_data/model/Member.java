package com.kawasaki.imageupload.file_data.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @ElementCollection
    @CollectionTable(name = "member_attribute",joinColumns = @JoinColumn(name = "member_id"))
    private Set<MemberAttribute> attributes;
    // Gender, Orientations, Age, Occupations etc...

    @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Submission> submissions;

    public Member(String userName, String nickName, String introduction) {
        this.nickName = nickName;
        this.userName = userName;
        this.introduction = introduction;
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }
}
