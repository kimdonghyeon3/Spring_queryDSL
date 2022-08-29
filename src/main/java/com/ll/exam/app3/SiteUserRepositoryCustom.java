package com.ll.exam.app3;

public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
    Long getQslCount();

    SiteUser getQslUserOrderByIdAscOne();
}
