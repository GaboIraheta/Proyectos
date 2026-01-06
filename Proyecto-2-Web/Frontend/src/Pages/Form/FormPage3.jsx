import React from 'react';
import { FormPage } from '../../components/Forms/FormPage';

export const FormPage3 = () => {
  const questions = [
    "El área de preparación cumple con las normas de higiene.",
    "Techos están en buen estado. Adecuado mantenimiento. No acumulan suciedad. Sin filtración de agua, goteras ni desprendimiento de partículas.",
  ];

  return (
    <FormPage
      steps={[1, 2, 3, 4]} 
      currentStep={3} // Paso actual: 3
      imageSrc="https://cdn.builder.io/api/v1/image/assets/TEMP/2657867daa526bb635a5a8ce02d5e2eb7752c562ba45652bb3d62246c2914267" 
      questions={questions} 
      onNext={() => window.location.href = "/form/page4"} 
      onBack={() => window.location.href = "/form/page2"} 
    />
  );
};



