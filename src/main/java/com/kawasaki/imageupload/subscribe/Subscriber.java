package com.kawasaki.imageupload.subscribe;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @ElementCollection
    @CollectionTable(name = "subscriber_subscribed_tags",joinColumns = @JoinColumn(name = "subscriber_id"))
    @Column(name = "tag")
    private Set<String> subscribedTags;

    public void addSubscribedTag(String tag) {
        this.subscribedTags.add(tag);
    }

    public void removeSubscribedTag(String tag) {
        this.subscribedTags.remove(tag);
    }

    public Subscriber(String username, Set<String> tags){
        this.username = username;
        this.subscribedTags = tags;
    }

    public Subscriber(String username){
        this.username = username;
    }
}
