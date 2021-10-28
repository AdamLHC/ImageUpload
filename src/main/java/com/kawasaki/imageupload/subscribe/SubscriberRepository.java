package com.kawasaki.imageupload.subscribe;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubscriberRepository extends CrudRepository<Subscriber,Long> {
    public Optional<Subscriber> findByUsername(String username);
}
