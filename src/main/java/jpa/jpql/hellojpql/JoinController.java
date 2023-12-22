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
public class JoinController {

    private final EntityManager em;

    @PostMapping("join")
    @Transactional
    public String join() {

        Team team = new Team();
        team.setName("teamA");
        em.persist(team);


        Member member = new Member("hello1", 20);
        member.setTeam(team);
        em.persist(member);

        // 밑에 코드를 실행했을때 join 쿼리가 말고도 select쿼리가 하나 더나감.
        // Team을 select 하는 쿼리가 하나 나가는데 Member 의 Team 에 지연로딩 설정을 안해줘서 그럼
        // @ManyToOne(fetch = FetchType.LAZY)
//        String query = "select m from Member m inner join m.team t";
        String query = "select m from Member m left join m.team t on t.name = 'teamA'";
        List<Member> result = em.createQuery(query, Member.class)
                .getResultList();


        return "";
    }
}
