import React from "react";
import { FaTrash } from "react-icons/fa";
import "./UserTable.css";

const UserTable = ({ users, onDelete }) => {
  return (
    <div className="table-container">
      <table className="user-table">
        <thead>
          <tr>
            <th>Acciones</th>
            <th>Nombre de Usuario</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(users) && users ? users.map((user, index) => (
            <tr key={index}>
              <td>
                <FaTrash
                  className="delete-icon"
                  onClick={() => onDelete(user)} 
                />
              </td>
              <td>{user.username}</td>
              <td>{user.email}</td>
            </tr>
          )) : <p>No hay usuarios registrados...</p>}
        </tbody>
      </table>
    </div>
  );
};

export default UserTable;
