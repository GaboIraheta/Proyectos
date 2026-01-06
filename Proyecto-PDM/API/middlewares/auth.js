import jwt from 'jsonwebtoken';
import User from '../models/user.model.js';
import { config } from '../config/config.js';

export const authenticate = async (req, res, next) => {

    const headerAuth = req.headers['authorization'];
    const token = headerAuth && headerAuth.split(' ')[1];
    
    if (!token) 
        return res.status(401).json({ message: 'Acceso no autorizado. Autenticación no válida.'});

    try {

        const decoded = jwt.verify(token, config.jwtSecret);

        const user = await User.findById(decoded.id);

        if (!user)
            return res.status(404).json({ message : "Usuario no encontrado" });

        req.user = user;
        next();

    } catch (error) {

        return res.status(403).json({ message : "Autenticacion no valida o expirada" });
    }
}