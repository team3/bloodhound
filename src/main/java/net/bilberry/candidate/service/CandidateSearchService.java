package net.bilberry.candidate.service;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.repository.CandidateSearchRepository;
import net.bilberry.candidate.service.search.InternalSearchEngine;
import net.bilberry.candidate.service.search.LinkedInSearchEngine;
import net.bilberry.candidate.service.search.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.springframework.data.geo.Metrics.KILOMETERS;

@Service
public class CandidateSearchService {
    @Autowired
    CandidateSearchRepository repository;

    @Autowired @Qualifier("candidatesTemplate") SolrOperations candidateSolrOperations;
    private SearchEngine searchEngine = new InternalSearchEngine();

    public void add(final String firstName, final String secondName) {

        Candidate candidate = Candidate.builder().id(String.valueOf(repository.count() + 1))
                .firstName(firstName)
                .secondName(secondName)
                .country("Ukraine")
                .skills(new HashSet<>(Arrays.asList("java", "php")))
                .experience(12)
                .build();

        repository.save(candidate);
    }

    public Set<String> findSkillsStartsWith(final String term) {
        Iterable<Candidate> candidates = repository.findBySkillsContains(term);
        Set<String> skills = new HashSet<>();
        StreamSupport.stream(candidates.spliterator(), false)
                .flatMap(candidate -> candidate.getSkills().stream().filter(s -> s.contains(term)))
                .distinct()
                .forEach(skills::add);

        return skills;
    }

    public List<Candidate> findBySkillsAndLocation(Set<String> skills, Point location) {

        Query query = new SimpleQuery(new Criteria());
        FilterQuery filterQuery = new SimpleFilterQuery();

        skills.forEach(s -> filterQuery.addCriteria(new Criteria("skills").contains(s)));
        filterQuery.addCriteria(new Criteria("location").within(location, new Distance(100, KILOMETERS)));

        query.addFilterQuery(filterQuery);
        query.setRows(100);

        ScoredPage<Candidate> candidatesPage = candidateSolrOperations.queryForPage(query, Candidate.class);

        return candidatesPage.getContent();
    }

    public CandidateSearchService withLinkedInSearchEngine() {

        this.searchEngine = new LinkedInSearchEngine();

        return this;
    }

    public List<Candidate> search() {
        return this.searchEngine.search(new Candidate.Builder().build());
    }
}
