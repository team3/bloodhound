package net.bilberry.index.controller;

import net.bilberry.candidate.service.auth.GithubAuthService;
import net.bilberry.candidate.service.auth.LinkedInAuthService;
import net.bilberry.candidate.service.auth.StackoverflowAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/search-engine-auth")
public class SearchEngineAuthController {
    @Qualifier("linkedInAuthService") @Autowired LinkedInAuthService linkedInAuthService;
    @Qualifier("githubAuthService") @Autowired GithubAuthService githubAuthService;
    @Qualifier("stackoverflowAuthService") @Autowired StackoverflowAuthService stackoverflowAuthService;

    @RequestMapping("/linkedin")
    public String requestLinkedinCode() {
        return "redirect:" + linkedInAuthService.prepareAuthUrl();
    }

    @RequestMapping("/github")
    public String requestGithubCode() {
        return "redirect:" + githubAuthService.prepareAuthUrl();
    }

    @RequestMapping("/stackoverflow")
    public String requestStackoverflowCode() {
        return "redirect:" + stackoverflowAuthService.prepareAuthUrl();
    }

    @RequestMapping("/linkedin/authorized")
    public String authorizedLinkedIn(@RequestParam("code") String code) {
        // redirect for requesting access token
        Optional<String> token = linkedInAuthService.requestAccessToken(code);
        String redirect = token.isPresent()? "/authorized": "/not-authorized";
        return "redirect:" + redirect;
    }

    @RequestMapping("/github/authorized")
    public String authorizedGithub(@RequestParam("code") String code) {
        // redirect for requesting access token
        Optional<String> token = githubAuthService.requestAccessToken(code);
        String redirect = token.isPresent()? "/authorized": "/not-authorized";
        return "redirect:" + redirect;
    }

    /*@RequestMapping("/facebook/authorized")
    public String authorizedFacebook(@RequestParam("code") String code) {
        // redirect for requesting access token
        Optional<String> token = linkedInAuthService.requestAccessToken(code);
        String redirect = token.isPresent()? "/authorized": "/not-authorized";
        return "redirect:" + redirect;
    }
*/
    @RequestMapping("/stackoverflow/authorized")
    public String authorizedStackOverflow(@RequestParam("code") String code) {
        // redirect for requesting access token
        Optional<String> token = stackoverflowAuthService.requestAccessToken(code);
        String redirect = token.isPresent()? "/authorized": "/not-authorized";
        return "redirect:" + redirect;
    }
}
