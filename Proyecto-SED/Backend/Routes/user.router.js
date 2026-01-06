import { execute } from "../Controller/user.controller.js";
import { DataBaseFailed, RegisterAlreadyExists, RouteNotExists } from "../Error/error.js";
import { authenticate, authorize, verifyExistsEmail } from "../Middlewares/auth.middleware.js";
import { actions, type, role } from "../Utils/enums.js";
import { paramParserFactory } from "../Middlewares/parser.middleware.js";
import { validateEmail, validateStrongPassword } from "../Middlewares/validator.middleware.js";

const startPath = "/api/users";

const routes = [
    { method : "POST", path : `${startPath}/register`, action : actions.REGISTER, type : type.AUTH, auth : false }, //FUNCIONA
    { method : "GET", path : `${startPath}/find`, action : actions.FIND, type : type.OBJECT, auth : true, role : role.ADMIN }, //FUNCIONA
    { method : "GET", path : `${startPath}/get`, action : actions.GET, type : type.LIST, auth : true, role : role.ADMIN }, //FUNCIONA
    { method : "PUT", path : `${startPath}/update`, action : actions.UPDATE, type : type.MESSAGE, auth : true, role : role.USER }, //FUNCIONA
    { method : "PUT", path : `${startPath}/addCart`, action : actions.ADD, type : type.MESSAGE, auth : true, role : role.USER }, //FUNCIONA
    { method : "GET", path : `${startPath}/cart`, action : actions.CART, type : type.LIST, auth : true, role : role.USER }, //FUNCIONA
    { method : "DELETE", path : `${startPath}/delete/:id`, action : actions.DELETE, type : type.MESSAGE, auth : true, role : role.ADMIN }, //FUNCIONA
    { method : "DELETE", path : `${startPath}/deleteFromCart`, action : actions.DELETE_CART, type : type.MESSAGE, auth : true, role : role.USER } //FUNCIONA
    ,{ method : "POST", path: `${startPath}/logout`, action: actions.LOGOUT, type: type.MESSAGE, auth: true }
]

const paramParser = paramParserFactory(routes);

export const userRouter = async (req, res) => {

    const { method } = req;

    paramParser(req, res, async () => {

        try {
            
            const route = routes.find(r => r.method === method && r.path === req.matchedPath);

            if (!route)
                throw new RouteNotExists();

            if (!route.auth) {

                const exists = await verifyExistsEmail({ email : req.body.email }, "User");

                if (exists) 
                    throw new RegisterAlreadyExists();
                
                return validateEmail(req, res, () => {
                
                    validateStrongPassword(req, res, async () => {
                
                        await execute(route.action, route.type) (req, res);
                    });
                });
            }

            if (route.action === actions.UPDATE) {

                const exists = await verifyExistsEmail({ email : req.body.credentials.email }, "User");

                if (exists)
                    throw new RegisterAlreadyExists();
            }

            return await authenticate(false) (req, res, async () => {
            
                if (route.role) {

                    await authorize(route.role) (req, res, async () => {
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