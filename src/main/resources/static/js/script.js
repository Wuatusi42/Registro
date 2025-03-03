
var contenedorRegister = document.querySelector(".container-registro");
var form = document.querySelector(".register-form");
var format = document.querySelector(".register-format");
var backBoxForm = document.querySelector(".back-box-form");
var backBoxFormat = document.querySelector(".back-box-format");

// Función para alternar la visibilidad del formulario

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".register-form").style.display = "block";  // Mostrar formulario individual por defecto
    document.querySelector(".register-format").style.display = "none"; // Ocultar formulario de formato
    document.querySelector(".consulta-form").style.display = "none";   // Ocultar formulario de consulta

    document.getElementById("btn-form").addEventListener("click", function () {
        document.querySelector(".register-form").style.display = "block";
        document.querySelector(".register-format").style.display = "none";
        document.querySelector(".consulta-form").style.display = "none";
    });

    document.getElementById("btn-format").addEventListener("click", function () {
        document.querySelector(".register-form").style.display = "none";
        document.querySelector(".register-format").style.display = "block";
        document.querySelector(".consulta-form").style.display = "none";
    });

    document.getElementById("btn-cons").addEventListener("click", function () {
        document.querySelector(".register-form").style.display = "none";
        document.querySelector(".register-format").style.display = "none";
        document.querySelector(".consulta-form").style.display = "block";
    });
});



function showPopup(message) {
    var popup = document.getElementById("popup");
    var popupMessage = document.getElementById("popupMessage");
    popupMessage.textContent = message; // Establecer el mensaje en el popup
    popup.style.display = "block"; // Mostrar el popup
}

// Función para cerrar el popup
function closePopup() {
    var popup = document.getElementById("popup");
    if (popup) {
        popup.style.display = "none"; // Ocultar el popup
    }
}

// Mostrar los mensajes de éxito o error al cargar la página
document.addEventListener("DOMContentLoaded", function() {
    var successMessage = /*[[${mensajeExito}]]*/'';
    var errorMessage = /*[[${mensajeError}]]*/'';

    if (successMessage) {
        showPopup(successMessage);
    } else if (errorMessage) {
        showPopup(errorMessage);
    }
});
    document.getElementById('birthday').addEventListener('focus', function(e) {
        var currentValue = e.target.value;
        if (currentValue) {
            var date = new Date(currentValue);
            // Establece la hora, minutos y segundos a 00
            date.setHours(0, 0, 0, 0);
            e.target.value = date.toISOString().slice(0, 16); // Formato YYYY-MM-DDTHH:MM
        }
    });

    document.getElementById('birthday').addEventListener('change', function(e) {
        var date = new Date(e.target.value);
        // Establece la hora, minutos y segundos a 00
        date.setHours(0, 0, 0, 0);
        e.target.value = date.toISOString().slice(0, 16); // Formato YYYY-MM-DDTHH:MM
    });
    function mayus(e) {
        e.value = e.value.toUpperCase();
    }
    // Función para cerrar el popup específico de registro
    function closePopupRegistro() {
        var popup = document.getElementById("popupRegistro");
        if (popup) {
            popup.style.display = "none"; // Ocultar el popup
        }
    }

    // Mostrar los mensajes de éxito o error al cargar la página
   document.addEventListener("DOMContentLoaded", function() {
       var popupRegistro = document.getElementById("popupRegistro");
       var cerrarPopupRegistro = document.getElementById("cerrarPopupRegistro");

       if (popupRegistro && cerrarPopupRegistro) {
           cerrarPopupRegistro.addEventListener("click", function() {
               popupRegistro.style.display = "none";
           });
       }
   });


    // Función para mostrar el popup con el mensaje
    function showPopupRegistro(message) {
        var popup = document.getElementById("popupRegistro");
        var popupMessage = popup.querySelector("h4");
        popupMessage.textContent = message; // Establecer el mensaje en el popup
        popup.style.display = "block"; // Mostrar el popup
    }
 


