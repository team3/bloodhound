package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.service.auth.LinkedInAuthService;
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
public class LinkedInSearchEngineTest {
    @Autowired LinkedInSearchEngine searchEngine;

    @Test
    public void testSearch() throws Exception {
        List<Candidate> search = searchEngine.search(new Candidate.Builder().build());
    }

    @Test
    public void testLogin() throws Exception {
        searchEngine.login("getuniquename@gmail.com", "javaforever");
    }

    @Test
    public void testParseLoginAttributes() throws Exception {
        searchEngine.parseLoginAttributes();
    }
}