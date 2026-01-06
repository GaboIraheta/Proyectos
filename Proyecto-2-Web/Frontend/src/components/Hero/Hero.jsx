import React from 'react';
import './Hero.css';
import logo from '../../assets/logo.svg';
import logoazul from '../../assets/logoazul.svg';
import logoblanco from '../../assets/logoblanco.svg';
import fondo from '../../assets/servicio.png'


const Hero = () => {
  return (
    <section className="hero">
      <div className="hero-content">
        <h1 className="hero-title">
          F
          <span className="icon-wrapper">
            <img src={logoazul} alt="logo" className="apple-icon apple-icon-blue" />

          </span>
          RMC
          <span className="icon-wrapper" >
            <img src={logoblanco} alt="logo" className="apple-icon apple-icon-white" />

          </span>
          <span className="rps-highlight">RPS</span>
        </h1>
        <h2 className='title-responsive'>FORM COPRS</h2>
      </div>
      <svg
        className="hero-background"
        width="100%"
        height="100%"
        viewBox="0 0 1394 974"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
        preserveAspectRatio="xMidYMid slice"
      >
        
        <path
          d="M765.762 434.802C868.238 162.231 1065.51 161.62 1237.06 149.739C1408.61 137.858 1392.85 -126.414 1393.92 -150.203V-157C1393.92 -157 1394.11 -154.442 1393.92 -150.203V974L-48 961C105.017 365.07 588.275 906.896 765.762 434.802Z"
          fill="#26364E"
        />
      </svg>
    </section>
  );
};

export default Hero;
