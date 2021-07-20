$.urlParam = function(name, href){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(href);
    if (results==null) {
       return null;
    }
    return decodeURI(results[1]) || 0;
}