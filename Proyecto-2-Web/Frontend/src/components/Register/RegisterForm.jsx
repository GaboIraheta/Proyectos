import React, { useContext, useEffect, useState } from "react";
import "./RegisterPage.css";
import usePost from "../../Hooks/UsePost";
import { LoginContext, LoginProvider } from "../../Context/LoginContext.jsx";
import config from "../../../config.js";
import { NavLink } from "react-router-dom";

const RegisterFormProvider = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");

  const [message, setMessage] = useState("");
  const [isValid, setIsValid] = useState(false);

  const { user, handleLogin, handleSaveID, handleSaveRole } =
    useContext(LoginContext);
  const { authentication: register, error, loading } = usePost(
    `${config.API_URL}/user/register`
  );

  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsValid(false);
    setMessage("");

    if (password !== confirm) {
      setIsValid(!isValid);
      return;
    }

    const userRegister = await register({ username, email, password });

    if (error || !userRegister) {
      console.error(error);
      return;
    }

    setMessage(userRegister.message);
    handleLogin(userRegister.data.user, userRegister.data.token);
    handleSaveID(userRegister.data.user.id);
    handleSaveRole(userRegister.data.user.role);

    window.location.href = "/profile";
  };

  useEffect(() => {
    if (user) console.log("Usuario registrado: ", user.username);
  }, [user]);

  return (
    <form className="formContainer" onSubmit={handleSubmit}>
      <header className="headerWrapper">
        <img
          loading="lazy"
          src="https://cdn.builder.io/api/v1/image/assets/TEMP/3031ae1a0ef5122343cfc046a0fe7378694ac59107f049ac12acaeaba50c1463?placeholderIfAbsent=true&apiKey=dc5b04b1bd644122995a9f89f3c5e2ef"
          className="logo"
          alt="Company logo"
        />
        <h1 className="title">Registrarse</h1>
      </header>

      <div className="formField">
        <label htmlFor="username" className="formLabel">
          Usuario
        </label>
        <input
          type="text"
          id="username"
          className="formInput"
          value={username}
          onChange={(event) => setUsername(event.target.value)}
          required
        />
      </div>

      <div className="formField">
        <label htmlFor="email" className="formLabel">
          Email
        </label>
        <input
          type="text"
          id="email"
          className="formInput"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
        />
      </div>

      <div className="formField">
        <label htmlFor="password" className="formLabel">
          Contraseña
        </label>
        <input
          type="password"
          id="password"
          className="formInput"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
        />
      </div>

      <div className="formField">
        <label htmlFor="confirmPassword" className="formLabel">
          Ingresa de nuevo la contraseña
        </label>
        <input
          type="password"
          id="confirmPassword"
          className="formInput"
          value={confirm}
          onChange={(event) => setConfirm(event.target.value)}
          required
        />
      </div>

      <div className="socialLoginContainer">
        <button type="submit" className="button google" disabled={loading}>
          {loading ? "Registrando..." : "Registrarse"}
        </button>

        <button class="button google">
          <svg
            viewBox="0 0 256 262"
            preserveAspectRatio="xMidYMid"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M255.878 133.451c0-10.734-.871-18.567-2.756-26.69H130.55v48.448h71.947c-1.45 12.04-9.283 30.172-26.69 42.356l-.244 1.622 38.755 30.023 2.685.268c24.659-22.774 38.875-56.282 38.875-96.027"
              fill="#4285F4"
            ></path>
            <path
              d="M130.55 261.1c35.248 0 64.839-11.605 86.453-31.622l-41.196-31.913c-11.024 7.688-25.82 13.055-45.257 13.055-34.523 0-63.824-22.773-74.269-54.25l-1.531.13-40.298 31.187-.527 1.465C35.393 231.798 79.49 261.1 130.55 261.1"
              fill="#34A853"
            ></path>
            <path
              d="M56.281 156.37c-2.756-8.123-4.351-16.827-4.351-25.82 0-8.994 1.595-17.697 4.206-25.82l-.073-1.73L15.26 71.312l-1.335.635C5.077 89.644 0 109.517 0 130.55s5.077 40.905 13.925 58.602l42.356-32.782"
              fill="#FBBC05"
            ></path>
            <path
              d="M130.55 50.479c24.514 0 41.05 10.589 50.479 19.438l36.844-35.974C195.245 12.91 165.798 0 130.55 0 79.49 0 35.393 29.301 13.925 71.947l42.211 32.783c10.59-31.477 39.891-54.251 74.414-54.251"
              fill="#EB4335"
            ></path>
          </svg>
          Registrarse con Google
        </button>
      </div>

      {message && <p style={{ color: "green" }}>{message}</p>}

      {error && <p style={{ color: "red" }}>{error}</p>}

      {isValid && (
        <p style={{ color: "red" }}>Las contraseñas ingresadas no coinciden</p>
      )}
    </form>
  );
};

export const RegisterForm = () => {
  return (
    <>
      <LoginProvider>
        <RegisterFormProvider />
      </LoginProvider>
    </>
  );
};
