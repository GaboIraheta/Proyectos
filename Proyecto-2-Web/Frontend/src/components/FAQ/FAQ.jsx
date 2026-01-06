import React from 'react'
import './FAQ.css'

const FAQ = ({ children }) => {
  return <section className="faq" id='faq'>
    <div className="faq__heading-section">
      <h2 className="faq__heading">
        Preguntas frecuentes
      </h2>
      <p className="faq__description">
        En esta sección encontrarás las respuestas a las preguntas que más suelen hacernos nuestros usuarios.
      </p>
    {children}
    </div>
  </section>
}

export default FAQ
