function downloadPicture(evt){
    evt.preventDefault();
    var formData = $('form').serialize();
    console.log(formData.split('%20'));
    $.ajax({
        type: 'GET',
        url: '/dataset',
        data: {
            tags:formData.split(' ')
        }

    }).done(function(response){
        console.log("ok");
        img.src = imageLink;

        console.log('ok');
        id=response.id;
    })
        .fail(function(response){
            console.log('failed ajax for downloadImages');

        });
}

$(document).ready(function() {
    $('form').submit(downloadPicture);
});