package jpa.jpql.hellojpql;

import jakarta.persistence.EntityManager;
import jpa.jpql.hellojpql.entity.Member;
import jpa.jpql.hellojpql.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FetchJoinController {

    private final EntityManager em;

    @PostMapping("/joinfetch")
    @Transactional
    public String joinfetch() {

        Team team1 = new Team();
        team1.setName("teamA");

        Team team2 = new Team();
        team2.setName("teamB");

        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member();
        member1.setAge(10);
        member1.setUsername("회원1");
        member1.setTeam(team1);

        Member member2 = new Member();
        member2.setAge(20);
        member2.setUsername("회원2");
        member2.setTeam(team1);

        Member member3 = new Member();
        member3.setAge(30);
        member3.setUsername("회원3");
        member3.setTeam(team2);


        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        em.flush();
        em.clear();

        // DISTINCT 가 SQL 에서 중복 제거해주는것도 있는데, 애플리케이션에서 추가로 중복 제거 시도를 함.
        // 밑의 경우는 같은 식별자를 가진 Team 엔티티를 제거해줌
        // distinct 를 쓰지 않았을때는 teamA 의 회원이 두명이니까 teamA 이 두번 조회됐지만 , distinct 를 사용해서
        // 애플리케이션 단위에서 teamA 가 같은 엔티티니까 중복을 제거해줘서 총결과값이 2개 나옴 ( teamA, teamB )
        String query = "select distinct t from Team t join fetch t.members";
        List<Team> resultList = em.createQuery(query, Team.class)
                .getResultList();

        System.out.println(resultList.size());

        for (Team t : resultList) {

            System.out.println(t.getName());
            for (Member member : t.getMembers()) {
                System.out.println("    member.getUsername() = " + member.getUsername());
            }
        }


        return "";
    }
}
