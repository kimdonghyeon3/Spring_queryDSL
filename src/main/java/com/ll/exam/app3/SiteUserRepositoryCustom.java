package com.ll.exam.app3;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
    Long getQslCount();

    SiteUser getQslUserOrderByIdAscOne();

    List<SiteUser> getQslUserOrderByIdAsc();

    List<SiteUser> serchQsl(String str);

    Page<SiteUser> searchQsl(String user, Pageable pageable);
}
