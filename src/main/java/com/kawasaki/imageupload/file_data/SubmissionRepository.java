package com.kawasaki.imageupload.file_data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Number> {

    public Optional<Submission> findByFileKey(String filekey);

    @Query("SELECT s FROM Submission s JOIN s.tags t WHERE t.Name IN :tags Order By s.uploadDate")
    public Iterable<Submission> findByByRelatedTags(@Param("tags") Iterable<Tag> tags, Pageable pageable);
}
