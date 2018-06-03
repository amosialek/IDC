function loadPicture(){
$.ajax({
        type: 'GET',
        url: 'http://localhost:8888/image/random'
        
    }).done(function(response){
        console.log('ok');
	  id=response.id;
	  imageLink=response.imageLink;
	  $('img').attr("src",response.imageLink);
    })
    .fail(function(response){
        console.log('fail');

        });
}

function commitTag (evt) {
    evt.preventDefault(); //prevents the default action
    var formData = $('form').serialize();
    console.log(formData);
    console.log(id);
    $.ajax({
        type: 'POST',
	  headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
	    },
        url: $('form').attr('action')+"?id="+String(id),
        data: '{"imageLink": "'+imageLink+'","tags": ["'+formData+'"]}'
    }).done(function(response){
        console.log('ok');
    })
    .fail(function(response){
        console.log('fail');

        });

}

$(document).ready(function() {
var id=-1;
var imageLink="";

    loadPicture();
    $('form').submit(commitTag);
});