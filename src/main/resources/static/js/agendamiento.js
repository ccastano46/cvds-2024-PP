const fecha = document.getElementById("fecha-actual"),
dias = document.querySelector(".dias"),
    horas = document.querySelector("#horas"),
    prevNext = document.querySelectorAll(".icons span"),
botonCotizar = document.getElementById("botonCotizar");

const contenidoC = document.querySelector("#calendario div.contenido"),
    contenidoI = document.querySelector("#informacion div.contenido");

const agendamiento = document.querySelector("form.agendamiento");

const modal = document.getElementById("modal");

const respuestaTexto = document.querySelector("#modal h2");


let date = new Date(),
ano = date.getFullYear(),
mes = date.getMonth();

const meses = ["Enero","Febreo","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];

function ajustarCalendario(){
    var nuevoLi;
    while (dias.firstChild) {
        dias.removeChild(dias.firstChild);
    }
    let primerDiaDelMes = new Date(ano,mes ,1).getDay(),
        ultimaFechaDelMes =new Date(ano,mes + 1,0).getDate(),
        ultimoDiaDelMes = new Date(ano,mes,ultimaFechaDelMes).getDay(),
        ultimaFechaDelUltimoMes = new Date(ano,mes,0).getDate();
    //let liTag = "";
    for(let i = primerDiaDelMes; i > 0;i--){
        nuevoLi  = document.createElement('li');
        nuevoLi.textContent = (ultimaFechaDelUltimoMes - i + 1).toString();
        nuevoLi.classList.add("inactivo");
        dias.appendChild(nuevoLi);
    }

    for(let i = 1; i <= ultimaFechaDelMes;i++){
        nuevoLi  = document.createElement('li');
        nuevoLi.textContent = i.toString();
        if(i === date.getDate() && mes === new Date().getMonth() && ano === new Date().getFullYear()){
            nuevoLi.classList.add("activo");
        }

        if ((ano === new Date().getFullYear() && mes === new Date().getMonth() && i < date.getDate())
            || (ano === new Date().getFullYear() && mes < new Date().getMonth())
            || ano < new Date().getFullYear()){
            nuevoLi.classList.add("inactivo");
        }else{
            nuevoLi.onclick = function() {
                seleccionar(this);
            };
        }
        dias.appendChild(nuevoLi);
        igualarAltura();
    }

    for(let i = ultimoDiaDelMes; i < 6 ;i++){
        nuevoLi  = document.createElement('li');
        nuevoLi.textContent = (i - ultimoDiaDelMes + 1).toString();
        nuevoLi.classList.add("inactivo");
        dias.appendChild(nuevoLi);
        //liTag +=  `<li class="inactivo">${i - ultimoDiaDelMes + 1}</li>`;
    }

    fecha.innerText = meses[mes] + ', ' + ano ;
    if(document.querySelector("ul.dias li.activo") != null){
        seleccionar(document.querySelector("ul.dias li.activo"));
    }

}

prevNext.forEach(icon =>{
    icon.addEventListener("click",() =>{
        mes = icon.id === "anterior" ? mes - 1 : mes + 1;
        if(mes < 0 || mes > 11){
            date = new Date(ano,mes, new Date().getDate());
            ano = date.getFullYear();
            mes = date.getMonth();
        } else {
            date = new Date();
        }
        while (horas.firstChild) {
            horas.removeChild(horas.firstChild);
        }
        ajustarCalendario();
    });
});




function actualizarHoras(ano,mes,dia){
    while (horas.firstChild) {
        horas.removeChild(horas.firstChild);
    }
    fetch('/cotizaciones/horasDisponibles/' + ano + '/' + mes + '/' + dia).then(response => response.json())
        .then(data => {
            data.forEach(hora =>{
                var nuevoLi = document.createElement('li');
                nuevoLi.textContent = hora;
                nuevoLi.classList.add('informacion');
                nuevoLi.onclick = function() {
                    seleccionarHora(this);
                };
                horas.appendChild(nuevoLi);
            });
        });

}

function seleccionar(elemento){
    horas.style.opacity = "0";
    const activo = document.querySelector("ul.dias li.activo");
    if(activo != null) activo.classList.remove("activo");
    elemento.classList.toggle("activo");
    const partes = fecha.innerText.split(/,\s*/);
    setTimeout(function() {
        actualizarHoras(partes[1],partes[0],elemento.innerHTML);
        horas.style.opacity = "1";
    }, 2000);
}

function seleccionarHora(elemento){
    const activo1 = document.querySelector("#horas li.activo");
    if(activo1 != null) activo1.classList.remove("activo");
    elemento.classList.toggle("activo");
    botonCotizar.style.display = "block";
}

function igualarAltura(){
    const alturaMaxima = contenidoC.offsetHeight;
    contenidoI.style.height = `${alturaMaxima}px`
}

function formulario() {
    botonCotizar.style.display = "none";
    horas.style.display = "none";
    const dia = document.querySelectorAll("ul.dias li");

    for (let i = 0; i < dia.length; i++) {
        // Hacer algo con cada elemento <li>
        dia[i].onclick = null;
    }

    agendamiento.style.display = "block";
}

function respuesta(event){
    event.preventDefault();
    const partes = fecha.innerText.split(/,\s*/);
    document.getElementById("dia").value = document.querySelector('#calendario div.contenido ul.dias li.activo').innerHTML;
    document.getElementById("mes").value = partes[0];
    document.getElementById("ano").value = partes[1];
    document.getElementById("hora").value = document.querySelector('#informacion div.contenido #horas li.activo').innerHTML;
    agendamiento.submit();

}

document.addEventListener("DOMContentLoaded", function() {
    if(respuestaTexto.innerHTML){
        console.log(1);
        modal.showModal();
    }
});

function cerrarModal(){
    modal.close();
    window.location.replace("/LincolnLines");
}

ajustarCalendario();
