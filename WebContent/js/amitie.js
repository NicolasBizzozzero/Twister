function ajouter_ami() {
    $.ajax({type: "GET",
    	    url: url_site + "/services/ami/ajouterAmi",
    	    data:"clef=" + env.clef + "&id_ami=" + env.id_utilisateur,
    	    dataType:"json",
    	    success: function(res) {
    	    	reponseFollow(res);
    	    },
    	    error: function(xhr, status, err) {
    	    	func_erreur(status);
    	    }
    });
}


function reponseFollow(){
    $("#cote button").replaceWith("<button type=\"button\" onclick=\"javascript:ne_plus_suivre()\" >Ne plus suivre</button>");
}


function ne_plus_suivre(){
    $.ajax({type: "GET",
    	    url: url_site + "/services/ami/supprimerAmi",
    	    data:"clef=" + env.clef + "&id_ami=" + env.id_utilisateur,
    	    dataType:"json",
    	    success: function(res) {
    	    	reponseStopFollow(res);
    	    },
    	    error: function(xhr, status, err) {
    	    	func_erreur(status);
    	    }
    });
}


function reponseStopFollow(){
    $("#cote button").replaceWith("<button type=\"button\" onclick=\"javascript:ajouter_ami()\" >Suivre</button>");
}


function listerAmis(){
    $.ajax({type: "GET",
    	    url: url_site + "/services/ami/listerAmis",
    	    data: "clef=" + env.clef + "&id_utilisateur=" + env.id_utilisateur + "&index_debut=0&nombre_demandes=10",
    	    dataType: "json",
    	    success: function(res) {
    	    	reponseListerAmis(res);
    	    },
    	    error: function(xhr, status, err) {
    	    	func_erreur(status);
    	    }
    });
}


function reponseListerAmis(rep){
    console.log("entree dans reponseListerAmis");
    console.log("rep",rep);
    var amis =rep.Amis;
    console.log("Amis", amis);
    var s="";
    amis.forEach(function (ami){
        console.log("ami",ami);
        s+="<div class=\"liens_amis\" >"+ami.login+"</div>";
        
    });
    var el = $("#amis");
    el.append(s);
}
