import { URL } from "url";
import { execute } from "../Controller/employee.controller.js";
import { authenticate, authorize, verifyExistsEmail } from "../Middlewares/auth.middleware.js";
import { actions, type, role } from "../Utils/enums.js";
import { RouteNotExists, DataBaseFailed, RegisterAlreadyExists } from "../Error/error.js";
import { paramParserFactory } from "../Middlewares/parser.middleware.js";
import { validateEmail, validateStrongPassword } from "../Middlewares/validator.middleware.js";

const startPath = "/api/employees";

const routes = [
    { method : "POST", path : `${startPath}/register`, action : actions.REGISTER, type : type.AUTH, auth : false/*, role : role.ADMIN*/ }, //FUNCIONA
    { method : "GET", path : `${startPath}/find`, action : actions.FIND, type : type.OBJECT, auth : true, role : role.ADMIN }, //FUNCIONA
    { method : "GET", path : `${startPath}/get`, action : actions.GET, type : type.LIST, auth : true, role : role.ADMIN }, //FUNCIONA
    { method : "PUT", path : `${startPath}/update/:id`, action : actions.UPDATE, type : type.MESSAGE, auth : true, role : role.ADMIN }, //FUNCIONA
    // credenciales de admin, credenciales de empleado por admin, credenciales de empleado por si mismo
    { method : "PUT", path : `${startPath}/enable/:id`, action : actions.ENABLE, type : type.MESSAGE, auth : true, role : role.ADMIN }, //FUNCIONA
    { method : "DELETE", path : `${startPath}/delete/:id`, action : actions.DELETE, type : type.MESSAGE, auth : true, role : role.ADMIN } //FUNCIONA
]

const paramParser = paramParserFactory(routes);

export const employeeRouter = async (req, res) => {

    const { method } = req;
    // const { pathname } = new URL(req.url, "http://localhost");

    let _role;

    paramParser(req, res, async () => {
        
        try {

            const route = routes.find(r => r.method === method && r.path === req.matchedPath);

            if (!route)
                throw new RouteNotExists();

            // esto solo es para cuando la ruta es de update

            if (route.action === actions.UPDATE) {
                
                const exists = await verifyExistsEmail({ email : req.body.credentials.email }, "User");

                if (exists)
                    throw new RegisterAlreadyExists();

                _role = req.headers.update === "true" ? role.ADMIN : role.EMPLOYEE
            }

            // se debe enviar update true en headers cuando un admin quiera cambiar sus propias credenciales
            // o cambiar las credenciales de un empleado

            // se debe enviar update false en headers cuando un empleado quiera cambiar sus propias credenciales

            if (!route.auth) {

                const exists = await verifyExistsEmail({ email : req.body.email }, "Employee");

                if (exists)
                    throw new RegisterAlreadyExists();

                return validateEmail(req, res, () => {

                    validateStrongPassword(req, res, async () => {

                        await execute(route.action, route.type) (req, res);
                    });
                });
            }

            return await authenticate(false) (req, res, async () => {
                
                if (route.role) {

                    await authorize(route.action === actions.UPDATE ? _role : route.role) (req, res, async () => {
                        await execute(route.action, route.type) (req, res);
                    });
                
                } else await execute(route.action, route.type) (req, res); 
            });

        } catch (error) {
            
            if (error instanceof RouteNotExists || error instanceof DataBaseFailed || error instanceof RegisterAlreadyExists) {

                res.writeHead(400, { "Content-Type": "application/json" });
                return res.end(JSON.stringify({ error: error.message }));
            }   
        }
    });
}