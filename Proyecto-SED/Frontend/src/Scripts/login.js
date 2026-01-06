export function init() {

    const errorText = document.getElementById('error-text');
    const MAX_ATTEMPTS = 3;
    const LOCKOUT_TIME = 15 * 60 * 1000; // 15 minutos en milisegundos
    const LOCKOUT_KEY = 'login_lockout';
    const ATTEMPTS_KEY = 'login_attempts';

    // Verificar si el usuario está bloqueado
    function isUserLocked() {
      const lockoutTime = localStorage.getItem(LOCKOUT_KEY);
      if (!lockoutTime) return false;

      const now = Date.now();
      if (now < parseInt(lockoutTime)) {
        return true;
      } else {
        // El bloqueo ha expirado, limpiar
        localStorage.removeItem(LOCKOUT_KEY);
        localStorage.removeItem(ATTEMPTS_KEY);
        return false;
      }
    }

    // Obtener tiempo restante en segundos
    function getRemainingLockoutTime() {
      const lockoutTime = localStorage.getItem(LOCKOUT_KEY);
      if (!lockoutTime) return 0;

      const remaining = Math.ceil((parseInt(lockoutTime) - Date.now()) / 1000);
      return Math.max(0, remaining);
    }

    // Incrementar contador de intentos fallidos
    function incrementFailedAttempts() {
      let attempts = parseInt(localStorage.getItem(ATTEMPTS_KEY) || '0');
      attempts++;
      localStorage.setItem(ATTEMPTS_KEY, String(attempts));
      return attempts;
    }

    // Bloquear usuario
    function lockUser() {
      const lockoutTime = Date.now() + LOCKOUT_TIME;
      localStorage.setItem(LOCKOUT_KEY, String(lockoutTime));
    }

    // Limpiar intentos (login exitoso)
    function clearAttempts() {
      localStorage.removeItem(ATTEMPTS_KEY);
      localStorage.removeItem(LOCKOUT_KEY);
    }


    document.getElementById('login-form').addEventListener('submit', async (e) => {
        e.preventDefault();

        // Verificar si el usuario está bloqueado
        if (isUserLocked()) {
          const remaining = getRemainingLockoutTime();
          errorText.textContent = `Demasiados intentos fallidos. Intenta de nuevo en ${remaining} segundos.`;
          return;
        }

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        errorText.textContent = '';

        if (!validateInputs(email, password)) {
            incrementFailedAttempts();
            return;
        }

        await submitLogin(email, password, clearAttempts, incrementFailedAttempts, lockUser);

    });

    function validateInputs(email, password) {
        try {
            if (!email || !password) {
                errorText.textContent = 'Por favor, completa todos los campos.';
                return false;
            }

            if (typeof email !== 'string' || typeof password !== 'string') {
                errorText.textContent = 'Entrada inválida.';
                return false;
            }

            const regexp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!regexp.test(email)) {
                errorText.textContent = 'Por favor, ingresa un correo electrónico válido.';
                return false;
            }

            if (password.length < 6) {
                errorText.textContent = 'La contraseña debe tener al menos 6 caracteres.';
                return false;
            }

            if (containsMongoOperators(email) || containsMongoOperators(password)) {
                errorText.textContent = 'Entrada inválida.';
                return false;
            }

            return true;

        } catch (error) {
            errorText.textContent = 'Error en la validación de entradas: ' + error.message;
        }
    }

    function containsMongoOperators(input) {
        const mongoOperators = ['$ne', '$eq', '$gt', '$lt', '$gte', '$lte', '$in', '$nin',
            '$or', '$and', '$not', '$nor', '$exists', '$type', '$expr', '$jsonSchema', '$mod',
            '$regex', '$text', '$where', '$geoIntersects', '$geoWithin', '$near', '$nearSphere'];

        const inputStr = input.toString().toLowerCase();

        return mongoOperators.some(op => inputStr.includes(op.toLowerCase()));
    }


    async function submitLogin(email, password, clearAttempts, incrementFailedAttempts, lockUser) {
        try {

            const response = await fetch(`${import.meta.env.VITE_API_URL}/api/signin`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),

            });

            /* deberia ser asi
            result = {
                "message": "Empleado registrado exitosamente.",
                "employee": {
                    "_id": "691d75a2c98d2dd6177dffe8",
                    "name": "Esmeralda Villanueva",
                    "email": "gabo7iraheta03@gmail.com",
                    "role": true,
                    "active": true
                }
            } si es un admin o empleado (el empleado solo cambia con tener role : false)
            */

            /*
            {
                "message": "Bienvenido/a, Gabriel Iraheta",
                "user": {
                    "_id": "691f767b96d28fc1d092b70f",
                    "name": "Gabriel Iraheta",
                    "email": "gabo7iraheta00@gmail.com",
                    "cart": []
                }
            } si es un usuario normal
            */

            if (!response.ok) {
                const attempts = incrementFailedAttempts();
                const errorData = await response.json();
                
                if (attempts >= MAX_ATTEMPTS) {
                    lockUser();
                    errorText.textContent = 'Demasiados intentos fallidos. Intenta de nuevo en 15 minutos.';
                } else {
                    const remaining = MAX_ATTEMPTS - attempts;
                    errorText.textContent = `${errorData.message || 'Error en el inicio de sesión.'} (${remaining} intentos restantes)`;
                }
                return;
            }

            // Login exitoso: limpiar intentos
            const result = await response.json()
            localStorage.setItem('username', result.employee?.name || result.user?.name || '' );
            clearAttempts();
            window.location.href = '/';
        } catch (error) {
            errorText.textContent = 'Error al conectar con el servidor: ' + error.message;
        }
    }
}