package com.kawasaki.imageupload.file_data;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubmissionRepository extends CrudRepository<Submission, Number> {

    public Optional<Submission> findByFileKey(String filekey);
}
