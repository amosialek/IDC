function downloadPicture(evt){
    evt.preventDefault();
    var formData = $('form').serialize();
    console.log(formData.split('%20'));
    $.ajax({
        type: 'GET',
        url: '/download2',
        data: {
            tag:formData.split(' ')[0].replace("tags=","")
        }

    }).done(function(response){
        console.log('ok');
        console.log(response.getJSON());
    })
        .fail(function(){
            console.log('failed ajax for downloadImages');

        });
}

$(document).ready(function() {
    $('form').submit(downloadPicture);
});