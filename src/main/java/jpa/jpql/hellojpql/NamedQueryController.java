package jpa.jpql.hellojpql;


import jakarta.persistence.EntityManager;
import jpa.jpql.hellojpql.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NamedQueryController {

    private final EntityManager em;

    @PostMapping("/namedQuery")
    @Transactional
    public void namedQuery() {

        Member findMember = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "회원1")
                .getSingleResult();

        System.out.println("findMember = " + findMember);

    }
}
