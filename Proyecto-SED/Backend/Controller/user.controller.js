import userService from "../Service/user.service.js";
import { actions, type } from "../Utils/enums.js";
import { sendError } from "../Middlewares/error.middleware.js";

export const execute = (action, type_res) => {

    return async (req, res) => {

        let response;
        let auth = false;
        let cart = false;

        const headers = { "Content-Type": "application/json" };

        try {

            const request = req.body;

            switch (action) {

                case actions.REGISTER : {

                    // request = { data }

                    response = await userService.register(request);
                    auth = true;

                    break;
                }

                case actions.FIND : {

                    // request = { email (de usuario) }

                    response = await userService.find({ email : request.email });
                    break;
                }

                case actions.GET : {

                    response = await userService.get();
                    break;
                }

                case actions.UPDATE : {

                    const id = req.token.id

                    // request = credentials { email, password }

                    response = await userService.update({
                        filter : id,
                        credentials : request.credentials
                    });
                    break;
                }

                case actions.ADD : {

                    const id = req.token.id;

                    // request = { products (lista carrito del usuario que ya incluya el nuevo producto dentro) }

                    response = await userService.addToCart({
                        filter : id,
                        item : request.product
                    });
                    break;
                }

                case actions.CART : {

                    cart = true;
                    const id = req.token.id;

                    response = await userService.getCart({ filter : id });
                    break;
                }

                case actions.DELETE : {

                    // request = { email o id de usuario }
                    const id = req.params.id;

                    response = await userService.Delete({ 
                        filter : id
                    });
                    break;
                }

                case actions.DELETE_CART : {

                    const id = req.token.id;

                    response = await userService.deleteFromCart({
                        filter : id,
                        name : request.name
                    });

                    break;
                }

                case actions.LOGOUT : {
                    // Clear auth cookie in browser
                    headers["Set-Cookie"] = `authToken=; HttpOnly; Path=/; Max-Age=0; SameSite=Lax`;
                    response = { message: "Sesión cerrada correctamente." };
                    break;
                }

                default :

                    res.writeHead(500, headers);
                    return res.end(JSON.stringify({ error : "No se ha podido ejecutar ninguna accion desde el servidor." }));
            }

            if (auth) {
                headers["Access-Control-Allow-Origin"] = "http://localhost:5173"; // esto es solo por si el front escucha en un puerto diferente
                headers["Access-Control-Allow-Credentials"] = "true";
                headers["Set-Cookie"] = `authToken=${response.token}; HttpOnly; Path=/; Max-Age=3600; SameSite=Lax`;
            }

            // cuando sea https cambiar a SameSite=None; Secure;

            res.writeHead(200, headers);

            switch (type_res) {

                case type.AUTH : {

                    res.end(JSON.stringify({
                        message : response.message,
                        user : response.user
                    }));

                    break;
                }

                case type.OBJECT : {

                    res.end(JSON.stringify({
                        user : response.user 
                    }));

                    break;
                }

                case type.LIST : {

                    if (cart) {

                        res.end(JSON.stringify({
                            cart : response.cart
                        }));

                        break;
                    }

                    res.end(JSON.stringify({
                        users : response.users 
                    }));

                    break;
                }

                case type.MESSAGE : {

                    res.end(JSON.stringify({
                        message : response.message 
                    }));    

                    break;
                }

                default :
                    res.end(JSON.stringify({ message : "No se ha podido obtener respuesta del servidor." }));
            }

        } catch (error) {

            sendError(res, error);
        }
    }
}