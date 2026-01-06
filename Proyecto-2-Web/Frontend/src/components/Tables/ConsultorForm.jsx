import React, { useState, useEffect } from "react";
import "./ConsultorForm.css";

const ConsultorForm = ({ onSubmit, consultorData, isAdding }) => {
  const [username, setUsername] = useState("");
  const [price, setPrice] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");

  useEffect(() => {
    if (consultorData) {
      setUsername(consultorData.username);
      setPrice(consultorData.price);
      setEmail(consultorData.email);
      setPhone(consultorData.phone);
    } else {
      if (isAdding) {
        setUsername("");
        setPrice("");
        setEmail("");
        setPhone("");
      }
    }
  }, [consultorData, isAdding]);

  const handleSubmit = (e) => {
    e.preventDefault();
    let consultor = { username, price, email, phone };
    if (!isAdding) {
      consultor = { ...consultor, id: consultorData._id};
    }
    onSubmit(consultor);
  };

  return (
    <form className="consultor-form" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Nombre"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Precio"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
        required
      />
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />
      <input
        type="tel"
        placeholder="Teléfono"
        value={phone}
        onChange={(e) => setPhone(e.target.value)}
        required
      />
      <div className="consultor-form-buttons">
        <button type="submit">{isAdding ? "Agregar" : "Guardar"}</button>
        <button type="button" onClick={() => onSubmit(null)}>
          Cancelar
        </button>
      </div>
    </form>
  );
};

export default ConsultorForm;
