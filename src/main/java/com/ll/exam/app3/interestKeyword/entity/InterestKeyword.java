package com.ll.exam.app3.interestKeyword.entity;

import com.ll.exam.app3.user.entity.SiteUser;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(InterestKeywordId.class)
public class InterestKeyword {
    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private SiteUser user;

    @Id
    @EqualsAndHashCode.Include
    private String content;
}