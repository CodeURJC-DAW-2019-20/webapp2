function windowResize() {
    if($(window).height() >= ($('body').height() + 92)) {
        $('footer').addClass('shortContent');
    }
    else {
        $('footer').removeClass('shortContent');
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

(function ($) {
    "use strict";

    const TOUR_PAGE_ELEM = 1;
    var tourPosition = 0;

    function LoadTournaments(e){
        if (e) {
            e.preventDefault();
        }
        var posNewTournaments = tourPosition + TOUR_PAGE_ELEM;
        $.ajax({
            url: "https://localhost:8443/TenniShip/RegisterMatch/Tournament/ListTournament/" + tourPosition + "/" + posNewTournaments,
            type: "GET",
            dataType: "html",
            success: function(msg){
                spinner();
                $('#tournamentListChange').append(msg)
            }
        });
        tourPosition += TOUR_PAGE_ELEM;
        sleep(250);
        windowResize();
    }

    $('#loadContentButtonTournament').click(function(e) {
        LoadTournaments(e)
    });

    $(document).ready(function() {
        LoadTournaments()
    });

    const MATCH_PAGE_ELEM = 2;
    var matchPosition = 0;

    function LoadMatches(e){
        if (e) {
            e.preventDefault();
        }
        var teamName = document.getElementById("teamNameIdH3").innerHTML;
        var posNewMatches = matchPosition + MATCH_PAGE_ELEM;
        $.ajax({
            url: "https://localhost:8443/TenniShip/Team/" + teamName + "/ListMatches/" + matchPosition + "/" + posNewMatches,
            type: "GET",
            dataType: "html",
            success: function(msg){
                spinner();
                $('#matchListTeamFile').append(msg)
            }
        });
        matchPosition += MATCH_PAGE_ELEM;
        sleep(250);
        windowResize();
    }

    $('#loadContentButtonMatches').click(function(e) {
        LoadMatches(e)
    });

    $(document).ready(function() {
        LoadMatches()
    });

    function spinner () {
        $('#preloader').delay(100).fadeOut('slow', function () {
            $(this).remove();
        });
    }

})(jQuery);
