import React, { useContext, useEffect, useState } from "react";
import { FetchContext, FetchProvider } from "../../Context/FetchContext";
import RequerimientoTable from "../../components/Tables/RequerimientoTable";
import RequerimientoForm from "../../components/Tables/RequerimientoForm";
import useFetch from "../../Hooks/UseFetch";
import usePost from "../../Hooks/UsePost";
import useDelete from "../../Hooks/UseDelete";
import useUpdate from "../../Hooks/UseUpdate";
import API_URL from "../../../config";
import './RequerimientosPage.css';
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import config from '../../../config';
import Unauthorized from "../../components/Unauthorized/Unauthorized";
import AdminNavBar from "../../components/Navigation/AdminNavBar";
import Footer from "../../components/Footer/Footer";

const RequerimientosPageProvider = () => {

  const { token, role } = useContext(LoginContext);
  const { checklist, handleFetchChecklist, getID } = useContext(FetchContext);

  const { data, error: errorFetch, loading: loadingFetch } = useFetch(`${config.API_URL}/checklist/`, token, role);
  const { postData, error: errorPost, loading: loadingPost } = usePost(`${config.API_URL}/checklist/add`);
  const { updateData, error: errorUpdate, loading: loadingUpdate } = useUpdate(`${config.API_URL}/checklist/update`);
  const { deleteData, error: errorDelete, loading: loadingDelete } = useDelete(`${config.API_URL}/checklist/delete`);

  const [selectedRequerimiento, setSelectedRequerimiento] = useState(null);
  const [isAdding, setIsAdding] = useState(false);
  const [isLoading, setIsLoading] = useState(false); 

  useEffect(() => {

    if (!loadingFetch) setIsLoading(loadingFetch);

    if (errorFetch) return;

    if (data) {
      handleFetchChecklist(data.checklist[0]?.checks, data.checklist[0]._id);
    }
  }, [data, errorFetch, loadingFetch]);

  const handleAdd = () => {
    setSelectedRequerimiento(null);
    setIsAdding(true);
  };

  const handleSave = async (requerimiento) => {
    
    let body = {
      name: requerimiento.name,
      description: requerimiento.description,
      order: requerimiento.order
    };

    if (isAdding) {
      setIsLoading(loadingPost);

      const response = await postData(getID('checklistID'), body, token, role);

      if (errorPost || !response)
        return;

      console.log(response.message);
      handleFetchChecklist([...checklist, response.data], getID('checklistID'));
    } else {
      setIsLoading(loadingUpdate);

      console.log(requerimiento.id);
      body = {
        ...body,
        _id: requerimiento.id
      };

      const response = await updateData(getID('checklistID'), body, token, role);

      if (errorUpdate || !response) return;

      console.log(response.message);
      handleFetchChecklist(response.data.checks, getID('checklistID'));
    }
    setSelectedRequerimiento(null);
    setIsAdding(false);
  };

  const handleEdit = (requerimiento) => {
    setSelectedRequerimiento(requerimiento);
    setIsAdding(false);
  };

  const handleDelete = async (id) => {
    setIsLoading(loadingDelete);

    const body = {
      checksID: id
    };

    const response = await deleteData(getID('checklistID'), body, token, role);

    if (errorDelete || !response) return;

    console.log(response.message);
    handleFetchChecklist(response.data.checks, getID('checklistID'));
  };

  if (role !== 'admin') return <Unauthorized/>
  if (isLoading) return <div>Cargando...</div>;
  if (errorFetch) return (
    <>
      <AdminNavBar/>
      <p>[errorFetch]</p>
      <Footer/>
    </>
  );

  return (
    <div className="requerimientos-page">
      <div className="requerimientos-header">
        <h1 className="requerimientos-title">Requerimientos</h1>
        <div className="button-container">
          <button className="requerimientos-add-button" onClick={handleAdd}>
            Agregar Requerimiento
          </button>
        </div>
      </div>
      {(isAdding || selectedRequerimiento) && (
        <RequerimientoForm
          onSubmit={handleSave}
          requerimientoData={selectedRequerimiento}
          isAdding={isAdding}
        />
      )}
      {errorPost && <p style={{ color: 'red' }}>{errorPost}</p>}
      {errorUpdate && <p style={{ color: 'red' }}>{errorUpdate}</p>}
      {errorDelete && <p style={{ color: 'red' }}>{errorDelete}</p>}

      <div className="table-container">
        <RequerimientoTable
          requerimientos={checklist|| []}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

const RequerimientosPage = () => {
  return (
    <>
      <LoginProvider>
        <FetchProvider>
          <AdminNavBar/>
          <RequerimientosPageProvider/>
          <Footer/>
        </FetchProvider>
      </LoginProvider>
    </>
  )
}

export default RequerimientosPage;
