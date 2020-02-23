//Slice method for content in tournaments section
$('.displayTournamentsHidden').slice(0,3).show();

$('#loadContentButtonTournament').on('click',async function(){
    $('.displayTournamentsHidden:hidden').slice(0,3).slideDown();
    if($('.displayTournamentsHidden:hidden').length===0){
        $('#loadContentButtonTournament').fadeOut();
    }
    await sleep(250);
    windowResize();
});

$('.displayMatchesHidden').slice(0,2).show();

$('#loadContentButtonMatches').on('click',async function(){
    $('.displayMatchesHidden:hidden').slice(0,1).slideDown();
    if($('.displayMatchesHidden:hidden').length===0){
        $('#loadContentButtonMatches').fadeOut();
    }
    await sleep(250);
    windowResize();
});


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