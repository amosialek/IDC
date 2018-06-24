function signOut(){
    $.ajax({
        type: 'GET',
        url: '/logout'
    }).done(function(response){
        console.log("ok");
        window.location.replace("/describe")
    })
        .fail(function(response){
            console.log('failed ajax for logging out');
            console.log(response.statusCode());
            window.location.replace("/describe")
        });
}