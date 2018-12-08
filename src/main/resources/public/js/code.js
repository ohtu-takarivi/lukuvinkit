$(document).ready(function () {
    // remove javascript-only class to show JS-only content
    $('.javascript-only').removeClass('javascript-only');

    // for the sidebar form
    $('#sidebarcategory').on('change', function () {
        if (this.value == 'articles') {
            $("#sidebarisbn-div").hide();
            $("#sidebarurl-div").hide();
        } else if (this.value == 'books') {
            $("#sidebarisbn-div").show();
            $("#sidebarurl-div").hide();
        } else if (this.value == 'links' || this.value == 'videos') {
            $("#sidebarisbn-div").hide();
            $("#sidebarurl-div").show();
        }
    }).change();

    // Sidebar toggle
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
});
