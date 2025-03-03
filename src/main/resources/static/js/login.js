window.onload = function () {
        // Mostrar el modal si existe el error en el modelo
        var errorModal = document.getElementById("errorModal");
        if (errorModal) {
            var myModal = new bootstrap.Modal(errorModal);
            myModal.show();
        }

        // Ocultar la alerta de error después de 5 segundos
        var errorAlert = document.getElementById("errorAlert");
        if (errorAlert) {
            setTimeout(function () {
                errorAlert.style.transition = "opacity 0.5s ease"; // Agrega una transición suave
                errorAlert.style.opacity = "0"; // Reduce la opacidad

                setTimeout(function () {
                    errorAlert.style.display = "none"; // Oculta completamente después de la animación
                }, 500);
            }, 5000);
        }
    };