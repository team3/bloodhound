package net.bilberry.candidate.repository;

import com.google.code.geocoder.model.LatLng;
import net.bilberry.candidate.model.Candidate;
import net.bilberry.index.model.Location;
import net.bilberry.index.service.GeoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/applicationContext.xml")
@WebAppConfiguration
public class CandidateSearchRepositoryTest {
    @Autowired
    CandidateSearchRepository repository;

    @Test
    public void test_add() {

        Candidate candidate = new Candidate.Builder()
                .location(GeoService.findLocationFor("Kiev").orElse(null))
                .firstName("Semen")
                .secondName("Petrov")
                .experience(10)
                .id(String.valueOf(System.currentTimeMillis()))
                .skills(new HashSet<>(Arrays.asList("selenium", "tdd")))
                .build();

        repository.save(candidate);
    }

    @Test
    public void test_FindBySkills() {
        Candidate c = createTestCandidate();
        repository.save(c);

        Iterable<Candidate> candidates = repository.findBySkills("java");

        long count = StreamSupport.stream(candidates.spliterator(), false)
                .filter(candidate -> candidate.getCandidateId().equals(c.getCandidateId()))
                .count();

        System.out.println("count = " + count);
        assertTrue(count >= 1);
    }

    @Test
    public void test_FindByFirstName() {
        Candidate c = createTestCandidate();
        repository.save(c);

        Iterable<Candidate> candidates = repository.findByFirstName(c.getFirstName());

        long count = StreamSupport.stream(candidates.spliterator(), false)
                .filter(candidate -> candidate.getCandidateId().equals(c.getCandidateId()))
                .count();


        System.out.println("count = " + count);
        assertTrue(count == 1);
    }

    @Test
    public void test_DeleteAll() {
        repository.deleteAll();
    }

    @Test
    public void test_findBySkillsStartingWith() {
        Iterable<Candidate> candidates = repository.findBySkillsContains("java");

        System.out.println(candidates);
    }

    @Test
    public void test_findBySkillsInNear() {
        Iterable<Candidate> candidates =
                repository.findBySkillsContainingWithin("java",
                        Location.KIEV.getLocation(), new Distance(5, Metrics.KILOMETERS));

        System.out.println(candidates);
    }

    private Candidate createTestCandidate() {
        return new Candidate.Builder()
                .id(String.valueOf(System.currentTimeMillis()))
                .country("UA")
                .firstName("Andrii")
                .secondName("Parkhomenko")
                .skills(new HashSet<>(Arrays.asList("java", "c++")))
                .build();
    }
}