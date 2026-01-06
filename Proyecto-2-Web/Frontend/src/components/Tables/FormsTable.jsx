import React from "react";
import { FaEdit, FaTrash } from "react-icons/fa";
import "./FormsTable.css";

const FormsTable = ({ forms, onEdit, onDelete }) => {
  return (
    <div className="table-container">
      <table className="forms-table">
        <thead>
          <tr>
            <th>Acciones</th>
            <th>Descripción</th>
            <th>Número</th>
            <th>Imagen (URL)</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(forms) && forms ? forms.map((form, index) => (
            <tr key={index}>
              <td>
                <FaEdit className="edit-icon" onClick={() => onEdit(form)} />
                <FaTrash
                  className="delete-icon"
                  onClick={() => onDelete(form._id)}
                />
              </td>
              <td>{form.question}</td>
              <td>{form.order}</td>
              <td>
                {form.image !== 'Imagen no disponible' ? (
                  // Si req.url tiene un valor (es decir, si existe), muestra el coso
                  <a href={form.image} target="_blank" rel="noopener noreferrer">
                    Ver Imagen
                  </a>
                ) : (
                  // Si req.url no tiene valor muestra esto.
                  "Sin URL"
                )}
              </td>
            </tr>
          )) : <p>No hay formulario disponible...</p>}
        </tbody>
      </table>
    </div>
  );
};

export default FormsTable;
