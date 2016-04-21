package net.bilberry.spring.model;

import org.springframework.data.solr.core.query.Query;

public class SolrModel {
    private Query query;

    public SolrModel query(final Query query) {

        this.query = query;

        return this;
    }

    public Query getQuery() {
        return query;
    }
}
