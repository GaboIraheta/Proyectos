import userService from "../Service/user.service.js";
import employeeService from "../Service/employee.service.js";
import { RegisterNotFound } from "../Error/error.js";
import { sendError } from "../Middlewares/error.middleware.js";

export const auth = async (req, res) => {

    const request = req.body;
    let user;
    let account;
    let response;

    try {

        user = await userService.find({ email: request.email }, true);
        account = "user";

        if (!user) {

            user = await employeeService.find({ email: request.email }, true);
            account = "employee";
        }


        if (!user) {

            throw new RegisterNotFound("Credenciales invalidas.");
        }

        if (account === "user")
            response = await userService.login(request);

        else
            response = await employeeService.login(request);

        res.writeHead(200,
            {
                "Content-Type": "application/json",
                "Set-Cookie": `authToken=${response.token}; HttpOnly; Path=/; Max-Age=3600`,
                "Access-Control-Allow-Credentials": "true"
            },
        );
        return res.end(JSON.stringify({
            message: response.message,
            user: account === "user" ? response.user : response.employee
        }));

    } catch (error) {

        sendError(res, error);
    }
}