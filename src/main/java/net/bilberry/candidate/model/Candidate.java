package net.bilberry.candidate.model;

import com.google.code.geocoder.model.LatLng;
import net.bilberry.annotations.SolrSearchResultsRows;
import net.bilberry.index.model.LocationContainer;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.*;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SolrDocument(solrCoreName = "bilberry")
@SolrSearchResultsRows(500)
public final class Candidate extends LocationContainer{
    private Candidate() {}

    List<ProfileUrl> profileUrls = new ArrayList<>(0);

    @Id
    @Field(value = "candidate_id")
    String candidateId;

    @Field(value = "first_name")
    String firstName;

    @Field(value = "second_name")
    String secondName;

    @Field(value = "country")
    String country;

    @Field(value = "skills")
    Set<String> skills;

    @Field(value = "experience")
    Integer experience = 0;

    @Field(value = "avatar_url")
    String avatarUrl;

    public Candidate addProfileUrl(final ProfileUrl url) {
        profileUrls.add(url);

        return this;
    }

    public List<ProfileUrl> profileUrls() {
        return profileUrls;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getCountry() {
        return country;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public int getExperience() {
        return experience == null? 0: experience;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public void setSkills(final Set<String> skills) {
        this.skills = skills;
    }

    public void setExperience(final Integer experience) {
        this.experience = experience;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Candidate candidate = new Candidate();

        public Builder id(String id) {
            candidate.candidateId = id;

            return this;
        }

        public Builder firstName(String firstName) {
            candidate.firstName = firstName;

            return this;
        }

        public Builder secondName(String secondName) {
            candidate.secondName = secondName;

            return this;
        }

        public Builder avatar(String avatarUrl) {
            candidate.avatarUrl = avatarUrl;

            return this;
        }

        public Builder country(String country) {
            candidate.country = country;

            return this;
        }

        public Builder location(String location) {
            candidate.setStringLocation(location);

            return this;
        }

        public Builder location(LatLng location) {
            candidate.setLocation(new Point(location.getLat().doubleValue(), location.getLng().doubleValue()));

            return this;
        }

        public Builder location(Point location) {
            candidate.setLocation(location);

            return this;
        }

        public Builder skills(Set<String> languages) {
            candidate.skills = languages;

            return this;
        }

        public Builder experience(Integer experience) {
            candidate.experience = experience;

            return this;
        }

        public Candidate build() {
            return candidate;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Candidate{");
        sb.append("candidateId='").append(candidateId).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", secondName='").append(secondName).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", skills=").append(skills);
        sb.append(", experience=").append(experience);
        sb.append(", location=").append(getStringLocation());
        sb.append('}');
        return sb.toString();
    }
}