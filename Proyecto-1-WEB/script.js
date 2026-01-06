//Implementacion de carrusel
let slider = document.querySelector(".slider");  //se obtiene el objeto del contenedor que implementa el flex de slider

//slider_inner.style.transform = 'translateX(-68%)';

let index = 1; //se declara la primera posicion en uno

let containers = slider.querySelectorAll('.profile'); //se obtienen todos los profiles que se encuentran dentro del slider

setInterval(function() { //funcion que permite que el carrusel se ejecute
    let porcentaje = index * - 34; //operacion para calcular la traslacion de cada elemento por el eje X
    slider.style.transform = `translateX(${porcentaje}%)`; //linea que ejecuta el cambio en el estilo CSS
    index++; //se aumenta el indice para recorrer todos los profiles
    if (index === containers.length) { //se evalua si el indice actual es igual que el numero de profiles
        index = 0; //si es igual el index se reinicia
    }
}, 5000);

//codigo para desplegar ventana modal de formulario

const modal = document.getElementById("modal"); //se accede al objeto que contiene a todo lo que sera la ventana modal
const contactLink = document.getElementById("contact-link"); //obteniendo el objeto del enlace que manda a llamar al contenedor que es ventana modal
const closeBtn = document.querySelector(".btn-close"); //se obtiene el objeto que puede cerrar la ventana modal

// Abrir modal al hacer clic en el enlace del footer
contactLink.addEventListener("click", function(event) {
    event.preventDefault(); // Evitar que recargue la página
    modal.style.display = "flex"; // Mostrar el modal
});

// Cerrar modal al hacer clic en el botón de cierre
closeBtn.addEventListener("click", function(event) {
    event.preventDefault();
    modal.style.display = "none"; // Ocultar el modal
});

// Cerrar modal si se hace clic fuera del contenido modal
window.addEventListener("click", function(event) {
    if (event.target === modal) {
        modal.style.display = "none"; // Ocultar modal
    }
});

//Codigo para validacion de datos
const form = document.getElementById('form');
const inputEmail = document.getElementById('email');
const inputPhone = document.getElementById('phone');
const inputMessage = document.getElementById('message');

//obteniendo elementos que indican el error
const errorEmail = document.getElementById('email-error');
const errorPhone = document.getElementById('error-phone');
const errorMessage = document.getElementById('message-error');

//expresion regular para validar el formato de correo electronico
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

//expresion regular para validar el formato del numero de telefono
const phoneRegex = /^\d{4}-\d{4}$/;

form.addEventListener('submit', (element) => {
    
    let valid = true;

    //validar email
    if (!emailRegex.test(inputEmail.value.trim())) {
        document.getElementById('email-error').textContent = 'Email ingresado no es valido';
        document.getElementById('email-error').style.display = 'block';
        form.style.gap = '0.5rem';
        valid = false;
    } else {
        document.getElementById('email-error').style.display = 'none';
        form.style.gap = '1.2rem';
    }

    //validar numero de telefono
    if (!phoneRegex.test(inputPhone.value.trim())) {
        document.getElementById('phone-error').textContent = 'Formato invaliado (valido: XXXX-XXXX)';
        document.getElementById('phone-error').style.display = 'block';
        form.style.gap = '0.5rem';
        valid = false;
    } else {
        document.getElementById('phone-error').style.display = 'none';
        form.style.gap = '1.2rem';
    }

    //validar mensaje de consulta
    if (inputMessage.value.trim() === '' || inputMessage.value.trim() < 10) {
        document.getElementById('message-error').textContent = 'Mensaje de consulta requerido con 10 caracteres minimo';
        document.getElementById('message-error').style.display = 'block';
        valid = false;
    } else {
        document.getElementById('message-error').style.display = 'none';
    }

    //Si alguna de las validaciones falla prevenimos el envio de los datos
    if (!valid) {
        element.preventDefault();
    }
})
