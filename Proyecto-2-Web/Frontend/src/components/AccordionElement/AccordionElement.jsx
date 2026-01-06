import React from 'react';
import './AccordionElement.css';
import arrow from '../../assets/arrow.svg';
import openArrow from '../../assets/openarrow.svg';

const AccordionElement = ({ element, setOpenQuestion, openQuestion }) => {
  const { question, answerHeading, answer, id } = element;
  const open = openQuestion === id;

  const handleQuestionClick = (id) => {
    setOpenQuestion(open ? -1 : id); 
  };

  return (
    <div className="accordion-element">
      <button
        className="accordion-element__bar"
        onClick={() => handleQuestionClick(id)}
      >
        <p className="accordion-element__question">{question}</p>
        <img
          src={open ? openArrow : arrow} 
          alt="arrow"
          className="accordion-icon accordion-element__icon"
        />
      </button>
      <div className={`accordion-elemnt__answer ${open ? 'visible' : ''}`}>
        <div className="accordion-element__heading-container">
          <img
            src={openArrow} 
            alt="openarrow"
            className="accordion-icon accordion-element__answer-icon"
            onClick={() => setOpenQuestion(-1)} 
          />
        </div>
        <p className="accordion-element__answer-description">
          {answer}
        </p>
      </div>
    </div>
  );
};

export default AccordionElement;
