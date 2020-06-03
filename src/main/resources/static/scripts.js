// Tabbed Menu Index
function openMenu(evt, menuName) {
    var i, x, tablinks;
    x = document.getElementsByClassName("menu");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < x.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" w3-dark-grey", "");
    }
    document.getElementById(menuName).style.display = "block";
    evt.currentTarget.firstElementChild.className += " w3-dark-grey";
}
document.getElementById("myLink").click();


function move() {
    var elem = document.getElementById("myBar");   
    var width = 20;
    var id = setInterval(frame, 10);
    function frame() {
      if (width >= 100) {
        clearInterval(id);
      } else {
        width++; 
        elem.style.width = width + '%'; 
        elem.innerHTML = width * 1  + '%';
      }
    }
  }
  var colorButton = document.getElementById("primary_color");
    var colorDiv = document.getElementById("color_val");
    colorButton.onchange = function() {
        colorDiv.innerHTML = colorButton.value;
        colorDiv.style.color = colorButton.value;
    }
    