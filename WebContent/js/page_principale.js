/**
 * Charge le contenu de la page HTML du header + celle de la page principale.
 */
function makePagePrincipale() {
    env.fromId = -1;
    env.query = "";
    env.messages = [];
    env.minId = Infinity;
    env.maxId = -Infinity;
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_principale.html");
    });
}
