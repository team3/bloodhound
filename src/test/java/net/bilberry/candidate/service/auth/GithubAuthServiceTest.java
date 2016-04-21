package net.bilberry.candidate.service.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/applicationContext.xml")
@WebAppConfiguration
public class GithubAuthServiceTest
{
    @Qualifier("githubAuthService")
    @Autowired GithubAuthService githubAuthService;


    @Test
    public void testHasAccessToken() throws Exception {

    }

    @Test
    public void testProvideAccessToken() throws Exception {

    }

    @Test
    public void testPrepareAuthUrl() throws Exception {
        String s = githubAuthService.prepareAuthUrl();

        System.out.println(s);
    }

    @Test
    public void testRequestAccessToken() throws Exception {
        Optional<String> s = githubAuthService.requestAccessToken("52732159af1102820595");

        System.out.println(s);
    }
}