import axios from "axios";
import { useCallback } from "react";
import config from "../../config";

const useUtil = () => {

    const handleError = useCallback((error) => {
        if (error.response) {
            if (error.response.data.message)
                return error.response.data.message;
    
            if (error.response.data.error) 
                return error.response.data.error.map(err => <p>{`${err.msg}\n`}</p>);
        } else {
            return 'Ha ocurrido un error en el envío de los datos';
        }
    }, []);

    const verifyRole = useCallback(async (email) => {
        localStorage.setItem("verify", "user");
        console.log(localStorage.getItem('verify'));
        const verify = await axios.post(
          `${config.API_URL}/verify`,
          { email },
          { headers: { "Content-Type": "application/json" } }
        );
    
        console.log(verify.data.role);
        if (verify.data.role === "admin") {
          localStorage.setItem("verify", "admin");
        }
        console.log(localStorage.getItem('verify'));
    }, []);

    const extractToken = useCallback(async (user) => {
        const response = await axios.post(
            `${config.API_URL}/obtain-auth`, 
            { id: user.id, role: user.role }, 
            { headers: { "Content-Type": "application/json" } }
        );

        return response.data;
    }, [])

    return { handleError, verifyRole, extractToken };
}

export default useUtil;