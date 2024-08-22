function loadGetMsg() {
    let nameVar = document.getElementById("fruit").value;
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        document.getElementById("getrespmsg").innerHTML =
            this.responseText;
    }
    xhttp.open("GET", nameVar);
    xhttp.send();
}

function loadPostMsg(name){
    let url = "/hellopost?name=" + name.value;

    fetch (url, {method: 'POST'})
        .then(x => x.text())
        .then(y => document.getElementById("postrespmsg").innerHTML = y);
}