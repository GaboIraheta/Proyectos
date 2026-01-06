import Footer from "../../components/Footer/Footer";
import Navigation from "../../components/Navigation/Navigation";
import UserNavBar from "../../components/Navigation/UserNavBar";
import { ProfileLayout } from "./ProfileLayout";

export const ProfilePage = () => {
    return (
        <>
            <UserNavBar/>
            <ProfileLayout/>
            <Footer/>
        </>
    );
}