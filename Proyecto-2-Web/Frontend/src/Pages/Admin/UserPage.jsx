import React, { useContext, useEffect, useState } from "react";
import UserTable from "../../components/Tables/UserTable";
import SearchBar from "../../components/Tables/SearchBar";
import config from "../../../config"; 
import './UserPage.css'; 
import { NavLink } from "react-router-dom";
import AdminNavBar from "../../components/Navigation/AdminNavBar";
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import { FetchContext, FetchProvider } from "../../Context/FetchContext";
import useFetch from "../../Hooks/UseFetch";
import Unauthorized from "../../components/Unauthorized/Unauthorized";
import useDelete from "../../Hooks/UseDelete";
import './UserPage.css'; 
import Footer from "../../components/Footer/Footer";
import "./UserPage.css"

const UserPageProvider = () => {

  const { token, role } = useContext(LoginContext);
  const { users, handleFetchUsers } = useContext(FetchContext);

  const { data, error, loading } = useFetch(`${config.API_URL}/admin/all-users`, token, role);
  const { deleteUser, error: errorDelete, loading: loadingDelete } = useDelete(`${config.API_URL}/admin/delete-user`);

  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {

    if (loading) setIsLoading(loading);
    
    if (error) return;

    if (data) 
      handleFetchUsers(data.users);
  }, [data, error]);


  const handleSearch = (email) => {
    if (email === "") {
      handleFetchUsers(users);
    } else {
      handleFetchUsers(
        users.filter((user) => user.email.toLowerCase().includes(email.toLowerCase()))
      );
    }
  };

  const handleDelete = async (user) => {
    setIsLoading(loadingDelete);

    const response = await deleteUser(user._id, token, role);

    if (errorDelete || !response) return;

    console.log(response.message);
    handleFetchUsers(users.filter((user) => user.username !== response.data));
  };

  if (role !== 'admin') return (
    <>
      <Navigation/>
      <Unauthorized/>
      <Footer/>
    </>
  );
  if (isLoading) return <div>Cargando...</div>;

  return (
    <>
      <AdminNavBar/>
      <div className="user-page">
        <h1 className="user-page-title">Lista de Usuarios</h1>
        <div className="search-container">
          <SearchBar onSearch={handleSearch} />
        </div>
        <div className="table-container">
          <UserTable users={users} onDelete={handleDelete} />
        </div>
        {error && <p>{error}</p>}
        <div className="goBack-container">
          <NavLink to="/admin">
            <button className="goBack">Regresar</button>
          </NavLink>
        </div>
      </div>
      <Footer/>
    </>
  );
};

const UserPage = () => {
  return (
    <>
      <LoginProvider>
        <FetchProvider>
          <UserPageProvider/>
        </FetchProvider>
      </LoginProvider>
    </>
  )
}

export default UserPage;
