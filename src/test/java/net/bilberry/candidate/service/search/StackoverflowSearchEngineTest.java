package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.model.ProfileUrl;
import net.bilberry.candidate.service.search.algorithm.StackoverflowByLoginAndLocationSearchAlgorithm;
import net.bilberry.index.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/applicationContext.xml")
@WebAppConfiguration
public class StackoverflowSearchEngineTest {

    @Autowired StackoverflowSearchEngine searchEngine;

    @Test
    public void testSearch() throws Exception {

    }

    @Test
    public void testFindMatchingProfiles() throws Exception {
        Candidate c = new Candidate.Builder().firstName("Andrey").secondName("Parkhomenko").location(Location.KIEV.getLocation()).build();
        List<Candidate> matchingProfiles = searchEngine.findMatchingProfiles(new StackoverflowByLoginAndLocationSearchAlgorithm().on(c));

        System.out.println(matchingProfiles);
    }

    @Test
    public void testFindByLogin() throws Exception {

    }
}