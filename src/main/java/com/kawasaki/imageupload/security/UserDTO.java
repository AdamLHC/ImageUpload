package com.kawasaki.imageupload.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserDTO {
    private final String userName;
    private final String passWord;
    private final String nickName;
}
