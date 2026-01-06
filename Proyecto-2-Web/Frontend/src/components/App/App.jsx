import React, { useContext, useEffect, useState } from "react";
import "./App.css";
import Navigation from "../Navigation/Navigation";
import Footer from "../Footer/Footer";
import { RegisterPage } from "../Register/RegisterPage";
import { LoginPage } from "../Login/LoginPage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ConsultorPage from "../../Pages/Admin/ConsultorPage";
import UserPage from "../../Pages/Admin/UserPage";
import Consultants from "../Consultants/Consultants";
import Requirements from "../Requirements/Requirements";
import NotFound from "../NotFound/NotFound";
import { MainPage } from "../../Pages/MainPage";
import { ConsultantsPage } from "../../Pages/ConsultantPage";
import { RequirementsPage } from "../../Pages/RequirementPage";
import { ProfilePage } from "../../Pages/Profile/ProfilePage";
import { AdminPage } from "../../Pages/Admin/AdminPage";
import FormsPage from "../../Pages/Admin/FormsPage";
import { LoginContext, LoginProvider } from "../../Context/LoginContext";
import { ProtectedRoutes } from "../../ProtectedRoutes/ProtectedRoutes";
import { FormPage } from "../../Pages/FormPage";
import AdminNavBar from "../Navigation/AdminNavBar";
import { ProfileLayout } from "../../Pages/Profile/ProfileLayout";
import ChangePassword from "../ChangePassword/ChangePassword";
import UserNavBar from "../Navigation/UserNavBar";
import Credentials from "../Credentials/Credentials";
import RequerimientosPage from "../../Pages/Admin/RequerimientosPage"

const AppProvider = () => {
  const { token, role } = useContext(LoginContext);

  return (
    <Router>
      <Routes>
        <Route index element={<MainPage />} />

        <Route path="/register" element={<RegisterPage />} />

        <Route path="/login" element={<LoginPage />} />

        <Route path="/recovery" element={<ChangePassword />} />

        <Route path="" />

        <Route
          element={<ProtectedRoutes isAllowed={token && role === "user"} />}
        >
          <Route path="/consultants" element={<ConsultantsPage />} />

          <Route path="/requirements" element={<RequirementsPage />} />

          <Route path="/profile" element={<ProfilePage />} />

          <Route path="/form" element={<FormPage />} />

          <Route
            path="/change-password"
            element={
              <>
                <UserNavBar />
                <ChangePassword />
              </>
            }
          />

          <Route
            path="/change-credentials"
            element={
              <>
                <UserNavBar />
                <Credentials />
              </>
            }
          />
        </Route>

        <Route
          element={
            <ProtectedRoutes
              ProtectedRoutes
              isAllowed={token && role === "admin"}
            />
          }
        >
          <Route path="/admin" element={<AdminPage />} />

          <Route path="/admin/users" element={<UserPage />} />

          <Route path="/admin/consultants" element={<ConsultorPage />} />

          <Route path="/admin/formPage" element={<FormsPage />} />

          <Route path="/admin/checklistPage" element={<RequerimientosPage/>}/>

          <Route
            path="/admin/change-password"
            element={
              <>
                <AdminNavBar />
                <ChangePassword />
              </>
            }
          />

          <Route
            path="/admin/change-credentials"
            element={
              <>
                <AdminNavBar />
                <Credentials />
              </>
            }
          />
        </Route>


        <Route
          path="*"
          element={
            <>
              <Navigation />
              <NotFound />
              <Footer />
            </>
          }
        />
      </Routes>
    </Router>
  );
};

const App = () => {
  return (
    <>
      <LoginProvider>
        <AppProvider />
      </LoginProvider>
    </>
  );
};

export default App;
