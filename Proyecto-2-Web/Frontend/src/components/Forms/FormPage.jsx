import React from 'react';
import './FormPage.css';
import { ProgressSteps } from './ProgressSteps';
import { InspectionItem } from './InspectionItem';

export const FormPage = ({ steps, currentStep, imageSrc, questions, responses, onNext, onBack, validationMessage, onOptionSelect, onFinish }) => {
  return (
    <div className="pageContainer">
      <main className="formContainer">
        <ProgressSteps currentStep={currentStep} />

        <div className="imageContainer">
          <img src={imageSrc} alt="Inspection area" className="inspectionImage" />
        </div>

        <div className="questionsContainer">
          {questions.map((question, index) => (
            <InspectionItem
              key={index}
              description={question.question}
              selectedOption={responses[index]}
              onOptionSelect={(response) => onOptionSelect(index, response)}
              image={question.image}
            />
          ))}
        </div>

        {validationMessage && <div className="validationMessage">{validationMessage}</div>}

        <div className="finishedbtn">
          <button className="finbtn" onClick={onFinish}>Terminar</button>
        </div>
      </main>
    </div>
  );
};