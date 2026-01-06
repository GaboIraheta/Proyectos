import logoazul from '../../assets/logoazul.svg';
import React, { useState } from 'react';
import { Link, NavLink } from 'react-router-dom';
import './Navigation.css';

const Navigation = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen);
    };

    return (
        <nav className="navbar-custom">
            <div className="navbar-logo-custom">
                <Link className="Link" to="/">
                    <img src={logoazul} alt="Logo" className="logo-img" />
                </Link>
            </div>

            <ul className={`navbar-links-custom ${isMenuOpen ? 'active' : ''}`}>
                <li className="NavElements">
                    <Link className="Link" to="/#services">
                        Servicios
                    </Link>
                </li>
                <li className="NavElements">
                    <NavLink className="Link" to="/#about">
                        Sobre nosotros
                    </NavLink>
                </li>
                <li className="NavElements">
                    <NavLink className="Link" to="/#faq">
                        Preguntas
                    </NavLink>
                </li>
                <li className="NavElements">
                    <NavLink className="Link" to="/#footer">
                        Contacto
                    </NavLink>
                </li>
            </ul>

            <ul className={`navbar-buttons-custom ${isMenuOpen ? 'active' : ''}`}>
                <li>
                    <NavLink className="LinkButton" to="/login">
                        <button className="NavButtom">Iniciar sesión</button>
                    </NavLink>
                </li>
                <li>
                    <NavLink className="LinkButton registerbutt" to="/register">
                        <button className="NavButtom">Registrarse</button>
                    </NavLink>
                </li>
            </ul>

            <div className="navbar-toggle-custom" onClick={toggleMenu}>
                <span className="material-icons">menu</span>
            </div>
        </nav>
    );
};

export default Navigation;
