package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.model.ProfileUrl;
import net.bilberry.candidate.service.search.algorithm.CandidateSearchAlgorithm;

import java.util.List;

public interface SearchEngine {
    List<Candidate> search(Candidate searchParametersContainer);
    List<Candidate> findMatchingProfiles(final CandidateSearchAlgorithm searchAlgorithm);
}
