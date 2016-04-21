package net.bilberry.index.controller;

import net.bilberry.candidate.repository.CandidateSearchRepository;
import net.bilberry.candidate.service.auth.GithubAuthService;
import net.bilberry.candidate.service.auth.LinkedInAuthService;
import net.bilberry.candidate.service.auth.StackoverflowAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Stack;

@Controller
public class IndexController {
    @Autowired
    CandidateSearchRepository repository;

    @Qualifier("linkedInAuthService")
    @Autowired LinkedInAuthService linkedInAuthService;

    @Qualifier("githubAuthService")
    @Autowired
    GithubAuthService githubAuthService;

    @Autowired
    StackoverflowAuthService stackoverflowAuthService;

    @RequestMapping(value = "/")
    public ModelAndView indexPage() {

        return new ModelAndView("index", "profiles_count", repository.count());
    }

    @RequestMapping("/authorized")
    public ModelAndView engineAuthorized() {
        return new ModelAndView("auth/authorized");
    }

    @RequestMapping("/admin/connections")
    public ModelAndView administrationPage(Model model) {
        model.addAttribute("isLinkedinConnected", linkedInAuthService.hasAccessToken());
        model.addAttribute("isFacebookConnected", false);
        model.addAttribute("isGithubConnected", githubAuthService.hasAccessToken());
        model.addAttribute("isStackoverflowConnected", stackoverflowAuthService.hasAccessToken());

        return new ModelAndView("administration/connections");
    }

}
