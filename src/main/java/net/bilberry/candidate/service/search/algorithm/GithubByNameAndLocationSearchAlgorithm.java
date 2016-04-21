package net.bilberry.candidate.service.search.algorithm;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.index.service.GeoService;

import java.util.Optional;

/**
 * Finding github users by second name and location
 */
public class GithubByNameAndLocationSearchAlgorithm implements CandidateSearchAlgorithm {
    private Candidate candidate;

    @Override
    public CandidateSearchAlgorithm on(final Candidate c) {
        this.candidate = c;
        return this;
    }

    @Override
    public String query() {
        Optional<String> country = GeoService.findCountryFor(candidate.getLocation());
        return String.format("%s+in:fullname+type:user+location:%s",
                candidate.getSecondName(), country.orElse(candidate.getCountry()));
    }
}
