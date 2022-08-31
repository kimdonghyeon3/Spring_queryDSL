package com.ll.exam.app3.user.repository;

import com.ll.exam.app3.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long>, SiteUserRepositoryCustom{


    List<SiteUser> findByInterestKeywords_content(String str);
}
