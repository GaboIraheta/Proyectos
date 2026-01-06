/**
 * Middleware de autorizacion de usuarios y administradores
 * @param {String} role recibe que tipo de role tiene el usuario que esta intentando a acceder a un sitio de la aplicacion
 * con el parametro role se logra identificar si se esta intentando acceder a un sitio para el cual tiene o no tiene autorizacion
 * esto se logra con el role decodificado por la autenticacion en el middleware anterior a este 
 * @returns {Function} retorna una funcion controladora que funciona como middleware para autorizar y realiza la validacion 
 * correspondiente del rol del usuario para dar o no dar acceso a la ruta que se intenta acceder
 */

export const authorizeRole = (role) => {
    return (req, res, next) => {
        if (req.user.role !== role) 
            return res.status(403).json({ message: 'Intento de acceso a un sitio no autorizado.'});
        next();
    }
}