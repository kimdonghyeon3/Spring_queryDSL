package com.ll.exam.app3;

import com.ll.exam.app3.interestKeyword.entity.InterestKeyword;
import com.ll.exam.app3.user.entity.SiteUser;
import com.ll.exam.app3.user.repository.SiteUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        SiteUser u9 = SiteUser.builder()
                .username("user9")
                .password("{noop}1234")
                .email("user9@test.com")
                .build();

        SiteUser u10 = SiteUser.builder()
                .username("user10")
                .password("{noop}1234")
                .email("user10@test.com")
                .build();
        siteUserRepository.saveAll(Arrays.asList(u9, u10));
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

        assertThat(u.getId()).isEqualTo(8L);
        assertThat(u.getUsername()).isEqualTo("user8");
        assertThat(u.getEmail()).isEqualTo("user8@test.com");
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

        assertThat(u.getId()).isEqualTo(8L);
        assertThat(u.getUsername()).isEqualTo("user8");
        assertThat(u.getEmail()).isEqualTo("user8@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");

        assertThat(usersPage.getNumber()).isEqualTo(1);
        assertThat(usersPage.getTotalPages()).isEqualTo(2);
        assertThat(usersPage.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원에게 관심사를 등록할 수 있다.")
    void t10() {
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        u2.addInterestKeywordContent("축구");
        u2.addInterestKeywordContent("롤");
        u2.addInterestKeywordContent("헬스");
        u2.addInterestKeywordContent("헬스"); // 중복등록은 무시

        siteUserRepository.save(u2);
    }

    @Test
    @DisplayName("축구에 관심 있는 회원을 검색")
    void t11() {
        List<SiteUser> siteUsers = siteUserRepository.getSiteUserByInterestKeyword("축구");

        assertThat(siteUsers.size()).isEqualTo(1);

        SiteUser u = siteUsers.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("Spring data jpa 축구에 관심 있는 회원을 검색")
    void t12() {
        List<SiteUser> siteUsers = siteUserRepository.findByInterestKeywords_content("축구");

        assertThat(siteUsers.size()).isEqualTo(1);

        SiteUser u = siteUsers.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("u1은 u2의 팬이다.")
    void t13() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        u1.follow(u2);

        //siteUserRepository.save(u2);
    }

    @Test
    @DisplayName("u1은 u1을 팔로우 할 수 없다.")
    void t14() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);
        u1.follow(u1);

        assertThat(u1.getFollowers().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("u1과 u2는 맞팔이다.")
    @Rollback(false)
    void t15() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        u1.follow(u2);
        u2.follow(u1);

        siteUserRepository.save(u1);
        siteUserRepository.save(u2);
    }

    @Test
    @DisplayName("특정회원의 follower들과 following들을 모두 알 수 있어야 한다.")
    @Rollback(false)
    void t16() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);
        SiteUser u2 = siteUserRepository.getQslUser(2L);

        u1.follow(u2);

        // follower
        // u1의 구독자 : 0
        assertThat(u1.getFollowers().size()).isEqualTo(1);

        // follower
        // u2의 구독자 : 1
        assertThat(u2.getFollowers().size()).isEqualTo(1);

        // following
        // u1이 구독중인 회원 : 1
        assertThat(u1.getFollowings().size()).isEqualTo(1);

        // following
        // u2가 구독중인 회원 : 0
        assertThat(u2.getFollowings().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("u1 회원이 농구에 흥미를 잃었다.")
    @Rollback(false)
    void t17() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);

        Set<InterestKeyword> interestKeywords = u1.getInterestKeywords();

        Iterator<InterestKeyword> iterator = interestKeywords.iterator();

        if(iterator.hasNext()){
            InterestKeyword next = iterator.next();
             if( next.getContent().equals("농구")){
                 interestKeywords.remove(next);
             }
        }
    }

    @Test
    @DisplayName("u8 회원이 팔로워하는 사람의 취미 가져오기")
    @Rollback(false)
    void t18() {
        SiteUser u1 = siteUserRepository.getQslUser(1L);

        u1.removeInterestKeywordContent("농구");
    }



}
