package com.ll.exam.app3;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

}
