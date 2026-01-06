import { actions, type } from "../Utils/enums.js";
import { sendError } from "../Middlewares/error.middleware.js";
import productService from "../Service/product.service.js";

export const execute = (action, type_res) => {

    return async (req, res) => {

        let response;

        try {

            const request = req.body;

            switch (action) {

                case actions.GET : {

                    response = await productService.get();
                    break;
                }

                case actions.UPDATE : {

                    // request = { id (producto), updatedData }
                    const id = req.params.id;

                    response = await productService.update({ 
                        filter : id, 
                        updatedData : request.updatedData
                    });
                    break;
                }

                case actions.ENABLE : {
                    
                    const id = req.params.id;
                    response = await productService.enableDisableProduct({
                        filter : id,
                        active : request.active
                    });

                    break;
                }

                case actions.ADD : {

                    // request = { data }

                    response = await productService.add(request);
                    break;
                }

                case actions.DELETE : {

                    // request = { filter }
                    const id = req.params.id;

                    response = await productService.Delete({ filter : id });
                    break;
                }

                default :

                    res.writeHead(500, { "Content-Type": "application/json" });
                    return res.end(JSON.stringify({ error : "No se ha podido ejecutar ninguna accion desde el servidor." }));
            }

            res.writeHead(200, { "Content-Type": "application/json" });

            switch (type_res) {

                case type.OBJECT : {

                    res.end(JSON.stringify({
                        products : response.products,
                        message : response.message
                    }));

                    break;
                }

                case type.LIST : {

                    res.end(JSON.stringify({
                        products : response.products
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