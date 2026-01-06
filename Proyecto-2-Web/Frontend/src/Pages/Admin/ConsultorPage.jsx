import React, { useContext, useEffect, useState } from "react";
import { FetchContext, FetchProvider } from "../../Context/FetchContext";
import ConsultorTable from "../../components/Tables/ConsultorTable";
import ConsultorForm from "../../components/Tables/ConsultorForm";
import useFetch from "../../Hooks/UseFetch";
import usePost from "../../Hooks/UsePost";
import useDelete from "../../Hooks/UseDelete";
import useUpdate from "../../Hooks/UseUpdate";
import config from "../../../config";
import "./ConsultorPage.css";
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import Unauthorized from "../../components/Unauthorized/Unauthorized";
import Navigation from "../../components/Navigation/Navigation";
import Footer from "../../components/Footer/Footer";
import { NavLink } from "react-router-dom";
import "./ConsultorPage.css";
import AdminNavBar from "../../components/Navigation/AdminNavBar";

const ConsultantPageProvider = () => {
  const { token, role } = useContext(LoginContext);
  const { consultants, handleFetchConsultants, getID } =
    useContext(FetchContext);

  const {
    data,
    error: errorFetch,
    loading: loadingFetch,
  } = useFetch(`${config.API_URL}/consultant/`, token, role);
  const {
    postData,
    error: errorAdd,
    loading: loadingAdd,
  } = usePost(`${config.API_URL}/consultant/add`);
  const {
    updateData,
    error: errorUpdate,
    loading: loadingUpdate,
  } = useUpdate(`${config.API_URL}/consultant/update`);
  const {
    deleteData,
    error: errorDelete,
    loading: loadingDelete,
  } = useDelete(`${config.API_URL}/consultant/delete`);

  const [selectedConsultor, setSelectedConsultor] = useState(null);
  const [isAdding, setIsAdding] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (!loadingFetch) setIsLoading(loadingFetch);

    if (errorFetch) return;

    if (data) {
      handleFetchConsultants(
        data.consultants[0]?.consultants,
        data.consultants[0]._id
      );
    }
  }, [data, errorFetch, loadingFetch]);

  const handleAdd = () => {
    setSelectedConsultor(null);
    setIsAdding(true);
  };

  const handleSave = async (consultor) => {
    let body = {
      username: consultor.username,
      email: consultor.email,
      phone: consultor.phone,
      price: consultor.price,
    };

    if (isAdding) {
      setIsLoading(loadingAdd);

      const response = await postData(
        getID("consultantsID"),
        body,
        token,
        role
      );

      if (errorAdd || !response) return;

      console.log(response.message);
      handleFetchConsultants(
        [...consultants, response.data],
        getID("consultantsID")
      );
    } else {
      setIsLoading(loadingUpdate);

      body = {
        ...body,
        _id: consultor.id,
      };

      const response = await updateData(
        getID("consultantsID"),
        body,
        token,
        role
      );

      if (errorUpdate || !response) return;

      console.log(response.message);
      handleFetchConsultants(
        response.consultants.consultants,
        getID("consultantsID")
      );
    }
    setSelectedConsultor(null);
    setIsAdding(false);
  };

  const handleEdit = (consultor) => {
    setSelectedConsultor(consultor);
    setIsAdding(false);
  };

  const handleDelete = async (id) => {
    setIsLoading(loadingDelete);

    const body = {
      consultantID: id,
    };

    const response = await deleteData(
      getID("consultantsID"),
      body,
      token,
      role
    );

    if (errorDelete || !response) return;

    console.log(response.message);
    console.log(response.data);
    handleFetchConsultants(response.data.consultants, getID("consultantsID"));
  };

  if (role !== "admin")
    return (
      <>
        <Navigation />
        <Unauthorized />
        <Footer />
      </>
    );
  if (isLoading) return <div>Cargando...</div>;

  if (errorFetch)
    return (
      <>
        <Navigation />
        <Unauthorized />
        <Footer />
      </>
    );

  return (
    <>
      <AdminNavBar />
      <div className="consultant-page">
        <div className="consultor-header">
          <h1 className="consultor-title">Consultores</h1>
          <div className="button-container">
            <button className="consultor-add-button" onClick={handleAdd}>
              Agregar Consultor
            </button>
          </div>
        </div>
        {(isAdding || selectedConsultor) && (
          <ConsultorForm
            onSubmit={handleSave}
            consultorData={selectedConsultor}
            isAdding={isAdding}
          />
        )}
        {errorAdd && <p style={{ color: "red" }}>{errorAdd}</p>}
        {errorUpdate && <p style={{ color: "red" }}>{errorUpdate}</p>}
        {errorDelete && <p style={{ color: "red" }}>{errorDelete}</p>}

        <div className="table-container">
          <ConsultorTable
            consultants={consultants || []}
            onEdit={handleEdit}
            onDelete={handleDelete}
          />
        </div>
        <div className="goBack-container">
          <NavLink to="/admin">
            <button className="goBack">Regresar</button>
          </NavLink>
        </div>
      </div>
      <Footer />
    </>
  );
};

const ConsultantPage = () => {
  return (
    <>
      <LoginProvider>
        <FetchProvider>
          <ConsultantPageProvider />
        </FetchProvider>
      </LoginProvider>
    </>
  );
};

export default ConsultantPage;
