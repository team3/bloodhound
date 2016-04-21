package net.bilberry.candidate.controller.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.service.search.StackoverflowSearchEngine;
import net.bilberry.candidate.service.search.algorithm.StackoverflowByLoginAndLocationSearchAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/search/stackoverflow")
public class StackoverflowSearchController {
    @Autowired
    StackoverflowSearchEngine stackoverflowSearchEngine;

    @RequestMapping("/suggestions")
    public @ResponseBody
    List<Candidate> findSuggestions(@RequestParam Map<String, String> allParams) {
        Candidate c = new Candidate.Builder()
                .firstName(allParams.get("firstName"))
                .secondName(allParams.get("secondName"))
                .location(allParams.get("coordinates"))
                .build();

        return stackoverflowSearchEngine.findMatchingProfiles(new StackoverflowByLoginAndLocationSearchAlgorithm().on(c));
    }
}
