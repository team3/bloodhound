package net.bilberry.vacancy.repository;

import net.bilberry.vacancy.model.Vacancy;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository(value = "QueryBasedVacanciesRepository")
public interface QueryBasedVacanciesRepository extends SolrCrudRepository<Vacancy, String>{
    @Query(value = "vacancy_skills:(*?0* )")
    List<Vacancy> findByCandidateMatching(Set<String> skills);
}
