package net.bilberry.candidate.repository;

import net.bilberry.candidate.model.Candidate;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateSearchRepository extends SolrCrudRepository<Candidate, String> {
    Iterable<Candidate> findByFirstName(String firstName);

    Iterable<Candidate> findBySkills(String java);

    @Query(fields = {"skills"})
    Iterable<Candidate> findBySkillsContains(String term);

    @Query(value = "skills:*?0* AND !geofilt pt=<point.x>,<point.y> sfield=location d=<distance.value>")
    Iterable<Candidate> findBySkillsContainingWithin(String skills, Point point, Distance distance);
}
