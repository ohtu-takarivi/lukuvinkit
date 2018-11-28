$(document).ready(function () {
    $('#buttonautofill').click(function() {
        $('#buttonautofill').prop('disabled', true);
        $.ajax({
            url: '/api/getTitle?url=' + encodeURIComponent(document.getElementById('url').value),
            complete: function(data) {
                $('#buttonautofill').prop('disabled', false);
                if (data.responseText) {
                    var title = data.responseText.split('\n')[0];
                    var desc = data.responseText.split('\n')[1];
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
