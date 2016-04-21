package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.service.auth.StackoverflowAuthService;
import net.bilberry.candidate.service.search.algorithm.CandidateSearchAlgorithm;
import net.bilberry.index.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StackoverflowSearchEngine implements SearchEngine {
    private static final String USERS_SEARCH_ENDPOINT = "http://api.stackexchange.com/2.2/users?";

    @Autowired
    StackoverflowAuthService authService;

    /**
     * "badge_counts": {
     * "bronze": 3,
     * "silver": 1,
     * "gold": 0
     * },
     * "account_id": 954964,
     * "is_employee": false,
     * "last_modified_date": 1332357718,
     * "last_access_date": 1341223509,
     * "reputation_change_year": 5,
     * "reputation_change_quarter": 0,
     * "reputation_change_month": 0,
     * "reputation_change_week": 0,
     * "reputation_change_day": 0,
     * "reputation": 18,
     * "creation_date": 1317821400,
     * "user_type": "registered",
     * "user_id": 980445,
     * "location": "Sumi, Ukraine",
     * "website_url": "",
     * "link": "http://stackoverflow.com/users/980445/a-parkhomenko",
     * "profile_image": "https://www.gravatar.com/avatar/2e856cdc3305c09f47dfcb78e6273061?s=128&d=identicon&r=PG",
     * "display_name": "a.parkhomenko"
     * },
     * @param searchParametersContainer
     * @return
     */
    @Override
    public List<Candidate> search(final Candidate searchParametersContainer) {
        return null;
    }

    /**
     * order=desc&sort=reputation&inname=Parkhomenko&site=stackoverflow
     * @param searchAlgorithm
     * @return
     */
    @Override
    public List<Candidate> findMatchingProfiles(final CandidateSearchAlgorithm searchAlgorithm) {
        JSONResource response;
        try {
            System.out.println(USERS_SEARCH_ENDPOINT + searchAlgorithm.query());
            response = request(USERS_SEARCH_ENDPOINT + searchAlgorithm.query());
            JSONArray items = response.toObject().optJSONArray("items");

            if (items != null) {
                List<Candidate> profiles = new ArrayList<>(items.length());
                for (int i = 0; i < items.length(); i++) {
                    profiles.add(mapFromJson(items.getJSONObject(i)));
                }
                return profiles;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public Candidate mapFromJson(final JSONObject jsonObject) {
        final String displayName = jsonObject.optString("display_name");
        final String avatarUrl = jsonObject.optString("profile_image");
        final String location = jsonObject.optString("location");

        return new Candidate.Builder()
                .firstName(displayName)
                .location(GeoService.findLocationFor(location).get())
                .avatar(avatarUrl)
                .build();
    }

    private JSONResource request(final String url) throws IOException {
        Resty resty = new Resty().identifyAsMozilla();
        resty.withHeader("Authorization", "Bearer " + authService.provideAccessToken());
        resty.withHeader("Accept-Encoding", "GZIP");
        resty.withHeader("Content-Encoding", "GZIP");

        //System.out.println(resty.json(url));
        return resty.json(url);
    }
}
