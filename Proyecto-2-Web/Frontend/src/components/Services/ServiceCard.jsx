import React, { useState } from 'react';
import VisibilitySensor from 'react-visibility-sensor';
import './ServiceCard.css';

const ServiceCard = (props) => {
  const [isVisible, setIsVisible] = useState(false);

  
  const handleVisibilityChange = (isVisible) => {
    setIsVisible(isVisible);
  };

  return (
    <VisibilitySensor onChange={handleVisibilityChange} partialVisibility>
      <div
        className={`service_box ${isVisible ? 'visible' : ''}`}
      >
        <img src={props.image} alt={props.name} />
        <div className={`overlay ${props.hoverDirection}`}>
          <p>{props.hoverDescription}</p>
        </div>
        <div className="description">
          <h3>{props.name}</h3>
          <p className="para">{props.description}</p>
        </div>
      </div>
    </VisibilitySensor>
  );
};

export default ServiceCard;
