package net.bilberry.candidate.service.search;

import net.bilberry.candidate.model.Candidate;
import net.bilberry.candidate.service.auth.LinkedInAuthService;
import net.bilberry.candidate.service.search.algorithm.CandidateSearchAlgorithm;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Collections;
import java.util.List;

import static us.monoid.web.Resty.data;
import static us.monoid.web.Resty.enc;
import static us.monoid.web.Resty.form;

@Service
public class LinkedInSearchEngine implements SearchEngine {
    public static final String ENDPOINT = "https://api.linkedin.com/v1";
    private static final String LOGIN_URL = "https://www.linkedin.com/uas/login-submit";

    @Qualifier("linkedInAuthService")
    @Autowired
    LinkedInAuthService authService;

    @Override
    public List<Candidate> search(final Candidate searchParametersContainer) {

        try {
            JSONResource response = request(ENDPOINT + "/people-search?keywords=java&sort=connections");

            JSONObject jsonObject = response.toObject();

            System.out.println(jsonObject);
            System.out.println(response);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public List<Candidate> findMatchingProfiles(final CandidateSearchAlgorithm searchAlgorithm) {

        return null;
    }

    private JSONResource request(final String url) throws IOException {
        Resty resty = new Resty();
        resty.withHeader("Content-Type", "application/json");
        resty.withHeader("x-li-format", "json");
        resty.withHeader("Authorization", "Bearer " + authService.provideAccessToken());

        return resty.json(url + "?format=json");
    }


    // ---------------------------------------------

    public void parseLoginAttributes() {

    }

    /**
     * isJsEnabled:true
     * source_app:
     * tryCount:
     * clickedSuggestion:false
     * session_key:getuniquename%40gmail.com
     * session_password:javaforever
     * signin:Sign%20In
     * session_redirect:
     * trk:hb_signin
     * loginCsrfParam:8c10e20a-102a-4a99-8850-bc4cfce75acd
     * fromEmail:
     * csrfToken:ajax%3A8293949044790772253
     * sourceAlias:0_7r5yezRXCiA_H0CRD8sf6DhOjTKUNps5xGTqeX8EEoi
     * client_ts:1445974398913
     * client_r:getuniquename%40gmail.com%3A246250671%3A895118265%3A738047038
     * client_output:32022778
     * client_n:246250671%3A895118265%3A738047038
     * client_v:1.0.1
     * @param login
     * @param password
     */
    public void login(final String login, final String password) {
        Resty r = new Resty().identifyAsMozilla();
        CookieHandler.setDefault(new CookieManager());
        r.withHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        r.withHeader("Cookie", "JSESSIONID=\"ajax:8972854920152343711\"; bcookie=\"v=2&12f86678-373b-4424-80b2-ef9454b2ad7a\"; bscookie=\"v=1&201510272048060f043ee1-66f9-48cc-8ed4-4e6991310715AQE-RXyJKs8F8VLm_zpJmEVidRXbVmuB\"; lidc=\"b=TB41:g=214:u=1:i=1445978886:t=1446065286:s=AQGw4FbMBg7TH23VyZl0xrldgHARGwG0\"; L1e=1e953b94; leo_auth_token=\"GST:Zv0Q2gSEQiwf8QfpkxT0nqXNx5P2vlTpZNTQduiZb4yUE03pnZfyUJ:1445978888:ab47c86236e4d9142ab2eca278c2ea0fd379a3d0\"; visit=\"v=1&G\"; lang=\"v=2&lang=ru-ru\"; _ga=GA1.2.1644925801.1445978889; _gat=1");

        try {
            TextResource text = r.text(LOGIN_URL,
                    form(
                            data("isJsEnabled", "false"),
                            data("session_key", login),
                            data("session_password", enc(password)),
                            data("loginCsrfParam", "12f86678-373b-4424-80b2-ef9454b2ad7a"),
                            data("csrfToken", enc("ajax:8972854920152343711")),
                            data("sourceAlias", "0_7r5yezRXCiA_H0CRD8sf6DhOjTKUNps5xGTqeX8EEoi")
                    ));

            System.out.println(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
