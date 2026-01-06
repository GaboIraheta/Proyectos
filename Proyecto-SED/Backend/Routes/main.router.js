import { userRouter } from "./user.router.js";
import { employeeRouter } from "./employee.router.js";
import { productRouter } from "./product.router.js";
import { DataBaseFailed, RegisterAlreadyExists, RouteNotExists } from "../Error/error.js";
import { authenticate } from "../Middlewares/auth.middleware.js";
import { URL } from "url";
import { auth } from "../Controller/auth.controller.js";
import { limitLoginAttempts } from "../Middlewares/validator.middleware.js";

export const mainRouter = async (req, res) => {

    try {

        const { pathname } = new URL(req.url, "http://localhost");


        if (pathname.startsWith("/api/signin")) {//FUNCIONA

            return limitLoginAttempts(5, 5 * 60 * 1000) (req, res, async () => {
                return await auth(req, res);
            });
        }

        if (pathname.startsWith("/api/users")) {
            return await userRouter(req, res); //FUNCIONA TODO
        }
        
        if (pathname.startsWith("/api/employees")) {
            return await employeeRouter(req, res); //FUNCIONA TODO
        }
        
        if (pathname.startsWith("/api/products"))
            return await productRouter(req, res); //FUNCIONA TODO

        if (pathname.startsWith("/api/auth"))
            return await authenticate(true) (req, res, async () => {}); //FUNCIONA

        if (pathname.startsWith("api/closeSession"))
            return await authenticate(true, false) (req, res, async () => {});

        throw new RouteNotExists();

    } catch (error) {

        res.writeHead(404, { "Content-Type" : "application/json" });

        if (error instanceof RouteNotExists)
            return res.end(JSON.stringify({ error : error.message }));

        return res.end(JSON.stringify({ error : "El servidor no contiene la ruta solicitada. "}));
    }
}