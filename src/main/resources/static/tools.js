function loadPicture(){
$.ajax({
        type: 'GET',
        url: '/image/random'
        
    }).done(function(response){
        var img = new Image();
        imageLink=response.imageLink;
        img.onload = function(){
            // code here to use the dimensions
            scale = img.width/500;
            newWidth = img.width/scale;
            newHeight = img.height/scale;
            img.width = newWidth;
            img.height = newHeight;
            var imageDiv =document.getElementsByName("ImageDiv")[0];
            imageDiv.firstChild.remove();
            imageDiv.appendChild(img);
        };
        img.src = imageLink;

        console.log('ok');
	  id=response.id;
    })
    .fail(function(response){
        console.log('failed to load picture');
        console.log(response);
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
        'Content-Type': 'application/json',
          'token': token
	    },
        url: $('form').attr('action')+"?id="+String(id),
        data: '{"imageLink": "'+imageLink+'","tags": ["'+formData+'"]}'
    }).done(function(response){
        console.log('ok');
    })
    .fail(function(response){
        console.log('failed to post tag');
        console.log(response)
        });

}








$(document).ready(function() {
var id=-1;
var imageLink="";

    loadPicture();
    $('form').submit(commitTag);
});