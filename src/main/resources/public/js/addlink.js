$(document).ready(function () {
    $('#buttonautofilllink').click(function() {
        // disable the button while fetching
        $('#buttonautofilllink').prop('disabled', true);
        $.ajax({
            url: '/api/getLinkInfo?url=' + encodeURIComponent(document.getElementById('url').value),
            complete: function(data) {
                $('#buttonautofilllink').prop('disabled', false);
                if (data.responseText) {
                    // {"title": "title", "description": "description"}
                    var obj = JSON.parse(data.responseText);
                    var title = obj['title'];
                    var desc = obj['description'];
                    // decodeURIComponent(escape(...)) fixes Unicode
                    if (title)
                        $('#title').val(decodeURIComponent(escape(title)));
                    if (desc)
                        $('#description').val(decodeURIComponent(escape(desc)));
                } else {
                    alert("Kyseisestä osoitteesta ei kyetty hakemaan otsikkoa tai kuvausta.");
                }
            }
        });
    });
});
