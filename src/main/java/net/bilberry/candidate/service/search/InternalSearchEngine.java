package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.service.search.algorithm.CandidateSearchAlgorithm;

import java.util.List;

public class InternalSearchEngine implements SearchEngine {

    @Override
    public List<Candidate> search(final Candidate searchParametersContainer) {
        return null;
    }

    @Override
    public List<Candidate> findMatchingProfiles(final CandidateSearchAlgorithm searchAlgorithm) {
        return null;
    }
}
