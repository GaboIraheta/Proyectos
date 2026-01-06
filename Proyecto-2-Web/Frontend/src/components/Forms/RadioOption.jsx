import React from 'react';
import './RadioOption.css';

export const RadioOption = ({ label, isSelected, onClick }) => {
  return (
    <div className="radioContainer" onClick={onClick}>
      <div className="radioWrapper">
        <div className={`radio ${isSelected ? 'radioSelected' : ''}`}>
          {isSelected && <div className="dot" />}
        </div>
      </div>
      <div className="radioLabel">{label}</div>
    </div>
  );
};
