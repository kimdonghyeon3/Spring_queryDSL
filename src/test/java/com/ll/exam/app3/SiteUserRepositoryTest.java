package com.ll.exam.app3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ActiveProfiles("test") // 테스트 모드 활성화
public class SiteUserRepositoryTest {

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Test
    @DisplayName("회원 생성")
    void t1() {
        SiteUser u3 = SiteUser.builder()
                .username("user3")
                .password("{noop}1234")
                .email("user3@test.com")
                .build();

        SiteUser u4 = SiteUser.builder()
                .username("user4")
                .password("{noop}1234")
                .email("user4@test.com")
                .build();

        siteUserRepository.saveAll(Arrays.asList(u3, u4));
    }

    @Test
    @DisplayName("1번 회원을 Qsl로 가져오기")
    void t2() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("2번 회원을 Qsl로 가져오기")
    void t3() {
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        assertThat(u2.getId()).isEqualTo(2L);
        assertThat(u2.getUsername()).isEqualTo("user2");
        assertThat(u2.getEmail()).isEqualTo("user2@test.com");
        assertThat(u2.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("모든 회원의 수")
    void t4() {
        Long count = siteUserRepository.getQslCount();

        assertThat(count).isGreaterThan(0L);
    }

    @Test
    @DisplayName("가장 오래된 회원")
    void t5() {
        SiteUser u1 = siteUserRepository.getQslUserOrderByIdAscOne();

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("가장 오래된 회원 순서")
    void t6() {
        List<SiteUser> siteUsers = siteUserRepository.getQslUserOrderByIdAsc();

        assertThat(siteUsers.get(0).getId()).isEqualTo(1L);
        assertThat(siteUsers.get(0).getUsername()).isEqualTo("user1");
        assertThat(siteUsers.get(0).getEmail()).isEqualTo("user1@test.com");
        assertThat(siteUsers.get(0).getPassword()).isEqualTo("{noop}1234");

        assertThat(siteUsers.get(1).getId()).isEqualTo(2L);
        assertThat(siteUsers.get(1).getUsername()).isEqualTo("user2");
        assertThat(siteUsers.get(1).getEmail()).isEqualTo("user2@test.com");
        assertThat(siteUsers.get(1).getPassword()).isEqualTo("{noop}1234");
    }


    @Test
    @DisplayName("유저이름, 이메일 like 검색")
    void t7() {
        List<SiteUser> siteUsers = siteUserRepository.serchQsl("user1");

        assertThat(siteUsers.get(0).getId()).isEqualTo(1L);
        assertThat(siteUsers.get(0).getUsername()).isEqualTo("user1");
        assertThat(siteUsers.get(0).getEmail()).isEqualTo("user1@test.com");
        assertThat(siteUsers.get(0).getPassword()).isEqualTo("{noop}1234");
        assertThat(siteUsers.size()).isEqualTo(1);
    }

    //페이지를 리턴하는 법
    @Test
    @DisplayName("검색, Page 리턴")
    void t8() {
        int itemsInAPage = 1; // 한 페이지에 보여줄 아이템 개수
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(1, itemsInAPage, Sort.by(sorts)); // 한 페이지에 10까지 가능
        Page<SiteUser> usersPage = siteUserRepository.searchQsl("user", pageable);

        List<SiteUser> users = usersPage.get().toList();

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(2L);
        assertThat(u.getUsername()).isEqualTo("user2");
        assertThat(u.getEmail()).isEqualTo("user2@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");

        assertThat(usersPage.getNumber()).isEqualTo(1);
        assertThat(usersPage.getTotalPages()).isEqualTo(2);
        assertThat(usersPage.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("검색, Page 리턴2")
    void t9() {
        int itemsInAPage = 1; // 한 페이지에 보여줄 아이템 개수
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(1, itemsInAPage, Sort.by(sorts)); // 한 페이지에 10까지 가능
        Page<SiteUser> usersPage = siteUserRepository.searchQsl("user", pageable);

        List<SiteUser> users = usersPage.get().toList();

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");

        assertThat(usersPage.getNumber()).isEqualTo(1);
        assertThat(usersPage.getTotalPages()).isEqualTo(2);
        assertThat(usersPage.getTotalElements()).isEqualTo(2);
    }
}
