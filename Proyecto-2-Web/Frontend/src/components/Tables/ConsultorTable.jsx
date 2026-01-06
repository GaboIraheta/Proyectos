import React from "react";
import { FaEdit, FaTrash } from "react-icons/fa";
import "./ConsultorTable.css";

const ConsultorTable = ({ consultants, onEdit, onDelete }) => {
  return (
    <div className="table-container">
      <table className="consultor-table">
        <thead>
          <tr>
            <th>Acciones</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Email</th>
            <th>Teléfono</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(consultants) && consultants ? consultants.map((consultor, index) => (
            <tr key={index}>
              <td>
                <FaEdit
                  className="edit-icon"
                  onClick={() => onEdit(consultor)}
                />
                <FaTrash
                  className="delete-icon"
                  onClick={() => onDelete(consultor._id)}
                />
              </td>
              <td>{consultor.username}</td>
              <td>{consultor.price}</td>
              <td>{consultor.email}</td>
              <td>{consultor.phone}</td>
            </tr>
          )) : <tr><p>No hay consultores disponibles...</p></tr> }
        </tbody>
      </table>
    </div>
  );
};

export default ConsultorTable;
