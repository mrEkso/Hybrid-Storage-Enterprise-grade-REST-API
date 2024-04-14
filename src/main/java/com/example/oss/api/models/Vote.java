package com.example.oss.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document(collection = "votes")
@Getter
@NoArgsConstructor
public class Vote {
    @Id
    private final UUID id = UUID.randomUUID();

    @NotNull
    @DBRef
    @Field("user_id")
    @Indexed
    private User user;

    @NotNull
    @DBRef
    @Field("survey_id")
    @Indexed
    private Survey survey;

    @NotNull
    @DBRef
    @Field("survey_option_id")
    @Indexed
    private SurveyOption surveyOption;

    public Vote(User user, Survey survey, SurveyOption surveyOption) {
        this.user = user;
        this.survey = survey;
        this.surveyOption = surveyOption;
    }

    public Vote(User user, Survey survey) {
        this.user = user;
        this.survey = survey;
    }
}
