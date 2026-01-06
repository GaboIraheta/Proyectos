import React from 'react';
import { FormPage } from '../../components/Forms/FormPage';

export const FormPage2 = () => {
  const questions = [
    "El equipo se encuentra en condiciones óptimas de funcionamiento.",
    "Pisos de materiales que no contaminan los alimentos, facilitanprocesos de limpieza y sanitización, sin daños ni grietas.",
  ];

  return (
    <FormPage
      steps={[1, 2, 3, 4]} 
      currentStep={2} // Paso actual: 2
      imageSrc="https://cdn.builder.io/api/v1/image/assets/TEMP/2657867daa526bb635a5a8ce02d5e2eb7752c562ba45652bb3d62246c2914267" 
      questions={questions} 
      onNext={() => window.location.href = "/form/page3"} 
      onBack={() => window.location.href = "/form/page1"} 
    />
  );
};



