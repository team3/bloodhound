package net.bilberry.vacancy.controller;

import net.bilberry.index.service.GeoService;
import net.bilberry.vacancy.model.Vacancy;
import net.bilberry.vacancy.repository.VacanciesRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vacancies")
public class VacanciesController {
    @Autowired
    VacanciesRepository repository;

    @RequestMapping("/list")
    public ModelAndView list() {

        List<Vacancy> all = new ArrayList<>();
        repository.findAll()
                .forEach(v -> { v.setAddress(GeoService.findAddressFor(v.getLocation()).orElse(StringUtils.EMPTY)); all.add(v);});

        return new ModelAndView("/vacancies/list", "vacancies", all);
    }

    @RequestMapping("/show/{id}")
    public ModelAndView show(@PathVariable("id") String id) {
        return new ModelAndView("/vacancies/show", "vacancy", repository.findOne(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(Map<String, Object> model) {
        model.put("vacancy", new Vacancy.Builder().id(String.valueOf(System.currentTimeMillis())).build());
        return new ModelAndView("/vacancies/add", model);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("vacancy") Vacancy vacancy) {
        repository.save(vacancy);
        return "redirect:/vacancies/list";
    }

    // TODO: request method should be DELETE
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id) {
        repository.delete(id);

        return "redirect:/vacancies/list";
    }
}
