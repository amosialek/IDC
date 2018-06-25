function loadScore(){
    $.ajax({
        type: 'GET',
        url: '/profile/score',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).done(function(response){
        console.log(response);
        var imageDiv =document.getElementsByName("score")[0];
        imageDiv.innerHTML = "<h1><b><u>Score: " + response+"</u></b></h1>";
        console.log('ok');

    })
        .fail(function(){
            console.log('fail');

        });
}

$(document).ready(function() {
    loadScore();

});