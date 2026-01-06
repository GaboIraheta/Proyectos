import React from 'react';
import './FormCompleted.css';

export const FormPage4 = () => {
  return (
    <div className="completedPageContainer">
      <div className="completedFormContainer">
        {}
        <div className="stepsContainer">
          <div className="step activeStep">1</div>
          <div className="connector activeConnector"></div>
          <div className="step activeStep">2</div>
          <div className="connector activeConnector"></div>
          <div className="step activeStep">3</div>
          <div className="connector activeConnector"></div>
          <div className="step activeStep">4</div>
        </div>

        {}
        <div className="completionMessage">
          <div className="completionIcon">
            <span className="checkmark">✔</span>
          </div>
          <h1 className="completionTitle">¡Completado!</h1>
        </div>

        {}
        <div className="navigationButtons">
          <button
            className="backButton"
            onClick={() => window.location.href = "/form/page3"}
          >
            Regresar
          </button>
          <button
            className="finishButton"
            onClick={() => {
              alert("Formulario enviado con éxito.");
              window.location.href = "/profile";
            }}
          >
            Subir
          </button>
        </div>
      </div>
    </div>
  );
};
