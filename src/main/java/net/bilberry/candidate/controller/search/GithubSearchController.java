package net.bilberry.candidate.controller.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.model.ProfileUrl;
import net.bilberry.candidate.service.search.GithubSearchEngine;
import net.bilberry.candidate.service.search.algorithm.CandidateSearchAlgorithm;
import net.bilberry.candidate.service.search.algorithm.GithubByNameAndLocationSearchAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/api/search/github")
public class GithubSearchController {
    @Autowired
    GithubSearchEngine githubSearchEngine;

    @RequestMapping("/suggestions")
    public @ResponseBody List<Candidate> findSuggestions(@RequestParam Map<String, String> allParams) {
        Candidate c = new Candidate.Builder()
                .firstName(allParams.get("firstName"))
                .secondName(allParams.get("secondName"))
                .location(allParams.get("coordinates"))
                .build();

        CandidateSearchAlgorithm algorithm = new GithubByNameAndLocationSearchAlgorithm().on(c);
        return githubSearchEngine.findMatchingProfiles(algorithm);
    }

    @RequestMapping("/login/{login}")
    public @ResponseBody Candidate findByLogin(@PathVariable("login") String login) {
        return githubSearchEngine.findByLogin(login).orElse(null);
    }
}
