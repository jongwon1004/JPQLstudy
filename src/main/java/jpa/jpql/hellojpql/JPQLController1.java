package jpa.jpql.hellojpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jpa.jpql.hellojpql.entity.Member;
import jpa.jpql.hellojpql.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JPQLController1 {

    private final EntityManager em;

    @PostMapping("/jpql1")
    @Transactional
    public String jpql1() {
        Team team = new Team();
        team.setName("teamA");

        Member member = new Member("jongwon", 25);
        em.persist(member);

        // 반환타입이 명확할때 TypedQuery
        TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
        TypedQuery<String> query2 = em.createQuery("select m.username, m.age from Member m", String.class);

        // 반환타입이 명확하지 않을때 Query
        Query query3 = em.createQuery("select m.username, m.age from Member m");


        // 결과가 하나 이상일때 리스트 반환, 결과가 없으면 빈 리스트 반환
        List<Member> resultList = query1.getResultList();


        // 결과가 정확히 하나 , 단일 객체 반환.
        // 결과가 없으면 javax.persistence.NoResultException
        // 결과가 둘 이상이면 javax.persistence.NonUniqueResultException
        Member singleResult = query1.getSingleResult();// 결과값이 무조건 1 개일떄


        // 파라미터 바인딩 - 이름 기준, 위치 기준
        TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.username =:username", Member.class);
        query4.setParameter("username", "jongwon");
        Member singleResult1 = query4.getSingleResult();
        System.out.println("singleResult1 = " + singleResult1.getUsername());

        // 위치 기준 (사용하지 않는게 좋음)
        TypedQuery<Member> query5 = em.createQuery("select m from Member m where m.username =?1", Member.class);
        query5.setParameter(1, "jongwon");
        Member singleResult3 = query5.getSingleResult();
        System.out.println("singleResult1 = " + singleResult3.getUsername());

        // 메서드 체인
        Member singleResult2 = em.createQuery("select m from Member m where m.username =:username", Member.class)
                .setParameter("username", "jongwon")
                .getSingleResult();
        System.out.println("singleResult2 = " + singleResult2.getUsername());





        return "";
    }

}
