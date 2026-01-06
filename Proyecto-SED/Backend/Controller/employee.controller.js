import employeeService from "../Service/employee.service.js";
import { actions, type } from "../Utils/enums.js";
import { sendError } from "../Middlewares/error.middleware.js";

export const execute = (action, type_res) => {

    return async (req, res) => {

        let response;
        let auth = false;

        const headers = { "Content-Type": "application/json" };

        try {

            const request = req.body;

            switch (action) {

                case actions.REGISTER : {

                    // request = { todos los campos del empleado }

                    response = await employeeService.register(request);

                    break;
                }

                case actions.FIND : {

                    // request = { email (de empleado) }

                    response = await employeeService.find({ email : request.email });
                    break;
                }

                case actions.GET : {

                    response = await employeeService.get();
                    break;
                }

                case actions.UPDATE : {

                    const id = req.params.id;

                    // request = { credentials ( { email, password }) }

                    response = await employeeService.update({
                        filter : id,
                        credentials : request.credentials
                    });
                    break;
                }

                case actions.ENABLE : {

                    // una cuenta de admin o empleado normal solo puede ser activada o desactivada por un admin

                    const id = req.params.id;

                    response = await employeeService.enableDisableAccount({
                        filter : id,
                        active : request.active
                    });
                    break;
                }

                case actions.DELETE : {

                    // un admin solo puede ser eliminado si otro lo elimina, no puede eliminar su cuenta por si mismo
                    // a diferencia de un usuario normal, al igual que un empleado solo lo puede eliminar un admin

                    // request = { email }

                    const id = req.params.id;

                    response = await employeeService.Delete({ filter : id });
                    break;
                }

                default :

                    res.writeHead(500, headers);
                    return res.end(JSON.stringify({ error : "No se ha podido ejecutar ninguna accion desde el servidor." }));
            }


            res.writeHead(200, headers);

            switch (type_res) {

                case type.AUTH : {

                    res.end(JSON.stringify({
                        message : response.message,
                        employee : response.employee
                    }));

                    break;
                }

                case type.OBJECT : {

                    res.end(JSON.stringify({
                        employee : response.employee
                    }));

                    break;
                }

                case type.LIST : {

                    res.end(JSON.stringify({
                        employees : response.employees 
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