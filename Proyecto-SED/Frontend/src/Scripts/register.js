import containsMongoOperators from "/src/utils/containsMongoOperators.js";

export function init() {

    const errorText = document.getElementById('error-text');

    document.getElementById('registro-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const user = document.getElementById('nombre').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmar').value;
        errorText.innerText = '';

        if (!validateInputs(user, email, password, confirmPassword)) {
            return;
        }

        await submitRegistration(user, email, password);
    });

    function validateInputs(user, email, password, confirmPassword) {
        try {
            if (!user || !email || !password || !confirmPassword) {
                errorText.innerText = 'Por favor, completa todos los campos.';
                return false;
            }
            if (typeof email !== 'string' || typeof password !== 'string' || typeof confirmPassword !== 'string' || typeof user !== 'string') {
                errorText.innerText = 'Entrada inválida.';
                return false;
            }
            const regexp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!regexp.test(email)) {
                errorText.innerText = 'Por favor, ingresa un correo electrónico válido.';
                return false;
            }
            if (password.length < 6) {
                errorText.innerText = 'La contraseña debe tener al menos 6 caracteres.';
                return false;
            }
            if (password.length > 50) {
                errorText.innerText = 'La contraseña no debe exceder los 50 caracteres.';
                return false;
            }
            if (password.includes(' ')) {
                errorText.innerText = 'La contraseña no debe contener espacios.';
                return false;
            }
            const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
            if (!regexPassword.test(password)) {
                errorText.innerText = 'La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.';
                return false;
            }
            if (password !== confirmPassword) {
                errorText.innerText = 'Las contraseñas no coinciden.';
                return false;
            }
            if (containsMongoOperators(user) || containsMongoOperators(email) || containsMongoOperators(password) || containsMongoOperators(confirmPassword)) {
                errorText.innerText = 'Entrada inválida.';
                return false;
            }
            return true;
        } catch (error) {
            errorText.innerText = 'Error en la validación de entradas: ' + error.message;
        }
    }

    async function submitRegistration(name, email, password) { 
        try {
            const cart = [];
            const response = await fetch(`${import.meta.env.VITE_API_URL}/api/users/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name, email, password , cart }),
            });
            const result = await response.json(); 

            /* deberia ser asi
            result = {
                "message": "Usuario registrado exitosamente.",
                "user": {
                    "_id": "691d79088be627bcc1619c28",
                    "name": "Gabriel Iraheta",
                    "email": "gabo7iraheta00@gmail.com",
                    "cart": []
                }
            }
            */

            if (response.ok) {
                alert('Registro exitoso. Disfrute su compra!');
                window.location.href = '/';
            } else {
                errorText.innerText = result.error || 'Error en el registro.';
            }
        } catch (error) {
            errorText.innerText = 'Error al conectar con el servidor: ' + error.message;
        }
    }
}