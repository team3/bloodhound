package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.model.ProfileUrl;
import net.bilberry.candidate.service.auth.GithubAuthService;
import net.bilberry.candidate.service.search.algorithm.CandidateSearchAlgorithm;
import net.bilberry.index.service.GeoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import java.io.IOException;
import java.util.*;

@Service
public class GithubSearchEngine implements SearchEngine {
    @Qualifier("githubAuthService")
    @Autowired
    GithubAuthService authService;

    private static final String USERS_SEARCH_ENDPOINT = "https://api.github.com/search/users?";
    private static final String USER_ENDPOINT = "https://api.github.com/users/";


    @Override
    public List<Candidate> search(final Candidate searchParametersContainer) {

        try {
            System.out.println(USERS_SEARCH_ENDPOINT + "q=Andrey+language:javascript");

            JSONResource jsonResource = doRequest(USERS_SEARCH_ENDPOINT + "q=Andrey+language:java");
            JSONObject jsonObject = jsonResource.toObject();

            JSONArray items = jsonObject.optJSONArray("items");
            if (items != null) {
                List<Candidate> candidatesList = new ArrayList<>(items.length());
                for (int i = 0; i < items.length(); i++) {
                    Candidate candidate = mapFromJson(
                            readUser(
                                    ((JSONObject) items.get(i)).getString("login")));
                    candidatesList.add(candidate);
                }

                return candidatesList;
            }
        } catch (IOException | JSONException e) {

            System.out.println(e);
        }

        return Collections.emptyList();
    }

    /**
     * Searching users on GitHub
     ** @return
     */
    public List<Candidate> findMatchingProfiles(final CandidateSearchAlgorithm algorithm) {
        JSONResource response;
        try {
            final String url = USERS_SEARCH_ENDPOINT + "q=" + algorithm.query();
            System.out.println(url);
            response = doRequest(url);
            JSONArray items = response.toObject().optJSONArray("items");

            if (items != null) {
                List<Candidate> profiles = new ArrayList<>(items.length());
                for (int i = 0; i < items.length(); i++) {
                    String login = items.getJSONObject(i).optString("login");
                    if (login != null) profiles.add(findByLogin(login).get());
                }

                return profiles;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public Optional<Candidate> findByLogin(final String login) {
        try {
            return Optional.of(mapFromJson(readUser(login)));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * "login": "team3",
     * "id": 276688,
     * "avatar_url": "https://avatars.githubusercontent.com/u/276688?v=3",
     * "gravatar_id": "",
     * "url": "https://api.github.com/users/team3",
     * "html_url": "https://github.com/team3",
     * "followers_url": "https://api.github.com/users/team3/followers",
     * "following_url": "https://api.github.com/users/team3/following{/other_user}",
     * "gists_url": "https://api.github.com/users/team3/gists{/gist_id}",
     * "starred_url": "https://api.github.com/users/team3/starred{/owner}{/repo}",
     * "subscriptions_url": "https://api.github.com/users/team3/subscriptions",
     * "organizations_url": "https://api.github.com/users/team3/orgs",
     * "repos_url": "https://api.github.com/users/team3/repos",
     * "events_url": "https://api.github.com/users/team3/events{/privacy}",
     * "received_events_url": "https://api.github.com/users/team3/received_events",
     * "type": "User",
     * "site_admin": false,
     * "score": 12.767572
     * @param login
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private JSONObject readUser(final String login) throws IOException, JSONException {
        return doRequest(USER_ENDPOINT + login).toObject();
    }

    private Candidate mapFromJson(JSONObject jsonObject) {

        final String[] fullName = StringUtils.split(jsonObject.optString("name"), " ");
        final String location = jsonObject.optString("location");
        final String avatarUrl = jsonObject.optString("avatar_url");
        final String profileId = jsonObject.optString("id");
        final String profileUrl = jsonObject.optString("html_url");

        ProfileUrl url = new ProfileUrl(profileId, profileUrl, ProfileUrl.Type.GITHUB);

        return new Candidate.Builder()
                .id(profileId)
                .location(GeoService.findLocationFor(location).get())
                .firstName(fullName[0])
                .secondName(fullName.length == 2? fullName[1] : "")
                .avatar(avatarUrl)
                .country(location)
                .build().addProfileUrl(url);
    }

    private JSONResource doRequest(final String url) throws IOException {
        Resty r = new Resty();
        r.withHeader("Accept", "application/vnd.github.v3+json");
        r.withHeader("Authorization", "token " + authService.provideAccessToken());

        return r.json(url);
    }


}
