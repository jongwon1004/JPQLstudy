package jpa.jpql.hellojpql;


import jakarta.persistence.EntityManager;
import jpa.jpql.hellojpql.entity.Member;
import jpa.jpql.hellojpql.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
public class TestController {

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    public void test1() {

        Team team = new Team();
        team.setName("teamA");

        Team team1 = new Team();
        team1.setName("teamB");

        em.persist(team);
        em.persist(team1);


        Member member1 = new Member();
        member1.setAge(10);
        member1.setUsername("userA");
        member1.setTeam(team);

        Member member2 = new Member();
        member2.setAge(20);
        member2.setUsername("userB");
        member2.setTeam(team1);

        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        Long result = em.createQuery("select count(m) from Member  m", Long.class)
                .getSingleResult();

        System.out.println("result = " + result);

        System.out.println("-------------------------------------------------------------------------");

        List<Team> resultList = em.createQuery("select t from Team t join fetch t.members m", Team.class)
                .getResultList();

        for (Team t : resultList) {
            System.out.println("team.getName() = " + t.getName());
            for (Member member : t.getMembers()) {
                System.out.println("member = " + member);
            }
        }


        System.out.println("-------------------------------------------------------------------------");

        Member findMember = em.createQuery("select m from Member m where m = :member", Member.class)
                .setParameter("member", member1)
                .getSingleResult();

        System.out.println("findMember = " + findMember);

    }
}
