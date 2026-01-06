import React from 'react';
import './ProgressSteps.css';

export const ProgressSteps = ({ currentStep }) => {
  const steps = [1, 2, 3, 4]; 

  return (
    <div className="stepsContainer">
      {steps.map((step, index) => (
        <React.Fragment key={index}>
          {}
          <div className={`step ${currentStep >= step ? 'activeStep' : ''}`}>
            {step}
          </div>
          {}
          {index < steps.length - 1 && (
            <div
              className={`connector ${
                currentStep > step ? 'activeConnector' : ''
              }`}
            />
          )}
        </React.Fragment>
      ))}
    </div>
  );
};





