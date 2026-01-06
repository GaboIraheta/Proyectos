import React, { useContext, useEffect, useState } from "react";
import "./LoginPage.css";
import usePost from "../../Hooks/UsePost";
import { LoginContext, LoginProvider } from "../../Context/LoginContext.jsx";
import config from "../../../config.js";
import { NavLink, redirect } from "react-router-dom";
import axios from "axios";
import { Navigate } from "react-router-dom";
import useUtil from "../../Hooks/useUtil.jsx";

const LoginFormProvider = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");  
  const [message, setMessage] = useState("");
  const [yes, setYes] = useState(true);
  const [showButton, setShowButton] = useState(false);

  const { verifyRole } = useUtil();

  // useEffect(async () => {
  //   await verifyRole(email);
  // }, [email]);

  const { authentication: login, error: errorLogin, loading: loadingLogin } = usePost(
    `${config.API_URL}/${localStorage.getItem('verify')}/login`
  );

  const { handleLogin, handleSaveID, handleSaveRole } =
    useContext(LoginContext);

  const handleChangeEmail = (event) => {
    setEmail(event.target.value);
  };

  const handleChangePassword = (event) => {
    setPassword(event.target.value);
  };

  const handleForgotPasswordClick = () => {
    setShowButton(true);
  };

  const handleSubmit = async (event) => {
    setMessage("");
    setYes(true);
    event.preventDefault();

    await verifyRole(email);

    const userLogin = await login({ email, password });

    if (errorLogin || !userLogin) {
      return;
    }

    setMessage(userLogin.message);

    if (localStorage.getItem("verify") === "admin") {

      handleLogin(userLogin.data.admin, userLogin.data.token);
      handleSaveRole(userLogin.data.admin.role);
      handleSaveID(userLogin.data.admin.id);
      window.location.href = '/profile';

    } else {

      handleLogin(userLogin.data.user, userLogin.data.token);
      handleSaveRole(userLogin.data.user.role);
      handleSaveID(userLogin.data.user.id);
      window.location.href = "/admin";
    }
    localStorage.removeItem('verify');
  };

  return (
    <form className="formContainer" onSubmit={handleSubmit}>
      <header className="headerWrapper">
        <img
          loading="lazy"
          src="https://cdn.builder.io/api/v1/image/assets/TEMP/3031ae1a0ef5122343cfc046a0fe7378694ac59107f049ac12acaeaba50c1463?placeholderIfAbsent=true&apiKey=dc5b04b1bd644122995a9f89f3c5e2ef"
          className="logo"
          alt="Company logo"
        />
        <h1 className="title">Iniciar sesion</h1>
      </header>

      <div className="formField">
        <label htmlFor="username" className="formLabel">
          Correo
        </label>
        <input
          type="text"
          id="username"
          className="formInput"
          value={email}
          onChange={handleChangeEmail}
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
          onChange={handleChangePassword}
          required
        />
      </div>
      <div className="forgot-password">
        <span
          className="forgot-password-text"
          onClick={handleForgotPasswordClick}
        >
          ¿Olvidaste tu contraseña?
        </span>
        {showButton && (
          <NavLink to={'/recovery'} onClick={() => localStorage.setItem('isRecovery', true)}>
            <button type="button" className="reset-password-button">
              Recuperar contraseña
            </button>
          </NavLink>
        )}
      </div>
      <div className="socialLoginContainer">
        <button type="submit" className=" button google" disabled={loadingLogin}>
          {loadingLogin ? "Iniciando sesion..." : "Iniciar sesion"}
        </button>
      </div>
      {message && <p style={{  color: yes ? 'green' : 'red' }}>{message}</p>}
      {errorLogin && <p style={{ color: "red" }}>{errorLogin}</p>}
    </form>
  );
};

export const LoginForm = () => {
  return (
    <>
      <LoginProvider>
        <LoginFormProvider />
      </LoginProvider>
    </>
  );
};
