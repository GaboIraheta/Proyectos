import React from 'react';
import './About.css';
import aboutImage from '../../assets/about.jpg';  

const About = () => {
  return (
    <section className="about" id='about'>
      <div className="about__content">
        <p className="about__tagline">Tu socio en la formalización empresarial</p>
        <h2 className="about__title">Acerca de Nosotros</h2>
        <p className="about__description">
          En FormCorps, nos dedicamos a facilitar el proceso de formalización de negocios en la industria alimentaria. Nuestra plataforma ha sido diseñada pensando en las necesidades de los emprendedores, ofreciendo un servicio eficiente y de calidad que guía paso a paso a los usuarios a través de todos los requerimientos necesarios para obtener los permisos de funcionamiento.
        </p>
      </div>

      <div className="about__image-container">
        <img src={aboutImage} alt="About Us" className="about__image" />
      </div>
    </section>
  );
};

export default About;
