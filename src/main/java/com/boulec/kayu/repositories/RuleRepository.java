package com.boulec.kayu.repositories;

import com.boulec.kayu.models.Rule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    @Query("SELECT points FROM rule WHERE min_bound <= ?2 AND name = ?1")
    List<Integer> getRulePoint(String name, float value, Pageable pageable);

}
