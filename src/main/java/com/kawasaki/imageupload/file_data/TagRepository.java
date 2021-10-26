package com.kawasaki.imageupload.file_data;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag,Long> {
    Optional<Tag> findByName(String Name);
}
