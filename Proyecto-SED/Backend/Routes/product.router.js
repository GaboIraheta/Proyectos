import { URL } from "url";
import { execute } from "../Controller/product.controller.js";
import { authenticate, authorize } from "../Middlewares/auth.middleware.js";
import { actions, type, role } from "../Utils/enums.js";
import { paramParserFactory } from "../Middlewares/parser.middleware.js";
import { RouteNotExists } from "../Error/error.js";

const startPath = "/api/products";

const routes = [
    { method : "POST", path : `${startPath}/add`, action : actions.ADD, type : type.OBJECT }, //FUNCIONA
    { method : "GET", path : `${startPath}/get`, action : actions.GET, type : type.LIST }, //FUNCIONA
    { method : "PUT", path : `${startPath}/update/:id`, action : actions.UPDATE, type : type.MESSAGE }, //FUNCIONA
    { method : "PUT", path : `${startPath}/enable/:id`, action : actions.ENABLE, type : type.MESSAGE }, //FUNCIONA
    { method : "DELETE", path : `${startPath}/delete/:id`, action : actions.DELETE, type : type.MESSAGE } //FUNCIONA
]

const paramParser = paramParserFactory(routes);

export const productRouter = async (req, res) => {

    const { method, url } = req;
    // const { pathname } = new URL(url, "http://localhost");

    paramParser(req, res, async () => {

        try {

            const route = routes.find(r => r.method === method && r.path === req.matchedPath);


            if (!route)
                throw new RouteNotExists();

            if (route.method === "GET")
                return await execute(route.action, route.type) (req, res);

            return await authenticate(false) (req, res, async () => {

                let _role = role.ADMIN;

                if (req.token.role === role.ADMIN)
                    _role = role.ADMIN;

                if (req.token.role === role.EMPLOYEE)
                    _role = role.EMPLOYEE;


                await authorize(_role) (req, res, async () => {
                    await execute(route.action, route.type) (req, res);
                });
            });

        } catch (error) {

            if (error instanceof RouteNotExists) {
            
                res.writeHead(400, { "Content-Type": "application/json" });
                return res.end(JSON.stringify({ error: error.message }));
            }
        }
    });
}