import * as userRepo from "../repositories/user.repository.js";
import { createToken } from "../utils/utils.js";
import { 
    NotFoundUsers, 
    UserAlreadyExistError, 
    InvalidCredentialsError
} from '../errors/errors.js';
import { config } from "../config/config.js";
import { sendEmail } from "../utils/utils.js";

export const register = async ({ username, email, password }) => {

    const userExist = await userRepo.findUserByEmail(email);

    if (userExist) {
        throw new UserAlreadyExistError();
    }

    const user = await userRepo.createUser({
        username, email, password
    });

    const token = createToken(user);

    return {
        user : {
            id : user._id,
            username : user.username,
            email : user.email,
            isPremium : user.isPremium,
            molarMassList : user.molarMassList,
        },
        token : token
    };
}

export const login = async ({ email, password }) => {
    
    const user = await userRepo.findUserByEmail(email);

    if (!user) 
        throw new NotFoundUsers();

    else if (!await user.comparePassword(password)) 
        throw new InvalidCredentialsError();

    const token = createToken(user);

    return {
        user : {
            id : user._id,
            username : user.username,
            email : user.email,
            isPremium : user.isPremium,
            molarMassList : user.molarMassList,
        },
        token : token
    };  
}

export const updateIsPremium = async (id, isPremium) => {

    console.log(id)
    const user = await userRepo.findUserById(id);
    console.log(user)

    if (!user) throw new NotFoundUsers();

    return await userRepo.updatePremium(user._id, isPremium);
}

export const changeCredentials = async (id, credentials) => {

    const user = await userRepo.findUserById(id);

    if (!user) throw new NotFoundUsers();

    return await userRepo.updateCredentials(user._id, credentials);
}

export const changePassword = async (id, newPassword) => {

    const user = await userRepo.findUserById(id);

    if (!user) throw new NotFoundUsers();

    return await userRepo.updatePassword(id, newPassword);
}

export const addMolarMassToList = async (id, newMolarMass) => {

    const user = await userRepo.findUserById(id);

    if (!user) throw new NotFoundUsers();

    return await userRepo.updateMolarMassList(id, newMolarMass);
}

export const getMolarMassList = async (id) => {

    const user = await userRepo.findUserById(id);

    if (!user) throw new NotFoundUsers();

    return user.molarMassList;
}

export const deleteMolarMassToList = async (userId, molarMassId) => {

    const user = await userRepo.findUserById(userId);

    if (!user) throw new NotFoundUsers();

    return await userRepo.deleteMolarMass(userId, molarMassId);
}

export const recoverPassword = async (email) => {

    const user = await userRepo.findUserByEmail(email);

    if (!user) throw new NotFoundUsers();

    const token = createToken(user, true);

    const link = config.RESET_URI + `${token}`;

    const html = `<p>Hola ${user.username},</p>
      <p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>
      <a href="${link}">${link}</a>
      <p>Este enlace expirará en 15 minutos.</p>`

    await sendEmail(user.email, "Restablecimiento de contraseña", html);
}

import jwt from 'jsonwebtoken';

export const resetPassword = async (token, newPassword) => {

    const decoded = jwt.verify(token, config.jwtSecret);

    const user = await userRepo.findUserById(decoded.id);

    if (!user) throw new NotFoundUsers();

    return userRepo.updatePassword(user._id, newPassword);
}