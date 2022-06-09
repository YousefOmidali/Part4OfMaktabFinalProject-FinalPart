package com.maktabProject.Part4MaktabFinalProject.Final.part.services;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Suggestion;
import com.maktabProject.Part4MaktabFinalProject.Final.part.repository.SuggestionRepository;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SuggestionService implements BaseService<Suggestion, Long> {
    private final SuggestionRepository suggestionRepository;

    public SuggestionService(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @Override
    @Transactional
    public Suggestion saveOrUpdate(Suggestion suggestion) {
        return suggestionRepository.save(suggestion);
    }

    @Override
    @Transactional
    public List<Suggestion> findAll() {
        return suggestionRepository.findAll();
    }

    @Override
    @Transactional
    public Suggestion findById(Long id) {
        return suggestionRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        suggestionRepository.deleteById(id);
    }

    @Transactional
    public List<Suggestion> suggestionsOfAnOrder(Long orderId) {
        return suggestionRepository.findSuggestionsByOrder_IdOrderBySuggestedPriceDesc(orderId);
    }
}
