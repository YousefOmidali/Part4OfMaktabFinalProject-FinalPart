package com.maktabProject.Part4MaktabFinalProject.Final.part.repository;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findSuggestionsByOrder_IdOrderBySuggestedPriceDesc(Long id);
}
