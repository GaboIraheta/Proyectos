import React from 'react';
import './ModalImage.css';

const ModalImage = ({ isOpen, imageSrc, onClose }) => {
  if (!isOpen) return null;

  return (
    <div className="modalOverlay" onClick={onClose}>
      <div className="modalContent" onClick={(e) => e.stopPropagation()}>
        <img src={imageSrc} alt="Anexo" className="modalImage" />
        <button className="closeButton" onClick={onClose}>X</button>
      </div>
    </div>
  );
};

export default ModalImage;