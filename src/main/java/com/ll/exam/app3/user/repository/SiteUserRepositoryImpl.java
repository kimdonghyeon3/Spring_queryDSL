package com.ll.exam.app3.user.repository;

//import com.ll.exam.app3.user.entity.QSiteUser;
import com.ll.exam.app3.interestKeyword.entity.InterestKeyword;
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

        import static com.ll.exam.app3.interestKeyword.entity.QInterestKeyword.interestKeyword;
import static com.ll.exam.app3.user.entity.QSiteUser.siteUser;
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
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }


    @Override
    public Long getQslCount() {

        return jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .fetchOne();
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<SiteUser> getQslUserOrderByIdAsc() {

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();
    }

    @Override
    public List<SiteUser> serchQsl(String str) {

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.username.like("%"+str+"%").or(siteUser.email.like("%"+str+"%")))
                .fetch();
    }

    @Override
    public Page<SiteUser> searchQsl(String str, Pageable pageable) {

        List<SiteUser> results = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.username.like("%"+str+"%").or(siteUser.email.like("%"+str+"%")))
                .orderBy(siteUserSort(pageable))
                .offset(pageable.getOffset())   //????????? ?????? ????????? ????????? limit {1}, ?
                .limit(pageable.getPageSize())  //??????????????? ??? ?????? ????????? ????????? limit ?, {1]
                .fetch();

        Long count = jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .where(siteUser.username.like("%"+str+"%").or(siteUser.email.like("%"+str+"%")))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public List<SiteUser> getSiteUserByInterestKeyword(String str) {

        return jpaQueryFactory
                .selectFrom(siteUser)
                .innerJoin(siteUser.interestKeywords, interestKeyword)
                .where(
                        interestKeyword.content.eq(str)
                )
                .fetch();
    }

    @Override
    public List<InterestKeyword> getFollowingsInterests(SiteUser user) {
        return jpaQueryFactory.selectFrom(interestKeyword)
                .innerJoin(interestKeyword.user, siteUser)
                .where(interestKeyword.user.in(user.getFollowings()))
                .fetch();
    }


    private OrderSpecifier<?> siteUserSort(Pageable page) {
        //??????????????? ????????? Pageable ????????? ???????????? null ??? ??????
        if (!page.getSort().isEmpty()) {
            //???????????? ?????? ????????? for ???????????? ?????? ????????????
            for (Sort.Order order : page.getSort()) {
                // ??????????????? ????????? DESC or ASC ??? ????????????.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // ??????????????? ????????? ?????? ????????? ????????? ????????? ?????? ???????????? ???????????? ??????.
                switch (order.getProperty()){
                    case "id":
                        return new OrderSpecifier(direction, siteUser.id);
                    case "username":
                        return new OrderSpecifier(direction, siteUser.username);
                    case "email":
                        return new OrderSpecifier(direction, siteUser.email);
                }
            }
        }
        return null;
    }



}
