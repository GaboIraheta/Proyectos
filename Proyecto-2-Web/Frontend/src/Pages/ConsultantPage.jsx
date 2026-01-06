import Consultants from "../components/Consultants/Consultants";
import Footer from "../components/Footer/Footer";
import "../components/App/App.css"
import UserNavBar from "../components/Navigation/UserNavBar";

export const ConsultantsPage = () => {
  return (
    <>
      <UserNavBar/>
      <Consultants/>
      <Footer/>
    </>
  );
};
