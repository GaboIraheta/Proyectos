import React, { useContext, useState } from "react";
import { FaBars } from "react-icons/fa";
import "./AdminNavBar.css";
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import logoblanco from "../../assets/logoblanco.svg";
import { Link, NavLink } from "react-router-dom";
import "./AdminNavBar.css";

const AdminNavBarProvider = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isMoreMenuOpen, setIsMoreMenuOpen] = useState(false);

  const { logout } = useContext(LoginContext);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const toggleMoreMenu = () => {
    setIsMoreMenuOpen(!isMoreMenuOpen);
  };

  const handleLogout = () => {
    logout();
    window.location.href = "/"
  }

  return (
    <nav className="navbar-admin">
      <NavLink to={"/"}>
        <div className="navbar-logo">
          <img src={logoblanco} alt="Logo" />
        </div>
      </NavLink>

      <ul className={`navbar-links ${isMenuOpen ? "active" : ""}`}>
        <li>
          <Link to="/admin/users">Usuarios</Link>
        </li>
        <li>
          <Link to="/admin/consultants">Consultores</Link>
        </li>
        <li>
          <Link to="/admin/formPage">Formularios</Link>
        </li>
        <li>
          <Link to="/admin/checklistPage">Checklists</Link>
        </li>
      </ul>

      {/* esto lo vamos a cambiar por la imagen del admin? -Ño*/}
      {/* <div className="profile-icon">
          <span onClick={logout}>👤</span> */}
      <div className="navbar-icons">
        <div className="navbar-user">
          <Link to={"/admin"}><span className="material-icons">person</span></Link>
        </div>
        <span className="material-icons" onClick={handleLogout}>
          exit_to_app
        </span>

        <span className="material-icons" onClick={toggleMoreMenu}>
          more_horiz
        </span>
      </div>

      {isMoreMenuOpen && (
        <div className="more-menu">
          <NavLink to="/admin/change-password" onClick={() => localStorage.setItem('isRecovery', false)}>
            <span className="menu-item-us">Cambiar Contraseña</span>
          </NavLink>
          <NavLink to="/admin/change-credentials">
            <span className="menu-item-us">Cambiar Credenciales</span>
          </NavLink>
        </div>
      )}

      <div className="navbar-toggle" onClick={toggleMenu}>
        <span className="material-icons">menu</span>
      </div>
    </nav>
  );
};

const AdminNavBar = () => {
  return (
    <>
      <LoginProvider>
        <AdminNavBarProvider />
      </LoginProvider>
    </>
  );
};

export default AdminNavBar;
