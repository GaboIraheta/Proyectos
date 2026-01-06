import React, { useState, useEffect, useContext } from 'react';
import { FormPage } from '../../components/Forms/FormPage';
import useFetch from '../../Hooks/UseFetch';
import API_URL from '../../../config';
import formsone from '../../assets/formsone.jpg';
import formstwo from '../../assets/formstwo.jpg';
import formsthree from '../../assets/formsthree2.0.jpg';
import servicio from '../../assets/servicio.png';
import { FormPage4 } from '../Form/FormPage4';
import './DynamicForm.css';
import { LoginContext, LoginProvider } from '../../Context/LoginContext';
import config from '../../../config';
import { FetchContext, FetchProvider } from '../../Context/FetchContext';
import Unauthorized from '../../components/Unauthorized/Unauthorized';
import useUtil from '../../Hooks/useUtil';
import { Modal, Button, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { RiFontSansSerif } from 'react-icons/ri';

const DynamicFormProvider = () => {

  const { user, token, role, updateUserField, handleLogin } = useContext(LoginContext);
  const { form, handleFetchForm } = useContext(FetchContext);

  const { extractToken } = useUtil();
  const { data, error, loading, isAuth, setIsAuth } = useFetch(`${config.API_URL}/form`, token, role);

  const [currentStep, setCurrentStep] = useState(1);
  const [questions, setQuestions] = useState([]);
  const [responses, setResponses] = useState([]);
  const [validationMessage, setValidationMessage] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {

    if (!isAuth) console.log(user, token);

    if (error) return;

    if (data) {
      handleFetchForm(data.form[0].forms, data.form[0]._id);
    }
  }, [data, error]);

  useEffect(() => {
    if (Array.isArray(form) && form.length > 0 && user) {
      const orderedQuestions = form.sort((a, b) => a.order - b.order);
      setQuestions(orderedQuestions);
      setResponses(new Array(orderedQuestions.length).fill(null).map((item, index) => user.form[index] ? 'cumple' : 'no cumple'));
    }
    console.log(user);
  }, [form])

  const startIndex = (currentStep - 1) * 2;
  const currentQuestions = questions.slice(startIndex, startIndex + 2);

  let imageSrc;
  switch (currentStep) {
    case 1:
      imageSrc = formsone;
      break;
    case 2:
      imageSrc = formstwo;
      break;
    case 3:
      imageSrc = formsthree;
      break;
    case 4:
      imageSrc = servicio;
      break;
    default:
      imageSrc = servicio;
      break;
  }

  const handleResponseChange = (index, response) => {
    console.log(user.form);
    setResponses((prev) => {
      const updatedResponses = [...prev];
      updatedResponses[index] = response;
      updateUserField('form', updatedResponses.map((item) => item === 'cumple'));
      return updatedResponses;
    });
    console.log(user.form);
  };

  const handleNext = () => {
    if (responses.every(response => response !== null)) {
      if (currentStep < 4) {
        setCurrentStep(currentStep + 1);
        setValidationMessage("");
      }
    } else {
      setValidationMessage("Por favor, selecciona una respuesta para todas las preguntas.");
    }
  };

  const handleBack = () => {
    if (currentStep > 1) {
      setCurrentStep(currentStep - 1);
    }
  };

  if (role !== 'user') return <Unauthorized />
  if (loading) return <p>Extrayendo datos de formulario...</p>
  if (!form || form.length === 0)
    return <p>No hay formulario disponible...</p>

  //TODO implementacion temporal de recuperar token
  const generateToken = async () => {
    handleLogin(user, await extractToken(user).token);
    setIsAuth(true);
  }
  
  const handleFinish = () => {
    const hasNoCumple = responses.includes('no cumple');

    if (hasNoCumple) {
      
      setValidationMessage("Aún no cumples con lo necesario, ¡No te rindas!");
      setIsSuccess(false);
      setIsModalOpen(true); 
    } else {
      
      setValidationMessage("Formulario completado exitosamente.");
      setIsSuccess(true); 
      setIsModalOpen(true); 
    }
  };

 
  const handleModalClose = () => {
    setIsModalOpen(false);
    if (isSuccess) {
      
      navigate('/success');  
    } else {
      
      navigate('/form');  
    }
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };  

  return (
    <div className="formContainer-form">
      {currentStep === 4 ? (
        <FormPage4 />
      ) : (
        <FormPage
          steps={[1, 2, 3, 4]}
          currentStep={currentStep}
          imageSrc={imageSrc}
          questions={questions}
          responses={responses}
          onNext={handleNext}
          onBack={handleBack}
          validationMessage={validationMessage}
          onOptionSelect={handleResponseChange}
          onFinish={handleFinish}
        />
      )}
      {error && <p>{error}</p>}
      {!isAuth && <button onClick={generateToken}>Obtener nueva autenticación</button>}
      <Modal
        open={isModalOpen}
        onClose={handleCloseModal}
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          backdropFilter: 'blur(5px)',  
        }}
      >
        <Box
          sx={{
            backgroundColor: 'white',
            padding: 3,
            borderRadius: 2,
            boxShadow: 24,
            minWidth: '300px', 
            textAlign: 'center',
            fontFamily: 'Raleway'
          }}
        >
          <h2>{isSuccess ? 'Felicidades' : '¡Hora de mejorar!'}</h2>
          <p>{validationMessage}</p>

          <Button
            sx={{
              backgroundColor: '#26364E',
              color: 'white',
              '&:hover': {
                backgroundColor: '#1E2B3A',
              },
              marginTop: 2,
              marginRight: 1,
            }}
            variant="contained"
            onClick={() => {
              handleCloseModal();
              if (isSuccess) {
                navigate('/profile'); 
              } else {
                navigate('/profile'); 
              }
            }}
          >
            {isSuccess ? 'Continuar' : 'Regresar'}
          </Button>
        </Box>
      </Modal>
    </div>
  );
};

const DynamicForm = () => {
  return (
    <> 
      <LoginProvider>
        <FetchProvider>
          <DynamicFormProvider/>
        </FetchProvider>
      </LoginProvider>
    </>
  )
}

export default DynamicForm;
