import { validationResult } from "express-validator";

//Middleware para manejar errores de validacion y responderlos al cliente en caso de que existan
const validate = (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ error: errors.array() });
    }
    next();
};
export default validate;