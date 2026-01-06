import { NotAuthorized, InvalidAuthentication, DataBaseFailed, RegisterAlreadyExists } from "../Error/error.js";
import employeeService from "../Service/employee.service.js";
import userService from "../Service/user.service.js";
import { verifyAuth, verifyRole } from "../Utils/verify.js";

// todas las solicitudes que requieran autenticacion en el cliente deberan enviar un body 
// el token ira en cookies

export const authenticate = (get_role) => {

    return async (req, res, next) => {

        try {

            const cookies = req.headers.cookie;

            if (!cookies) 
                throw new InvalidAuthentication();

            const token = cookies.split(";").find((param) => param.trim().startsWith("authToken="))?.split("=")[1]

            if (!token)
                throw new InvalidAuthentication();

            const decoded = verifyAuth(token);

            if (get_role) {
                res.writeHead(200, { "Content-Type": "application/json" });
                return res.end(JSON.stringify({ role : decoded.role }));
            }

            req.token = decoded;

            next();

        } catch (error) {

            if (error instanceof InvalidAuthentication) {

                if (get_role) {

                    res.writeHead(401, { "Content-Type": "application/json" });
                    return res.end(JSON.stringify({ role : "guest" }));  
                }

                res.writeHead(401, { "Content-Type" : "application/json" });
                return res.end(JSON.stringify({ error : error.message }));
            }

            res.writeHead(403, { "Content-Type" : "application/json" });
            return res.end(JSON.stringify({ error : `Error interno en la autenticacion: ${error.message}.` }));
        }
    }
}

export const authorize = (realRole) => {

    return (req, res, next) => {

        try {

            const role = req.token.role;

            if (!role) {
                throw new NotAuthorized();
            }

            if (!verifyRole(role, realRole)) {
                throw new NotAuthorized();
            }

            next();

        } catch (error) {

            res.writeHead(403, { "Content-type" : "application/json" });

            if (error instanceof NotAuthorized) 
                return res.end(JSON.stringify( { error : error.message }));

            return res.end(JSON.stringify({ error : `Error en la autorizacion: ${error.message}.` }));
        }
    }   
}

export const verifyExistsEmail = async (email, model) => {
   
    let result;
    
    try {

        if (model === "User") {
            result = await employeeService.find(email, true);
        }

        if (model === "Employee") 
            result = await userService.find(email, true);

        return result;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;
    }
}