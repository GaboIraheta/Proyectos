import React from 'react';
import ServiceCard from './ServiceCard';
import './Services.css';
import serviceone from '../../assets/fondo.jpg';
import servicetwo from '../../assets/serviciouno.jpg';
import servicethree from '../../assets/serviciotres.jpg';

const Services = () => {
  return (
    <div className="service-section" id='services'>
      <h2>Servicios</h2>
      <div className="service">
        <ServiceCard
          image={serviceone}
          name="Seguimiento"
          description="Guías detalladas paso a paso"
          hoverDescription="Te orientamos sobre las normativas y regulaciones vigentes, asegurando que tu negocio cumpla con todos los estándares requeridos para obtener el permiso sin contratiempos."
          hoverDirection="left" 
        />
        <ServiceCard
          image={servicetwo}
          name="Asesoría"
          description="Contacto con expertos"
          hoverDescription="Ayudamos a preparar y organizar toda la documentación necesaria, garantizando que cumpla con los requisitos establecidos por la Superintendencia de Regulación Sanitaria (SRS)."
          hoverDirection="bottom" 
        />
        <ServiceCard
          image={servicethree}
          name="Empleo"
          description="Apoyando a otros profesionales"
          hoverDescription="Puedes contratar a nuestro equipo de expertos en regulación sanitaria y permisos de funcionamiento para un seguimiento más personalizado."
          hoverDirection="right" 
        />
      </div>
    </div>
  );
};

export default Services;
