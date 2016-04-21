package net.bilberry.candidate.service.search.algorithm;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.index.service.GeoService;

import java.util.Optional;

public class StackoverflowByLoginAndLocationSearchAlgorithm implements CandidateSearchAlgorithm {
    private Candidate candidate;

    @Override
    public CandidateSearchAlgorithm on(final Candidate c) {
        this.candidate = c;

        return this;
    }

    @Override
    public String query() {
        Optional<String> country = GeoService.findCountryFor(candidate.getLocation());
        return String.format("order=desc&sort=reputation&inname=%s&site=stackoverflow&location=%s",
                candidate.getSecondName(), country.orElse(""));
    }
}
