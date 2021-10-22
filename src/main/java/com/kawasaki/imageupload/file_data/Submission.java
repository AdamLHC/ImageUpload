package com.kawasaki.imageupload.file_data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

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

    @OneToMany
    private Collection<Tag> tags;

    public Submission(String title, String description, Member member, Date date, String fileKey) {
        this.title = title;
        this.descriptive = description;
        this.uploader = member;
        this.uploadDate = date;
        this.fileKey = fileKey;
    }
}
