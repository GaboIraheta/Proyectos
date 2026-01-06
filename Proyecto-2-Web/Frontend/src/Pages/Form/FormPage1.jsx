import React from 'react';
import { FormPage } from '../../components/Forms/FormPage';

export const FormPage1 = () => {
  const questions = [
    "Situado en zonas o lugares no expuestos a contaminación.",
    "Alrededores y áreas exteriores están limpios, libres de maleza, estancamiento de agua, promontorios de basura y polvo.",
    
  ];

  return (
    <FormPage
      steps={[1, 2, 3]} 
      currentStep={1} 
      imageSrc="https://cdn.builder.io/api/v1/image/assets/TEMP/2657867daa526bb635a5a8ce02d5e2eb7752c562ba45652bb3d62246c2914267" 
      questions={questions} 
      onNext={() => window.location.href = "/form/page2"} 
      onBack={null} 
    />
  );
};




  






