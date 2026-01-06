import React, { useState, useEffect } from "react";
import "./FormsForm.css";

const FormsForm = ({ onSubmit, formData, onCancel, isAdding }) => {
  const [description, setDescription] = useState("");
  const [image, setImage] = useState("");
  const [order, setOrder] = useState("");

  useEffect(() => {
    if (formData) {
      setDescription(formData.question);
      setOrder(formData.order);
      setImage(formData.image);
    } else if (isAdding) {
      setDescription("");
      setOrder("");
      setImage("");
    }
  }, [formData, isAdding]);

  const handleSubmit = (e) => {
    e.preventDefault();
    let form = null;
    console.log(formData);
    if (!image) {
      form = { description, image: "Imagen no disponible", order };
    } else {
      form = { description, image, order };
    }
    if (!isAdding) {
      form = { ...form, id: formData._id };
    }
    onSubmit(form);
  };

  return (
    <form className="forms-form" onSubmit={handleSubmit}>
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
      <input
        type="text"
        placeholder="URL de la imagen (Opcional)"
        value={image}
        onChange={(e) => setImage(e.target.value)}
      />
      <div className="forms-form-buttons">
        <button type="submit">{isAdding ? "Agregar" : "Guardar"}</button>
        <button type="button" onClick={onCancel}>
          Cancelar
        </button>
      </div>
    </form>
  );
};

export default FormsForm;
