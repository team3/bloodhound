package net.bilberry.candidate.repository;

import net.bilberry.candidate.model.ProfileUrl;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileUrlRepository extends SolrCrudRepository<ProfileUrl, String> {
}
