package com.boulec.kayu.repositories;

import com.boulec.kayu.models.NutritionScore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionScoreRepository extends JpaRepository<NutritionScore, Long> {

    @Query("SELECT classe,color FROM nutrition_score WHERE lower_bound <= ?1 ")
    List<List<String>> getClasseAndColor(int value, Pageable pageable);
}
