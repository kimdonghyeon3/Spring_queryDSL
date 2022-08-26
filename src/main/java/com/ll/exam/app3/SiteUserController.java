package com.ll.exam.app3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SiteUserController {

    private final SiteUserRepository userRepository;

    @RequestMapping("/user/{id}")
    public SiteUser user(@PathVariable Long id) {
        return userRepository.getQslUser(id);
    }

}
