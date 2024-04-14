package com.example.oss.api.controllers;

import com.example.oss.api.models.Survey;
import com.example.oss.api.models.SurveyOption;
import com.example.oss.api.models.User;
import com.example.oss.api.models.Vote;
import com.example.oss.api.responses.models.crud.CreateResponse;
import com.example.oss.api.responses.models.crud.DeleteResponse;
import com.example.oss.api.responses.models.crud.UpdateResponse;
import com.example.oss.api.services.Applicant.ApplicantService;
import com.example.oss.api.services.Survey.SurveyService;
import com.example.oss.api.services.Vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.oss.api.responses.factory.CrudResponseEntityFactory.*;

@RestController("surveyController")
@RequestMapping("/surveys")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SurveyController {
    final private SurveyService surveyService;
    final private VoteService voteService;
    final private ApplicantService applicantService;

    @GetMapping({"", "/search"})
    @ResponseBody
    protected Page<Survey> index(@RequestParam(required = false) String searchText,
                                 @RequestParam(defaultValue = "0") int page) {
        return surveyService.findAll(searchText, page);
    }

    @GetMapping("/{id}")
    protected Survey show(Model model,
                          @PathVariable UUID id,
                          @AuthenticationPrincipal User user) {
        Optional<Survey> survey = surveyService.findById(id);
        if (survey.isEmpty())
            throw new NullPointerException("Голосування з таким id не було знайдено.");

        Vote vote = new Vote(user, survey.get());
        if (voteService.checkVote(vote)) {
            model.addAttribute("AlreadyVote", true);
        }

        return survey.get();
    }

    @PostMapping
    @ResponseBody
    protected ResponseEntity<?> store(@Valid @RequestBody Survey survey,
                                      @AuthenticationPrincipal User user) {
        return createResponse(
                surveyService.convertToDto(
                        surveyService.insert(survey, user)));
    }

    @PutMapping("/{id}")
    @ResponseBody
    protected ResponseEntity<?> update(@Valid @RequestBody Survey survey,
                                       @AuthenticationPrincipal User user) {
        return updateResponse(
                surveyService.convertToDto(
                        surveyService.update(survey, user)));
    }

    @DeleteMapping
    @ResponseBody
    protected ResponseEntity<?> destroy(@RequestParam(name = "surveyId") Survey survey) {
        surveyService.delete(survey);
        return deleteResponse();
    }

    @GetMapping("/my")
    @ResponseBody
    protected List<Survey> mySurveys(@AuthenticationPrincipal User user) {
        return surveyService.findByUser(user);
    }

    @GetMapping("/result")
    @ResponseBody
    protected List<?> surveyResult(@RequestParam(name = "surveyId") Survey survey) {
        List<SurveyOption> applicants = applicantService.getByVotingId(survey.getId());
        return List.of(survey, applicants);
    }
}
