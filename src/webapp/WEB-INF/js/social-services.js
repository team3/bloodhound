(function(bilberry, $) {
    bilberry.openFacebookConnector = function() {
        window.open('/search-engine-auth/facebook', "_blank", "width=500, height=400");
    };

    bilberry.openLinkedinConnector = function () {
        window.open('/search-engine-auth/linkedin', "_blank", "width=500, height=400");
    };

    bilberry.openGithubConnector = function () {
        window.open('/search-engine-auth/github', "_blank", "width=500, height=400");
    };

    bilberry.openStackoverflowConnector = function () {
        window.open('/search-engine-auth/stackoverflow', "_blank", "width=500, height=400");
    };

    bilberry.showGithubSuggestions = function (container, firstName, secondName, coordinates) {
        showSuggestions('/api/search/github/suggestions', container, firstName, secondName, coordinates)
    };

    bilberry.showStackoverflowSuggestions = function (container, firstName, secondName, coordinates) {
        showSuggestions('/api/search/stackoverflow/suggestions', container, firstName, secondName, coordinates)
    };

    var showSuggestions = function (url, container, firstName, secondName, coordinates) {
        jQuery.ajax(
            {
                dataType: "json",
                url: url,
                data: {firstName: firstName, secondName: 'Parkhomenko', coordinates: '50.450091, 30.523415'},
                success: function (data) {
                    var items = [];
                    $.each(data, function (key, value) {
                        items.push("<div id='" + key + "' style='width:100%; font-size: 12px; border-bottom: 1px solid #e2f3ff'>" +
                            "<input type='checkbox' /><img src='" + value.avatarUrl + "' width=32 height=32 />"
                            + value.firstName + ' ' + value.secondName + ' [' + value.stringLocation + ']' + "</div>");
                    });

                    $(container).html(items.join("") +
                        "<div class='link-button btn-submit' onclick='bilberry.linkProfileUrl(value.candidateId, value.profileUrls[0].url)'>link</div>" +
                        "<div class='link-button btn-reset' onclick='bilberry.closeSuggestionList(" + container + ")'>close</div>"
                    );

                    $(container).css('display', 'block');
                },
                error: function (e) {
                    console.log(e);
                }
            }
        );
    };

    bilberry.closeSuggestionList = function(container) { $(container).close(); };
    bilberry.linkProfileUrl = function (profileId, profileUrl) {
        jQuery.ajax({
            url: '/api/link-profile',
            dataType: "json",
            data: {profileId: profileId, profileUrl: profileUrl, profileUrlType: 'GITHUB'},
            success: function(data) { console.log("linked successfully");}
        });
    };

})(window.bilberry = window.bilberry || {}, jQuery);