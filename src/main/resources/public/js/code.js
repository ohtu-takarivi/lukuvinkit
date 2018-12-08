$(document).ready(function () {
    $('#category').on('change', function () {
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
