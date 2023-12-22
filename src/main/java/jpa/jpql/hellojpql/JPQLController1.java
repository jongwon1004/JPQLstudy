package jpa.jpql.hellojpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jpa.jpql.hellojpql.entity.Address;
import jpa.jpql.hellojpql.entity.Member;
import jpa.jpql.hellojpql.entity.Order;
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

        // 위에 em.createQuery 해서 가져온 Member 리스트는 모두 영속성 컨텍스트에서 관리한다. List<Member> 안에 모든 멤버들은 관리된다
        // Member 리스트에서 한명을 데려와서 값을 바꿔도 update 쿼리 나감 -> 엔티티 프로젝션

        em.createQuery("select o.address from Order o", Address.class)
                .getResultList(); // 임베디드 타입 프로젝션

        List<MemberDto> result = em.createQuery("select new jpa.jpql.hellojpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        MemberDto memberDto = result.get(0);
        System.out.println("memberDto.getUsername() = " + memberDto.getUsername());
        System.out.println("memberDto.getAge() = " + memberDto.getAge());


//        MemberDto memberDto = result.get(0);
//        System.out.println("memberDto.getUsername() = " + memberDto.getUsername());
//        System.out.println("memberDto.getAge() = " + memberDto.getAge());


        return "";
    }

    @PostMapping("/jpql2")
    @Transactional
    public String jpql2() {

        Member member = new Member();
        member.setUsername("member1");
        member.setAge(20);

        em.persist(member);
        return "";
    }

}
