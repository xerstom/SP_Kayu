package com.boulec.kayu.repositories;

import com.boulec.kayu.models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public abstract class RuleRepository implements JpaRepository <Rule, Long> {

    @Query("SELECT DISTINCT points FROM rule WHERE ?2 < 5")
    public abstract int getPoints(String name, float value);
}
