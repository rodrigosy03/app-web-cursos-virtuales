$(".btn-toggle").click(function () {
    $(this).toggleClass("collapsed");
    $(this).find("i").toggleClass("fa-chevron-right fa-chevron-down");
});