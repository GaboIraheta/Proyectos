import jwt from 'jsonwebtoken';
import config from '../Config/config.js';
import { InvalidAuthentication } from '../Error/error.js';
import { ObjectId } from 'mongodb';

export const verifyAuth = (token) => {

    try {

        const decoded = jwt.verify(token, config.jwtSecret);
        return decoded;

    } catch (error) {

        if (error.name === "TokenExpiredError" || error.name === "JsonWebTokenError") 
            throw new InvalidAuthentication();
    }
}

export const verifyRole = (reqRole, realRole) => {

    return reqRole == realRole;
}

export const isValidID = (id) => { return ObjectId.isValid(id) && (String)(new ObjectId(id)) == id; }