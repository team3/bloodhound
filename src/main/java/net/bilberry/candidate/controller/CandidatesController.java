package net.bilberry.candidate.controller;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.model.ProfileUrl;
import net.bilberry.candidate.repository.CandidateSearchRepository;
import net.bilberry.candidate.repository.ProfileUrlRepository;
import net.bilberry.candidate.service.CandidateSearchService;
import net.bilberry.vacancy.model.Vacancy;
import net.bilberry.vacancy.repository.QueryBasedVacanciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/candidates")
public class CandidatesController {
    @Autowired
    private CandidateSearchService candidateSearchService;
    @Autowired
    private QueryBasedVacanciesRepository vacanciesRepository;
    @Autowired
    private CandidateSearchRepository candidateSearchRepository;
    @Autowired
    private ProfileUrlRepository profileUrlRepository;

    @RequestMapping(path = "/search", method = GET)
    public ModelAndView searchForm(Map<String, Object> model) {
        Candidate candidate = new Candidate.Builder()
                .experience(0)
                .build();
        model.put("candidateSearchForm", candidate);
        model.put("profiles_count", candidateSearchRepository.count());
        model.put("vacancies_count", vacanciesRepository.count());

        return new ModelAndView("search/index", model);
    }

    @RequestMapping(path = "/search", method = POST)
    public ModelAndView search(@RequestParam Map<String, String> allParams,
                               @ModelAttribute("candidateSearchForm") Candidate candidate,
                               /*@SolrQueryMapper(root = Candidate.class) SolrModel solrModel,*/
                               Map<String, Object> model) {

        return new ModelAndView("/candidates/list", "candidates",
                candidateSearchService.findBySkillsAndLocation(candidate.getSkills(), candidate.getLocation()));
    }

    @RequestMapping(path = "/e-search", method = POST)
    public
    @ResponseBody
    List<Candidate> searchLinkedIn(@RequestParam Map<String, String> allParams,
                                   @ModelAttribute("candidateSearchForm") Candidate candidate,
                                   Map<String, Object> model) {

        return candidateSearchService.withLinkedInSearchEngine().search();
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        candidateSearchService.add("Andrii_" + new Random().nextGaussian(), "Parkhomenko");

        return new ModelAndView("search/index", "count", candidateSearchRepository.count());
    }

    @RequestMapping("/view")
    public ModelAndView view() {

        return new ModelAndView("candidates/view");
    }

    @RequestMapping("/list")
    public ModelAndView list() {

        List<Candidate> candidateList = new ArrayList();
        for (Candidate c : candidateSearchRepository.findAll()) candidateList.add(c);
        return new ModelAndView("candidates/list", "candidates", candidateList);
    }

    @RequestMapping("/show/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        Candidate candidate = candidateSearchRepository.findOne(id);

        List<Vacancy> projectsList =
                vacanciesRepository.findByCandidateMatching(candidate.getSkills())
                        .stream()
                        .collect(toList());

        model.addAttribute("candidate", candidate);
        model.addAttribute("vacancies", projectsList);

        return "candidates/show";
    }

    @RequestMapping(value = "/skills", method = GET)
    public
    @ResponseBody
    Set<String> skillsInJson(@RequestParam("term") String term) {

        return candidateSearchService.findSkillsStartsWith(term);
    }

    // ---------------------  API CALLS  -------------------------------------------------------------------------------

    @RequestMapping(value = "/api/link-profile", method = GET)
    public void linkProfileUrl(@RequestParam Map<String, String> params) {
        profileUrlRepository.save(
                new ProfileUrl(
                        params.get("profileId"),
                        params.get("profileUrl"),
                        ProfileUrl.Type.valueOf(params.get("profileUrlType"))
                ));
    }
}
