package com.ll.exam.app3;

import org.junit.jupiter.api.Test;

public class SiteUserRepositoryTest {

    SiteUserRepository siteUserRepository;

    @Test
    public void create(){
        siteUserRepository.save(new SiteUser());

    }
}
