import React, {useState} from 'react'
import './Accordion.css'
import '../AccordionElement/AccordionElement'
import { faqElement } from '../../Utils/constants'
import AccordionElement from '../AccordionElement/AccordionElement'

const Accordion = () => {
  const[openQuestion,setOpenQuestion] = useState(-1);
  return (
    <div className="accordion">
     {faqElement.map((element) => {
        return (
          <AccordionElement
            element={element}
            key={element.id}
            setOpenQuestion={setOpenQuestion}
            openQuestion={openQuestion}
          />
        );
      })}
    </div>
  )
}

export default Accordion
