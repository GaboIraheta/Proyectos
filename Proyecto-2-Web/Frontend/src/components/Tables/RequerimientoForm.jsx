import React, { useState, useEffect } from "react";
import "./RequerimientoForm.css";

const RequerimientoForm = ({ onSubmit, requerimientoData, isAdding }) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [order, setOrder] = useState("");
  

  useEffect(() => {
    if (requerimientoData) {
      setName(requerimientoData.name);
      setDescription(requerimientoData.description);
      setOrder(requerimientoData.order);
    } else if (isAdding) {
      setName("");
      setDescription("");
      setOrder("");
    }
  }, [requerimientoData, isAdding]);

  const handleSubmit = (e) => {
    e.preventDefault();
    let check = { name, description, order };
    if (!isAdding) {
      check = { ...check, id: requerimientoData._id};
    }
    onSubmit(check);
  };

  return (
    <form className="requerimiento-form" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Nombre"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Descripción"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="Número"
        value={order}
        onChange={(e) => setOrder(e.target.value)}
        required
      />
      <div className="requerimiento-form-buttons">
        <button type="submit">{isAdding ? "Agregar" : "Guardar"}</button>
        <button type="button" onClick={() => onSubmit(null)}>
          Cancelar
        </button>
      </div>
    </form>
  );
};

export default RequerimientoForm;
