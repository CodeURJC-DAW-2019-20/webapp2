$(document).ready(function (){	

    document.getElementById('SignInPopUp').addEventListener("click",
    function(){
        document.querySelector('.jsSignIn').style.display = "flex";
    });

    document.getElementById('SignUpPopUp').addEventListener("click",
    function(){
        document.querySelector('.jsSignUp').style.display = "flex";
    });

    
    document.querySelector(".close").addEventListener("click",
    function(){
        document.querySelector('.bg-popup').style.display = 'none';
    });
}); 