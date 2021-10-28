package com.kawasaki.imageupload.file_data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String fileKey;

    private String title;

    private String descriptive;

    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member uploader;

    @ElementCollection
    @CollectionTable(name = "submission_tags",joinColumns = @JoinColumn(name = "submission_id"))
    @Column(name = "tag")
    private Set<String> tags;

    public Submission(String title, String description, Member member, Date date, String fileKey,Set<String> tags) {
        this.title = title;
        this.descriptive = description;
        this.uploader = member;
        this.uploadDate = date;
        this.fileKey = fileKey;
        this.tags = tags;
    }
}
