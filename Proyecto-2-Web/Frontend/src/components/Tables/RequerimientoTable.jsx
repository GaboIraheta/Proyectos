import React from "react";
import { FaEdit, FaTrash } from "react-icons/fa";
import './RequerimientoTable.css';

const RequerimientoTable = ({ requerimientos, onEdit, onDelete }) => {
  return (
    <div className="table-container">
      <table className="requerimiento-table">
        <thead>
          <tr>
            <th>Acciones</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Número</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(requerimientos) && requerimientos && requerimientos.map((req, index) => (
            <tr key={index}>
              <td>
                <FaEdit
                  className="edit-icon"
                  onClick={() => onEdit(req)}
                />
                <FaTrash
                  className="delete-icon"
                  onClick={() => onDelete(req._id)}
                />
              </td>
              <td>{req.name}</td>
              <td>{req.description}</td>
              <td>{req.order}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default RequerimientoTable;
