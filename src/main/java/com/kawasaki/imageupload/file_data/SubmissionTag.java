package com.kawasaki.imageupload.file_data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@NoArgsConstructor
@Data
@Entity
public class SubmissionTag {
    @Id
    Long Id;

    @NotNull
    String Name;
}
