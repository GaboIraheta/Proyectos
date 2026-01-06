import jwt from 'jsonwebtoken';
import config from '../Config/config.js';

export const generateTokenNormal = (id, email) => {

    const token = jwt.sign(
        { id : id, email : email, role : "USER" },
        config.jwtSecret,
        { expiresIn : "1h" }
    );

    return token;
}

export const generateToken = (id, email, role) => {

    const token = jwt.sign(
        { id : id, email : email, role : role ? "ADMIN" : "EMPLOYEE" },
        config.jwtSecret,
        { expiresIn : "1h" }
    );

    return token;
}