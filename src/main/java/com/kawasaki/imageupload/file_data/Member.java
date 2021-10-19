package com.kawasaki.imageupload.file_data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String notes;

    @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Submission> submissions;

    public Member(String userName,String nickName, String notes) {
        this.nickName = nickName;
        this.userName = userName;
        this.notes = notes;
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }
}
