function ajouter_ami() {
    $.ajax({type: "GET",
    	    url: url_site + "/services/ami/ajouterAmi",
    	    data:"clef=" + env.clef + "&id_ami=" + env.id_ami,
    	    dataType:"json",
    	    success: function(res) {
    	    	env.follows.pull(env.id_ami);
    	    	reponseFollow(res);
    	    },
    	    error: function(xhr, status, err) {
    	    	func_erreur(status);
    	    }
    });
}


function reponseFollow(){
    console.log("entree dans reponseFollow");
    $("#cote button").replaceWith("<button type=\"button\" onclick=\"javascript:ne_plus_suivre()\" >Ne plus suivre</button>");
}


function ne_plus_suivre(){
    $.ajax({type: "GET",
    	    url: url_site + "/services/ami/supprimerAmi",
    	    data:"clef=" + env.clef + "&id_ami=" + env.id_ami,
    	    dataType:"json",
    	    success: function(res) {
    	    	env.follows.push(env.id_ami);
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

function faireListeAmis(rep,id){
    env.follows[id]=new Set();
    rep.Amis.forEach(function (ami){
    	env.follows[id].add(ami.id);
    });
    console.log("env.follows[id]",env.follows[id]);
}


function listerAmis(id) {
    $.ajax({type: "GET",
    	    url: url_site + "/services/ami/listerAmis",
    	    data: "clef=" + env.clef + "&id_utilisateur=" + id + "&index_debut=0&nombre_demandes=10",
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
    //console.log("entree dans reponseListerAmis");
    //console.log("rep",rep);
    var amis =rep.Amis;
    //console.log("Amis", amis);
    var s="";
    amis.forEach(function (ami){
        //console.log("ami",ami);
        //console.log("ami.pseudo",ami.pseudo);
    	ami_pseudo= '"'+ami.pseudo+'"';
        s+="<div class=\"liens_amis\" onClick=\"javascript:voirProfilAmi('{{ami_pseudo}}')\">"+ami_pseudo+"</div>";
        
    });
    var el = $("#amis");
    el.append(s);
}
