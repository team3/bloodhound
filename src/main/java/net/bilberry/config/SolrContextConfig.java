package net.bilberry.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.SolrServerFactory;
import org.springframework.data.solr.server.support.MulticoreSolrServerFactory;

@Configuration
public class SolrContextConfig {

    @Value("${solr.host}")
    private String solrHost;

    @Bean
    public SolrServer solrServer() {
        return new HttpSolrServer(solrHost);
    }

//    @Bean
//    public SolrOperations solrTemplate() throws Exception {
//        return new SolrTemplate(solrServer());
//    }

    @Bean
    public SolrServerFactory solrServerFactory() {
        return new MulticoreSolrServerFactory(new HttpSolrServer(solrHost));
    }

    @Bean(name = "candidatesTemplate")
    public SolrOperations solrTemplate1() {
        SolrTemplate solrTemplate = new SolrTemplate(solrServerFactory());
        solrTemplate.setSolrCore("bilberry");
        return solrTemplate;
    }

    @Bean
    public SolrOperations solrTemplate2() {
        SolrTemplate solrTemplate = new SolrTemplate(solrServerFactory());
        solrTemplate.setSolrCore("bilberry-vacancies");
        return solrTemplate;
    }
}
