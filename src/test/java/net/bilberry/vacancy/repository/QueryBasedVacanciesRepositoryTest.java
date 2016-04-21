package net.bilberry.vacancy.repository;

import net.bilberry.index.model.Location;
import net.bilberry.vacancy.model.Vacancy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/applicationContext.xml")
@WebAppConfiguration
public class QueryBasedVacanciesRepositoryTest {
    @Autowired QueryBasedVacanciesRepository repository;

    @Test
    public void test_Add() {
        Vacancy vacancy = new Vacancy.Builder()
                .id(String.valueOf(System.currentTimeMillis()))
                .location(Location.KIEV.getLocation())
                .name("Senior Java Developer")
                .responsibilities(new HashSet<>(asList("write code")))
                .skills(new HashSet<>(asList("java, php")))
                .build();

        Vacancy savedVacancy = repository.save(vacancy);

        System.out.println(savedVacancy);
    }

    @Test
    public void test_FindAll() {
        Iterable<Vacancy> all = repository.findAll();

        all.forEach(System.out::println);
    }

    @Test
    public void test_findByCandidateMatching() {

        List<Vacancy> vacancies = repository.findByCandidateMatching(new HashSet<>(asList("java")));
        System.out.println(vacancies);
    }

    @Test
    public void test_DeleteAll() {
        repository.deleteAll();
    }
}