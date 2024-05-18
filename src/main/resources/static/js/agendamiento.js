const fecha = document.getElementById("fecha-actual");
const dias = document.querySelector(".dias");
const prevNext = document.querySelectorAll(".icons span");


let date = new Date();
ano = date.getFullYear();
mes = date.getMonth();

const meses = ["Enero","Febreo","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];

function ajustarCalendario(){
    let ultimoDiaDelMes = new Date(ano,mes + 1,0).getDate(),
        primerDiaDelMes =new Date(ano,mes,1).getDate(),
        ultimoDiaDelUltimoiMes = new Date(ano,mes,0).getDate(),
        ultimoDia = new Date(ano,mes,ultimoDiaDelMes).getDay();
    let liTag = "";
    for(let i = 3; i > 0;i--){
        liTag +=  `<li class="inactivo">${ultimoDiaDelUltimoiMes - i + 1}</li>`;
    }

    for(let i = 1; i <= ultimoDiaDelMes;i++){
        liTag +=  `<li>${i}</li>`;
    }


    for(let i = ultimoDia; i < 6 ;i++){
        liTag +=  `<li class="inactivo">${i - ultimoDia + 1}</li>`;
    }

    fecha.innerText = meses[mes] + ', ' + ano ;
    dias.innerHTML = liTag;
}

ajustarCalendario();

prevNext.forEach(icon =>{
   icon.addEventListener("click",() =>{
      mes = icon.id === "anterior" ? mes - 1 : mes + 1;
      ajustarCalendario();
   });
});