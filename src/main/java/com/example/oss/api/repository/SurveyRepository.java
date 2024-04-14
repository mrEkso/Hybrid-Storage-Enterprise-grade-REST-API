package com.example.oss.api.repository;

import com.example.oss.api.models.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface SurveyRepository extends MongoRepository<Survey, UUID> {

    List<Survey> findByUserId(UUID user_id);

    Page<Survey> findByTitle(Pageable pageable, String title);
}
