import React, { useContext } from "react";
import { useEffect, useState } from "react";
import "./Requirements.css";
import useFetch from "../../Hooks/UseFetch";
import { FetchContext, FetchProvider } from "../../Context/FetchContext";
import config from "../../../config";
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import Unauthorized from "../Unauthorized/Unauthorized";
import { NavLink } from "react-router-dom";

const RequirementsTableProvider = () => {
  const { user, token, role, updateUserField } = useContext(LoginContext);
  const { data, error, loading } = useFetch(
    `${config.API_URL}/checklist/`,
    token,
    role
  );
  const { checklist, handleFetchChecklist } = useContext(FetchContext);

  const [requerimientos, setRequerimientos] = useState([]);

  useEffect(() => {
    if (error) return;

    if (data) {
      handleFetchChecklist(data.checklist[0].checks, data.checklist[0]._id);
    }
  }, [data, error]);

  useEffect(() => {
    if (
      Array.isArray(checklist) &&
      checklist.length > 0 &&
      user &&
      Array.isArray(user.checklist)
    ) {
      setRequerimientos(
        checklist.map((item, index) => ({
          id: item.order,
          name: item.description,
          completed: user.checklist[index],
        }))
      );
    }
  }, [checklist]);

  // Realiza la actualización de los checkbox
  const handleCheckboxChange = (id) => {
    setRequerimientos((prev) => {
      const updatedRequerimientos = prev.map((req) =>
        req.id === id ? { ...req, completed: !req.completed } : req
      );

      updateUserField(
        "checklist",
        updatedRequerimientos.map((req) => req.completed)
      );
      return updatedRequerimientos;
    });
  };

  // Hace el cálculo del progreso
  const totalRequerimientos = requerimientos.length;
  const completados = requerimientos.filter((req) => req.completed).length;
  const porcentajeCompletado = Math.round(
    (completados / totalRequerimientos) * 100
  );

  if (role !== "user") return <Unauthorized />;
  if (loading) return <p>Extrayendo datos de requerimientos...</p>;

  if (error) return <p>{error}</p>;

  if ((!checklist || checklist.length === 0)) 
    return <p>No hay requerimientos disponibles...</p>

  return (
    <section className="requirement-section">
      <div className="header">
        <h2>Requerimientos</h2>
      </div>
      <table className="requirements-table">
        <thead>
          <tr>
            <th className="firstTh">Completado</th>
            <th>No.</th>
            <th>Nombre</th>
          </tr>
        </thead>
        <tbody>
          {requerimientos.map(({ id, name, completed }) => (
            <tr key={id}>
              <td>
                <label className="container">
                  <input
                    type="checkbox"
                    checked={completed}
                    onChange={() => handleCheckboxChange(id)}
                  />
                  <div className="checkmark"></div>
                </label>
              </td>
              <td>{id}</td>
              <td>{name}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="progress-container">
        <p>{porcentajeCompletado}% Para completar</p>
        <progress
          value={porcentajeCompletado}
          max="100"
          id="progress-bar"
          className={porcentajeCompletado === 100 ? "complete" : ""}
        ></progress>
      </div>
      <p className="p">
        <NavLink
          to={"/profile"}
          style={{ textDecoration: "none", color: "inherit" }}
        >
          ← Regresar
        </NavLink>
      </p>
      <p>{!error ? "" : error}</p>
    </section>
  );
};

const RequirementsTable = () => {
  return (
    <>
      <LoginProvider>
        <FetchProvider>
          <RequirementsTableProvider />
        </FetchProvider>
      </LoginProvider>
    </>
  );
};

export default RequirementsTable;
