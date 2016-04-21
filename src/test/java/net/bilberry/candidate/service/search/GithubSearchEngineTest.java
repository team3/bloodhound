package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.model.ProfileUrl;
import net.bilberry.candidate.service.search.algorithm.GithubByNameAndLocationSearchAlgorithm;
import net.bilberry.index.model.Location;
import net.bilberry.index.service.GeoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/applicationContext.xml")
@WebAppConfiguration
public class GithubSearchEngineTest
{
    @Autowired GithubSearchEngine githubSearchEngine;

    @Test
    public void testSearch() throws Exception {
        List<Candidate> search = githubSearchEngine.search(new Candidate.Builder().build());
    }

    @Test
    public void testFindByLogin() throws Exception {

    }

    @Test
    public void testFindMatchingProfiles() throws Exception {

        Candidate c = new Candidate.Builder().firstName("Andrey").secondName("Popelo").location(Location.KIEV.getLocation()).build();
        List<Candidate> matchingProfiles =
                githubSearchEngine.findMatchingProfiles(new GithubByNameAndLocationSearchAlgorithm().on(c));

        System.out.println(matchingProfiles);
    }
}