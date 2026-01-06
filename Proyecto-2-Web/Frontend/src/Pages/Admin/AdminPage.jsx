import Footer from "../../components/Footer/Footer";
import { AdminMenuLayout } from "./AdminMenuLayout";
import AdminNavBar from "../../components/Navigation/AdminNavBar";

export const AdminPage = () => {
  return (
    <>
      <AdminNavBar/>
      <AdminMenuLayout/>
      <Footer/>
    </>
  );
};
