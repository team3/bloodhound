package net.bilberry.vacancy.repository;

import net.bilberry.vacancy.model.Vacancy;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacanciesRepository extends SolrCrudRepository<Vacancy, String> {
}
