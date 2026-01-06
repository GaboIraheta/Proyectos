import { config } from "./config.js";

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('resetBtn').addEventListener('click', resetPassword);
});

async function resetPassword() {

  const password = document.getElementById("password").value;
  const confirm = document.getElementById("confirm").value;
  const message = document.getElementById("message");
  const error = document.getElementById("error");

  message.textContent = "";
  error.textContent = "";

  if (!password || !confirm) {
    error.textContent = "Todos los campos son obligatorios";
    return;
  }

  if (password !== confirm) {
    error.textContent = "Las contraseñas no coinciden";
    return;
  }

  const token = new URLSearchParams(window.location.search).get("token");

  try {

    const res = await fetch(config.FETCH_URI, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ token, newPassword: password }),
    });

    const data = await res.json();

    if (res.ok) {

      const form_container = document.getElementById('form-container');
      if (form_container) form_container.style.display = 'none';

      const succes_container = document.getElementById('success-container');
      if (succes_container) succes_container.style.display = 'block'

      message.textContent = "Password actualizada exitosamente. Puedes iniciar sesion."

    } else {

      error.textContent = data.message || "Error al restablecer la contraseña";
    }

  } catch (e) {

    error.textContent = "Error de conexión con el servidor";
    error.textContent = e.message;
  }
}
