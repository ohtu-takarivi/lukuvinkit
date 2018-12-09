$(document).ready(function () {
    $('#category-sidebar').on('change', function () {
        if (this.value == 'articles') {
            $("#isbn-div").hide();
            $("#url-div").hide();
        } else if (this.value == 'books') {
            $("#isbn-div").show();
            $("#url-div").hide();
        } else if (this.value == 'links') {
            $("#isbn-div").hide();
            $("#url-div").show();
        } else if (this.value == 'videos') {
            $("#isbn-div").hide();
            $("#url-div").show();
        }
    }).change();
    $('.javascript-only').removeClass('javascript-only');
});

/* Set the width of the side navigation to 250px */
function openNav() {
    document.getElementById("sidebar").style.width = "350px";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
    document.getElementById("sidebar").style.width = "0";
}

$(document).ready(function () {
    $('#buttonautofillbook-sidebar').click(function() {
        // disable the button while fetching
        $('#buttonautofillbook-sidebar').prop('disabled', true);
        // clean up the ISBN and do some quick validation
        var isbnClean = encodeURIComponent(document.getElementById('isbn-sidebar').value);
        isbnClean = isbnClean.replace(/-/g, '').replace(/ /g, '');
        if (isbnClean.length != 10 && isbnClean.length != 13) {
            alert('Annettu ISBN-tunnus ei ole validi.');
            return;
        }
        // use the Finna API to get book information; see https://api.finna.fi/swagger-ui/ for response data and so on
        $.ajax({
            url: 'https://api.finna.fi/api/v1/search?lookfor=isbn%3A' + isbnClean + '%20&type=AllFields&field[]=title&field[]=authors&sort=relevance%2Cid%20asc&page=1&limit=20&prettyPrint=false&lng=fi',
            complete: function(data) {
                $('#buttonautofillbook-sidebar').prop('disabled', false);
                if (data.responseText) {
                    var obj = JSON.parse(data.responseText);
                    if (obj['status'] != 'OK') {
                        // status should be OK
                        alert('Tietoja ei onnistuttu hakemaan; Finnan rajapinta palautti tilan "' + obj['status'] + '".');
                        return;
                    } else if (obj['resultCount'] < 1) {
                        // no results
                        alert('Kyseiselle ISBN-tunnukselle ei löytynyt teosta. Onko ISBN varmasti oikein?');
                        return;
                    } else {
                        var record = obj['records'][0];
                        // get title if found
                        if (record['title'])
                            $('#title-sidebar').val(record['title']);
                        // get authors if found
                        if (record['authors']) {
                            var authors = [];
                            // various author types include primary, secondary, corporate;
                            //    get all authors and join with ; as separator
                            Object.keys(record['authors']).forEach(function(type) {
                                Object.keys(record['authors'][type]).forEach(function(author) {
                                    authors.push(author);
                                });
                            });
                            $('#author-sidebar').val(authors.join('; '));
                        }
                    }
                } else {
                    alert('Tietoja ei onnistuttu hakemaan; Finnan rajapinta saattaa olla tilapäisesti pois käytöstä.');
                }
            }
        });
    });
});

$(document).ready(function () {
    $('#buttonautofilllink-sidebar').click(function() {
        // disable the button while fetching
        $('#buttonautofilllink-sidebar').prop('disabled', true);
        $.ajax({
            url: '/api/getLinkInfo?url=' + encodeURIComponent(document.getElementById('url').value),
            complete: function(data) {
                $('#buttonautofilllink-sidebar').prop('disabled', false);
                if (data.responseText) {
                    // {"title": "title", "description": "description"}
                    var obj = JSON.parse(data.responseText);
                    var title = obj['title'];
                    var desc = obj['description'];
                    // decodeURIComponent(escape(...)) fixes Unicode
                    if (title)
                        $('#title-sidebar').val(decodeURIComponent(escape(title)));
                    if (desc)
                        $('#description-sidebar').val(decodeURIComponent(escape(desc)));
                } else {
                    alert("Kyseisestä osoitteesta ei kyetty hakemaan otsikkoa tai kuvausta.");
                }
            }
        });
    });
});
