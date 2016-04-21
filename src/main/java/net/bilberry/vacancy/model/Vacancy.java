package net.bilberry.vacancy.model;

import com.google.common.base.Joiner;
import net.bilberry.annotations.SolrSearchResultsRows;
import net.bilberry.index.model.LocationContainer;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Set;

@SolrDocument(solrCoreName = "bilberry-vacancies")
@SolrSearchResultsRows(500)
public class Vacancy extends LocationContainer {
    @Id
    @Field("id")
    private String id;

    @Field("vacancy_title")
    private String name;

    @Field("vacancy_skills")
    private Set<String> skills;

    @Field("vacancy_responsibilities")
    private Set<String> responsibilities;

    @Field("vacancy_experience")
    private Integer experience;

    private String skillsMatrix;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public Integer getExperience() {
        return experience;
    }

    public Set<String> getResponsibilities() {
        return responsibilities;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSkills(final Set<String> skills) {
        this.skills = skills;
    }

    public void setResponsibilities(final Set<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public void setExperience(final Integer experience) {
        this.experience = experience;
    }

    public String skillsMatrix() {
        return Joiner.on(",").join(skills);
    }

    public static class Builder {
        Vacancy vacancy = new Vacancy();

        public Builder id(String id) {
            vacancy.id = id;

            return this;
        }

        public Builder name(String name) {
            vacancy.name = name;

            return this;
        }

        public Builder location(String location) {
            vacancy.setStringLocation(location);

            return this;
        }

        public Builder location(Point location) {
            vacancy.setLocation(location);

            return this;
        }

        public Builder experience(Integer experience) {
            vacancy.experience = experience;

            return this;
        }

        public Builder skills(Set<String> skills) {
            vacancy.skills = skills;

            return this;
        }

        public Builder responsibilities(Set<String> responsibilities) {
            vacancy.responsibilities = responsibilities;

            return this;
        }

        public Vacancy build() {
            return vacancy;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vacancy{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", skills=").append(Joiner.on(",").join(skills));
        sb.append(", responsibilities=").append(responsibilities);
        sb.append(", location=").append(getStringLocation());
        sb.append(", experience=").append(experience);
        sb.append('}');
        return sb.toString();
    }
}
