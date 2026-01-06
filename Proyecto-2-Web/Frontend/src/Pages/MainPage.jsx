import About from "../components/About/About";
import Accordion from "../components/Accordion/Accordion";
import FAQ from "../components/FAQ/FAQ";
import Footer from "../components/Footer/Footer";
import Hero from "../components/Hero/Hero";
import Logos from "../components/Logos/Logos";
import Navigation from "../components/Navigation/Navigation";
import Services from "../components/Services/Services";
import AdminNavBar from "../components/Navigation/AdminNavBar";
import { useContext } from "react";
import { LoginContext } from "../Context/LoginContext";
import UserNavBar from "../components/Navigation/UserNavBar";
import config from "../../config";
import useFetch from "../Hooks/UseFetch";

export const MainPage = () => {
  const { user, role, token } = useContext(LoginContext);
  const { data, error, loading } = useFetch(
    `${config.API_URL}/checklist/`,
    token,
    role
  );

  return (
    <>
      {error || !user ? (
        <Navigation />
      ) : role === "admin" ? (
        <AdminNavBar />
      ) : ( 
        <UserNavBar />
      )}
      <Hero />
      <Services id="services"/>
      <Logos />
      <FAQ>
        <Accordion />
      </FAQ>
      <About/>
      <Footer/>
    </>
  );
};
