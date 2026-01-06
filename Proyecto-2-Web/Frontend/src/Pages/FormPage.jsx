import Footer from "../components/Footer/Footer";
import UserNavBar from "../components/Navigation/UserNavBar";
import DynamicForm from "./Form/DynamicForm";

export const FormPage = () => {
    return (
        <>
            <UserNavBar/>
            <DynamicForm/>
            <Footer/>
        </>
    );
}