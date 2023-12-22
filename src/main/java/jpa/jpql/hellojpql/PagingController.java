package jpa.jpql.hellojpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa.jpql.hellojpql.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PagingController {

    private final EntityManager em;


    @PostMapping("/paging")
    @Transactional
    public String paging() {

//        Member member1 = new Member("seungsu", 23);
//        em.persist(member1);
//
//        Member member2 = new Member("changwoo", 24);
//        em.persist(member2);

        em.flush();
        em.clear();

        List<Member> result = em.createQuery("select m from Member m order by m.age desc ", Member.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();

        for (Member member : result) {
            System.out.println("member = " + member);
        }

        return "";
    }
}
