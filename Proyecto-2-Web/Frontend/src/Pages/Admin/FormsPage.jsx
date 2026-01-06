import React, { useContext, useEffect, useState } from "react";
import { FetchContext, FetchProvider } from "../../Context/FetchContext";
import FormsTable from "../../components/Tables/FormsTable";
import FormsForm from "../../components/Tables/FormsForm";
import API_URL from "../../../config";
import useFetch from "../../Hooks/UseFetch";
import Unauthorized from "../../components/Unauthorized/Unauthorized";
import './FormsPage.css';
import Navigation from "../../components/Navigation/Navigation";
import Footer from "../../components/Footer/Footer";
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import config from "../../../config";
import usePost from "../../Hooks/UsePost";
import useUpdate from "../../Hooks/UseUpdate";
import useDelete from "../../Hooks/UseDelete";
import AdminNavBar from "../../components/Navigation/AdminNavBar";

const FormsPageProvider = () => {

  const { token, role } = useContext(LoginContext);
  const { form, handleFetchForm, getID } = useContext(FetchContext);

  const { data, error: errorFetch, loading: loadingFetch } = useFetch(`${config.API_URL}/form/`, token, role);
  const { postData, error: errorPost, loading: loadingPost } = usePost(`${config.API_URL}/form/add`);
  const { updateData, error: errorUpdate, loading: loadingUpdate } = useUpdate(`${config.API_URL}/form/update`);
  const { deleteData, error: errorDelete, loading: loadingDelete } = useDelete(`${config.API_URL}/form/delete`);

  const [selectedForm, setSelectedForm] = useState(null);
  const [isAdding, setIsAdding] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {

    if (!loadingFetch) setIsLoading(loadingFetch);

    if (errorFetch) return;

    if (data) {
      handleFetchForm(data.form[0]?.forms, data.form[0]._id);
    }
  }, [data, errorFetch, loadingFetch]);

  const handleAdd = () => {
    setSelectedForm(null);
    setIsAdding(true);
  };

  const handleSave = async (formItem) => {
    
    let body = {
      question: formItem.description,
      image: formItem.image,
      order: formItem.order
    };

    console.log(body);

    if (isAdding) {
      setIsLoading(loadingPost);

      const response = await postData(getID('formID'), body, token, role);

      if (errorPost || !response) return;

      console.log(response.message);
      console.log(response.data);
      console.log(getID('formID'));
      handleFetchForm([...form, response.data], getID('formID'));
    } else {
      setIsLoading(loadingUpdate);

      body = {
        ...body,
        _id: formItem.id
      };

      const response = await updateData(getID('formID'), body, token, role);

      if (errorUpdate || !response) return;

      console.log(response.message);
      console.log(response.data);
      handleFetchForm(response.data.forms, getID('formID'));
    }
    setSelectedForm(null);
    setIsAdding(false);
  };

  const handleEdit = (formItem) => {
    setSelectedForm(formItem);
    setIsAdding(false);
  };

  const handleDelete = async (id) => {
    setIsLoading(loadingDelete);

    const body = {
      formsID: id
    };

    const response = await deleteData(getID('formID'), body, token, role);

    if (errorDelete || !response) return;

    console.log(response.message);
    console.log(response.data);
    handleFetchForm(response.data.forms, getID('formID'));
  };

  if (role !== 'admin') return <Unauthorized/>
  if (isLoading) return <div>Cargando...</div>;
  if (errorFetch) return (
    <>
      <AdminNavBar/>
      <Unauthorized/>
      <Footer/>
    </>
  );

  return (
    <>
      <AdminNavBar/>
      <div className="forms-page">
      <div className="forms-header">
        <h1 className="forms-title">Preguntas</h1>
        <div className="button-container">
          <button className="forms-add-button" onClick={handleAdd}>
            Agregar Pregunta
          </button>
        </div>
      </div>
      {(isAdding || selectedForm) && (
        <FormsForm
          onSubmit={handleSave}
          formData={selectedForm}
          onCancel={() => setSelectedForm(null)}
          isAdding={isAdding}
        />
      )}
      {errorPost && <p style={{ color: 'red' }}>{errorPost}</p>}
      {errorUpdate && <p style={{ color: 'red' }}>{errorUpdate}</p>}
      {errorDelete && <p style={{ color: 'red' }}>{errorDelete}</p>}
      <div className="table-container">
        <FormsTable
          forms={form || []}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
    <Footer/>
    </>
  );
};

const FormsPage = () => {
  return (
    <>
      <LoginProvider>
        <FetchProvider>
          <FormsPageProvider/>
        </FetchProvider>
      </LoginProvider>
    </>
  )
}

export default FormsPage;
