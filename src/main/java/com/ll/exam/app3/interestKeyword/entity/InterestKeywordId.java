package com.ll.exam.app3.interestKeyword.entity;

import com.ll.exam.app3.user.entity.SiteUser;
import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestKeywordId implements Serializable {
    private SiteUser user;
    private String content;
}
