package net.bilberry.candidate.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

public final class ProfileUrl {
    public enum Type {
        GITHUB,
        STACKOVERFLOW,
    }

    @Id
    @Field(value = "profile_id")
    private String id;

    @Field(value = "profile_url")
    private String url;

    @Field(value = "profile_url_type")
    private Type type;

    private ProfileUrl() {}

    public ProfileUrl(final String profileId, final String url, final Type type) {
        this.type = type;
        this.url = url;
    }

    public String url() {
        return url;
    }

    public Type type() { return type; }

    public String getUrl() {
        return url;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProfileUrl{");
        sb.append("url='").append(url).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
