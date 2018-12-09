function autofilllink(buttonautofilllinkId, descId, titleId, urlId) {
    // disable the button while fetching
    $('#' + buttonautofilllinkId).prop('disabled', true);
    $.ajax({
        url: '/api/getLinkInfo?url=' + encodeURIComponent(document.getElementById(urlId).value),
        complete: function(data) {
            $('#' + buttonautofilllinkId).prop('disabled', false);
            if (data.responseText) {
                // {"title": "title", "description": "description"}
                var obj = JSON.parse(data.responseText);
                var title = obj['title'];
                var desc = obj['description'];
                // decodeURIComponent(escape(...)) fixes Unicode
                if (title)
                    $('#' + titleId).val(decodeURIComponent(escape(title)));
                if (desc)
                    $('#' + descId).val(decodeURIComponent(escape(desc)));
            } else {
                alert("Kyseisestä osoitteesta ei kyetty hakemaan otsikkoa tai kuvausta.");
            }
        }
    });
}

$(document).ready(function () {
    $('#buttonautofilllink').click(function(){
        autofilllink('buttonautofilllink', 'description', 'title', 'url');
    });
});
