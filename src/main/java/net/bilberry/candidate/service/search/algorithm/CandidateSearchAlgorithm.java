package net.bilberry.candidate.service.search.algorithm;

import net.bilberry.candidate.model.Candidate;

public interface CandidateSearchAlgorithm {
    CandidateSearchAlgorithm on(Candidate c);
    String query();
}
