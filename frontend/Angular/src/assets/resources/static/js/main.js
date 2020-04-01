(function ($) {
  "use strict";

  // Preloader
  $(window).on('load', function () {
    if ($('#preloader').length) {
      $('#preloader').delay(100).fadeOut('slow', function () {
        $(this).remove();
      });
    }
  });

  // Back to top button
  $(window).scroll(function() {
    if ($(this).scrollTop() > 100) {
      $('.back-to-top').fadeIn('slow');
    } else {
      $('.back-to-top').fadeOut('slow');
    }
  });
  $('.back-to-top').click(function(){
    $('html, body').animate({scrollTop : 0},1500, 'easeInOutExpo');
    return false;
  });

  // Search boxes functionality

  $("#tournamentForm").keydown(function(e) {
    if (e.which === 13) {
      e.preventDefault();
      var input = document.getElementById("tournamentSearch");
      var text = input.value;
      var url = "./TenniShip/Tournaments/" + text;
      $(location).attr('href',url);
    }
  });

  $("#teamForm").keydown(function(e) {
    if (e.which === 13) {
      e.preventDefault();
      var input = document.getElementById("teamSearch");
      var text = input.value;
      var url = "./TenniShip/Teams/" + text;
      $(location).attr('href',url);
    }
  });

  // Initiate superfish on nav menu
  $('.nav-menu').superfish({
    animation: {
      opacity: 'show'
    },
    speed: 400
  });


  // Intro carousel
  var introCarousel = $(".carousel");
  var introCarouselIndicators = $(".carousel-indicators");
  introCarousel.find(".carousel-inner").children(".carousel-item").each(function(index) {
    (index === 0) ?
    introCarouselIndicators.append("<li data-target='#introCarousel' data-slide-to='" + index + "' class='active'></li>") :
    introCarouselIndicators.append("<li data-target='#introCarousel' data-slide-to='" + index + "'></li>");

    $(this).css("background-image", "url('" + $(this).children('.carousel-background').children('img').attr('src') +"')");
    $(this).children('.carousel-background').remove();
  });

  $(".carousel").swipe({
    swipe: function(event, direction, distance, duration, fingerCount, fingerData) {
      if (direction == 'left') $(this).carousel('next');
      if (direction == 'right') $(this).carousel('prev');
    },
    allowPageScroll:"vertical"
  });

  // Progress bar (for tournament completion)
  $('#progress-content').waypoint(function() {
    $('.progress .progress-bar').each(function() {
      $(this).css("width", $(this).attr("aria-valuenow") + '%');
    });
  }, { offset: '80%'} );


  document.getElementById('buttonDeleteTeamConfirm').addEventListener("click",
    function(){
      $('#DeleteTeamModal').modal('toggle');
      $('#ConfirmDeleteTeam').modal('toggle');
  });



  document.getElementById('buttonDeleteTournamentConfirm').addEventListener("click",
    function(){
      $('#DeleteTournamentModal').modal('toggle');
      $('#ConfirmDeleteTournament').modal('toggle');
  });

  //




})(jQuery);


