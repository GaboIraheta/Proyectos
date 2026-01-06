import jwt from 'jsonwebtoken';
import { config } from '../config/config.js';

/** 
* Middleware para autenticar tanto a usuarios como a administradores a traves de tokens
* @param {Request} req recibe los requerimientos enviados por el cliente para gestionarlos
* en este caso permite obtener cabeceras necesarias para validar el token para autenticar los usuarios y administradores
* y permitirles realizar acciones
* @param {Response} res recibe la respuesta, lo cual permite responder desde el servidor al cliente en caso de que alguna
* verificacion haya fallado en la autenticacion de los usuarios
* @param {import('express').NextFunction} next recibe la funcion que llama al siguiente middleware para el caso en los que 
* la verificacion de la autenticacion es valida, por lo cual da acceso a realizar las siguiente acciones y validaciones
*/
export const authenticate = (req, res, next) => {
    const headerAuth = req.headers['authorization'];
    const token = headerAuth && headerAuth.split(' ')[1];
    const headerRole = req.headers['role'];
    const role = headerRole && headerRole.split(' ')[1];
    
    if (!token || !role) 
        return res.status(401).json({ message: 'Acceso no autorizado. Autenticación no válida.'});

    let secret = config.jwtSecretUser;
    if (role === 'admin') secret = config.jwtSecretAdmin;

    jwt.verify(token, secret, (err, decoded) => {
        if (err) 
            return res.status(403).json({ message: 'Autenticación invalida o expirada.'});

        req.user = {
            id: decoded.id,
            username: decoded.username,
            role: decoded.role,
            checklist: decoded.checklist,
            form: decoded.form,
            contract: decoded.contract
        };

        next();
    });
}