

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
        var posNew = tourPosition + TOUR_PAGE_ELEM;
        $.ajax({
            url: "https://localhost:8443/TenniShip/RegisterMatch/Tournament/ListTournament/" + tourPosition + "/" + posNew,
            type: "GET",
            dataType: "html",
            success: function(msg){
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

})(jQuery);