import logoblanco from "../../assets/logoblanco.svg";
import React, { useContext, useState } from "react";
import "./UserNavBar.css";
import { NavLink } from "react-router-dom";
import useUpdate from "../../Hooks/UseUpdate";
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import config from "../../../config";
import useDelete from "../../Hooks/UseDelete";

const UserNavBarProvider = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isMoreMenuOpen, setIsMoreMenuOpen] = useState(false);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const { user, token, role, logout } = useContext(LoginContext);

  const { updateData, error, loading } = useUpdate(
    `${config.API_URL}/user/update`
  );
  const { deleteUser, error: errorDelete, loading: loadingDelete } = useDelete(`${config.API_URL}/user/delete`)

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const toggleMoreMenu = () => {
    setIsMoreMenuOpen(!isMoreMenuOpen);
  };

  const handleCancelDelete = () => {
    setShowDeleteConfirm(false);
  };

  const handleLogout = async () => {
    setIsLoading(loading);

    const body = {
      checklist: user.checklist,
      form: user.form,
      contract: user.contract,
    };

    const response = await updateData(
      sessionStorage.getItem("id"),
      body,
      token,
      role
    );

    if (error || !response) {
      console.log(error);
      return;
    }
    console.log(response.message);
    logout();
    window.location.href = "/";
  };

  const handleDeleteAccount = async () => {
    setIsLoading(loadingDelete);

    const response = await deleteUser(sessionStorage.getItem('id'), token, role);

    if (errorDelete || !response) return;
    logout();
    window.location.href = '/';
  }

  return (
    <nav className="navbar">
      <NavLink to="/">
        <div className="navbar-logo">
          <img src={logoblanco} alt="Logo" />
        </div>
      </NavLink>

      <div className={`navbar-links ${isMenuOpen ? "active" : ""}`}>
        <NavLink to={"/requirements"}>
          <a href="#">Requerimientos</a>
        </NavLink>
        <NavLink to={"/form"}>
          <a href="#">Ficha</a>
        </NavLink>
        <NavLink to={"/consultants"}>
          <a href="#">Consultores</a>
        </NavLink>
      </div>

      <div className="navbar-icons">
        <NavLink to={"/profile"}>
          <span className="material-icons">person</span>
        </NavLink>
        <span className="material-icons" onClick={handleLogout}>
          exit_to_app
        </span>

        <span className="material-icons" onClick={toggleMoreMenu}>
          more_horiz
        </span>
      </div>

      {isMoreMenuOpen && (
        <div className="more-menu">
          <NavLink to="/change-password" onClick={() => localStorage.setItem('isRecovery', false)}>
            <span className="menu-item-us">Cambiar Contraseña</span>
          </NavLink>
          <NavLink to="/change-credentials">
            <span className="menu-item-us">Cambiar Credenciales</span>
          </NavLink>

          <span
            className="menu-item-us"
            onClick={() => setShowDeleteConfirm(true)}
          >
            Eliminar cuenta
          </span>
        </div>
      )}

      {showDeleteConfirm && (
        <div className="delete-confirmation">
          <div className="confirmation-modal">
            <p>¿Estás seguro que quieres eliminar tu cuenta?</p>
            <div className="confirmation-buttons">
              <button onClick={handleDeleteAccount}>Aceptar</button>
              <button onClick={handleCancelDelete}>Cancelar</button>
            </div>
          </div>
        </div>
      )}

      <div className="navbar-toggle" onClick={toggleMenu}>
        <span className="material-icons">menu</span>
      </div>
    </nav>
  );
};

const UserNavBar = () => {
  return (
    <>
      <LoginProvider>
        <UserNavBarProvider />
      </LoginProvider>
    </>
  );
};

export default UserNavBar;
