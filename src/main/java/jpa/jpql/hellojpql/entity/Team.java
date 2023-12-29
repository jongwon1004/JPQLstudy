package jpa.jpql.hellojpql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // teamA 랑 teamB 가 있다고 치면 지연로딩으로 설정되어있는데, teamA 를 사용할때 쿼리가 한방 나가고 , teamB 를 조회할때 쿼리 한방이 나가고 하면
    // 팀이 10개라고 하면 N + 1 문제가 발생한다. BatchSize 를 줘서 100개 이상의 팀이 있다고 하면 팀 100개를 다 긁어와서 N + 1 문제를 해결 할 수 있게 해준다.
    // 글로벌 설정으로 해줘도 됨. spring.jpa.properties.hibernate.default_batch_fetch_size=100 (패치조인 성능 최적화)
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
