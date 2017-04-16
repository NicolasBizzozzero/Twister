function ajouter_ami(){
    $.ajax({type:"GET", url: url_site + "/services/ami/ajouterAmi", data:"clef="+env.clef+"&id_ami="+env.id_ami, dataType:"json",success:function(res){ reponseFollow(res);},error:function(xhr,status,err){func_erreur(status);}});
}

function reponseFollow(){
    $("#cote button").replaceWith("<button type=\"button\" onclick=\"javascript:ne_plus_suivre()\" >Ne plus suivre</button>");
}

function ne_plus_suivre(){
    $.ajax({type:"GET", url: url_site + "/services/ami/supprimerAmi", data:"clef="+env.clef+"&id_ami="+env.id_ami, dataType:"json",success:function(res){ reponseStopFollow(res);},error:function(xhr,status,err){func_erreur(status);}});
}

function reponseStopFollow(){
    $("#cote button").replaceWith("<button type=\"button\" onclick=\"javascript:ajouter_ami()\" >Suivre</button>");
}

function faireListeAmis(rep,id){
    env.follows[id]=new Set();
    rep.Amis.forEach(function (ami){
    	env.follows[id].add(ami.id);
    });
    //console.log("env.follows[id]",env.follows[id]);
}

function listerAmis(id){
    $.ajax({type:"GET", url: url_site + "/services/ami/listerAmis", data:"clef="+env.clef+"&id_utilisateur="+id+"&index_debut=0&nombre_demandes=10", dataType:"json",success:function(res){ reponseListerAmis(res);},error:function(xhr,status,err){func_erreur(status);}});
}

function reponseListerAmis(rep){
    var amis =rep.Amis;
    var s="";
    amis.forEach(function (ami){
        s+="<div class=\"liens_amis\" onClick=\"javascript:makePageProfil("+ami.id+")\">"+ami.pseudo+"</div>"; 
    });
    var el = $("#amis");
    el.append(s);
}
