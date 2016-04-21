package net.bilberry.candidate.service.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/applicationContext.xml")
@WebAppConfiguration
public class LinkedInAuthServiceTest {

    @Qualifier("linkedInAuthService")
    @Autowired LinkedInAuthService linkedInAuthService;

    @Test
    public void testAuth() throws Exception {
        linkedInAuthService.prepareAuthUrl();
    }
}