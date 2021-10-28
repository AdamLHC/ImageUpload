package com.kawasaki.imageupload.file_data;

import com.kawasaki.imageupload.file_data.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface SubmissionRepository extends JpaRepository<Submission, Number> {

    public Optional<Submission> findByFileKey(String filekey);

    @Query(value = "SELECT s FROM Submission s INNER JOIN s.tags t WHERE t IN :tags Order By s.uploadDate")
    public Iterable<Submission> findByByRelatedTags(Set<String> tags);
}
