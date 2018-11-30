$(document).ready(function () {
    $('#buttonautofilllink').click(function() {
        // disable the button while fetching
        $('#buttonautofilllink').prop('disabled', true);
        $.ajax({
            url: '/api/getLinkInfo?url=' + encodeURIComponent(document.getElementById('url').value),
            complete: function(data) {
                $('#buttonautofilllink').prop('disabled', false);
                if (data.responseText) {
                    // the returned text is title and description on two separate lines
                    var title = data.responseText.split('\n')[0];
                    var desc = data.responseText.split('\n')[1];
                    // decodeURIComponent(escape(...)) fixes Unicode
                    if (title)
                        document.getElementById('title').value = decodeURIComponent(escape(title));
                    if (desc)
                        document.getElementById('description').value = decodeURIComponent(escape(desc));
                } else {
                    alert("Kyseisestä osoitteesta ei kyetty hakemaan otsikkoa tai kuvausta.");
                }
            }
        });
    });
});
