package net.bilberry.swapper.controller;

import net.bilberry.swapper.Compiler;
import net.bilberry.swapper.model.SourceModel;
import net.bilberry.vacancy.model.Vacancy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/swapper")
@Controller
public class SwapperController {

    @RequestMapping(value = "/index", method = GET)
    public ModelAndView index(Map<String, Object> model) {
        Vacancy v = new Vacancy();

        v.setName("Name");

        model.put("value", v.getName());
        model.put("status", "?");
        model.put("source", "");
        model.put("swapperFormModel", new SourceModel());
        return new ModelAndView("swapper/index", model);
    }

    @RequestMapping(value = "/compile", method = POST)
    public String compile(Map<String, Object> model, @ModelAttribute(value = "swapperFormModel") SourceModel source) {

        Boolean execute = Compiler.execute("net.bilberry.vacancy.model.Vacancy", source.getSource());
        model.put("status", execute);
        Vacancy v = new Vacancy();
        v.setName("Name");
        model.put("value", v.getName());

        return "redirect:/swapper/index";
    }
}
