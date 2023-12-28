package jpa.jpql.hellojpql.entity;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CaseController {

    private final EntityManager em;

    @PostMapping("/case")
    @Transactional
    public String join() {

        Member member = new Member();
        member.setAge(10);
        member.setUsername("hohoho");


        em.persist(member);

        String query = "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                        "from Member m";

        List<String> resultList = em.createQuery(query, String.class)
                .getResultList();

        for (String s : resultList) {
            System.out.println("s = " + s);
        }

        return "";
    }

    @PostMapping("/coalesce")
    @Transactional
    public String coalesce() {

        Member member = new Member();
        member.setAge(10);
        member.setUsername(null);


        em.persist(member);

        // 사용자 이름이 null 이면 이름 없는 회원 반환
        String query = "select coalesce(m.username, '이름 없는 회원') from Member m";
        List<String> resultList = em.createQuery(query, String.class)
                .getResultList();

        for (String s : resultList) {
            System.out.println("s = " + s);
        }


        return "";
    }

    @PostMapping("/nullif")
    @Transactional
    public String nullif() {

        Member member = new Member();
        member.setAge(10);
        member.setUsername("관리자");


        em.persist(member);

        // 두번째 파라미터인 '관리자' 와 값이 같을경우 null 반환
        String query = "select nullif(m.username, '관리자') from Member m";
        List<String> resultList = em.createQuery(query, String.class)
                .getResultList();

        for (String s : resultList) {
            System.out.println("s = " + s);
        }


        return "";
    }


}
