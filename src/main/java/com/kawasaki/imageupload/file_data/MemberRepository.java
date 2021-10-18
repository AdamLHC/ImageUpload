package com.kawasaki.imageupload.file_data;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Number> {
    public Optional<Member> findByUserNameIs(String UserName);
}
