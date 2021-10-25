package com.kawasaki.imageupload.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@Entity
@Table(name = "UserRoles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    public Role(String name){
        this.name = name;
    }

    @Override
    public String getAuthority(){
        return name;
    }
}
