$(document).ready(function() {
    $('form').submit(function (evt) {
    evt.preventDefault(); //prevents the default action
    var formData = $('form').serialize();
    console.log(formData);
    $.ajax({
        type: 'POST',
        url: $('form').attr('action'),
        data: {"description":formData}
    }).done(function(response){
        console.log('ok');
    })
    .fail(function(response){
        console.log('fail');

        });

});
});