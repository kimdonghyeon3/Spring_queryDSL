package com.ll.exam.app3.user.controller;

import com.ll.exam.app3.user.entity.SiteUser;
import com.ll.exam.app3.user.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SiteUserController {

    private final SiteUserRepository siteUserRepository;

    @RequestMapping("/user/{id}")
    public SiteUser user(@PathVariable Long id) {
        return siteUserRepository.getQslUser(id);
    }

}
