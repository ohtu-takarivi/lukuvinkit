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

    // Sidebar toggle
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
});
