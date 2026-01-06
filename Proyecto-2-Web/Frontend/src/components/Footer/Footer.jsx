import React from 'react'
import logo from "../../assets/logo.svg"
import arrow from "../../assets/arrow.svg"
import './Footer.css'

const Footer = () => {
  return (
    <footer className="footer" id='footer'>
      <div className="footer__container">
        <div className="footer__content-left">
          <div className="footer__company-container">
            <img className="footer__logo" src={logo} alt="icon" />
            <h2 className="footer__company">FormCorps</h2>
          </div>
          <div className="footer__input-container">
            
            <img className="footer__input-icon" src={arrow} alt="arrow" />
          </div>
        </div>
        <div className="footer__content-right">
          <div className="footer__link-col">
            <p className="text-reg footer__col-heading">Servicios</p>
            <a href="#" className="text-small footer__link">
              Nosotros
            </a>
            <a href="#" className="text-small footer__link">
              Planes
            </a>
            <a href="#" className="text-small footer__link">
              Precios
            </a>
            <a href="#" className="text-small footer__link">
              Comunidad
            </a>
            <a href="#" className="text-small footer__link">
              FAQs
            </a>
          </div>
          <div className="footer__link-col">
            <p className="text-reg footer__col-heading">Corporacion</p>
            <a href="#" className="text-small footer__link">
              Historia
            </a>
            <a href="#" className="text-small footer__link">
              Equipo
            </a>
            <a href="#" className="text-small footer__link">
             
            </a>
            <a href="#" className="text-small footer__link">
              
            </a>
            <a href="#" className="text-small footer__link">
              
            </a>
          </div>
          <div className="footer__link-col">
            <p className="text-reg footer__col-heading">Recursos</p>
            <a href="#" className="text-small footer__link">
              Blog
            </a>
            <a href="#" className="text-small footer__link">
              Trabajos
            </a>
            <a href="#" className="text-small footer__link">
              
            </a>
            <a href="#" className="text-small footer__link">
              
            </a>
            <a href="#" className="text-small footer__link">
              
            </a>
          </div>
          <div className="footer__link-col">
            <p className="text-reg footer__col-heading">Legal</p>
            <a href="#" className="text-small footer__link">
            Términos de servicio
            </a>
            <a href="#" className="text-small footer__link">
            Política de privacidad
            </a>
            <a href="#" className="text-small footer__link">
            Política de cookies
            </a>
            <a href="#" className="text-small footer__link">
              
            </a>
            <a href="#" className="text-small footer__link">
             
            </a>
          </div>
        </div>
      </div>
      <div className="footer__banner">
        <p className="text-small footer__copywright">
          @2024 FormCorps. All Rights Reserved.
          <a
            href="https://x.com/iamhervewabo"
            className="underline"
            target="_blank"
          >
            @Lorax
          </a>
        </p>
        <div className="footer__external-links">
          <button className="footer__external-link facebook"></button>
          <button className="footer__external-link linkedin"></button>
          <button className="footer__external-link twitter"></button>
          <button className="footer__external-link youtube"></button>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
