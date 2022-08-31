package com.ll.exam.app3.user.repository;

import com.ll.exam.app3.QSiteUser;
import com.ll.exam.app3.user.entity.SiteUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;


@RequiredArgsConstructor
public class SiteUserRepositoryImpl implements SiteUserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id) {
        /*
        SELECT *
        FROM site_user
        WHERE id = 1
        */

        return jpaQueryFactory
                .select(QSiteUser.siteUser)
                .from(QSiteUser.siteUser)
                .where(QSiteUser.siteUser.id.eq(id))
                .fetchOne();
    }


    @Override
    public Long getQslCount() {

        return jpaQueryFactory
                .select(
                        QSiteUser.siteUser.count()
                        )
                .from(QSiteUser.siteUser)
                .fetchOne();
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {

        return jpaQueryFactory
                .select(
                        QSiteUser.siteUser
                )
                .from(QSiteUser.siteUser)
                .orderBy(QSiteUser.siteUser.id.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<SiteUser> getQslUserOrderByIdAsc() {

        return jpaQueryFactory
                .select(QSiteUser.siteUser)
                .from(QSiteUser.siteUser)
                .orderBy(QSiteUser.siteUser.id.asc())
                .fetch();
    }

    @Override
    public List<SiteUser> serchQsl(String str) {

        return jpaQueryFactory
                .select(QSiteUser.siteUser)
                .from(QSiteUser.siteUser)
                .where(QSiteUser.siteUser.username.like("%"+str+"%").or(QSiteUser.siteUser.email.like("%"+str+"%")))
                .fetch();
    }

    @Override
    public Page<SiteUser> searchQsl(String str, Pageable pageable) {

        List<SiteUser> results = jpaQueryFactory
                .select(QSiteUser.siteUser)
                .from(QSiteUser.siteUser)
                .where(QSiteUser.siteUser.username.like("%"+str+"%").or(QSiteUser.siteUser.email.like("%"+str+"%")))
                .orderBy(siteUserSort(pageable))
                .offset(pageable.getOffset())   //몇개를 건너 띄어야 하는지 limit {1}, ?
                .limit(pageable.getPageSize())  //한페이지에 몇 개가 보여야 하는지 limit ?, {1]
                .fetch();

        Long count = jpaQueryFactory
                .select(QSiteUser.siteUser.count())
                .from(QSiteUser.siteUser)
                .where(QSiteUser.siteUser.username.like("%"+str+"%").or(QSiteUser.siteUser.email.like("%"+str+"%")))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    private OrderSpecifier<?> siteUserSort(Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()){
                    case "id":
                        return new OrderSpecifier(direction, QSiteUser.siteUser.id);
                    case "username":
                        return new OrderSpecifier(direction, QSiteUser.siteUser.username);
                    case "email":
                        return new OrderSpecifier(direction, QSiteUser.siteUser.email);
                }
            }
        }
        return null;
    }

}