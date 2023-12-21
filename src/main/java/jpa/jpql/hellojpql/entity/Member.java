package jpa.jpql.hellojpql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int age;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
