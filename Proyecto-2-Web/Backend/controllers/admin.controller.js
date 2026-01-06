import * as adminServices from "../services/admin.service.js";
import {
  InvalidCredentialsError,
  NotFoundAdmin,
} from "../errors/errors.js";
import { deleteUser, getAllUsers } from "./user.controller.js";

/**
 * Metodo para permitir a un administrador iniciar sesion en la aplicacion
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {InvalidCrendentialsError} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const data = await adminServices.loginAdmin({ email, password });
    res
      .status(201)
      .json({ message: `Bienvenido, ${data.admin.username}.`, data: data });
  } catch (error) {
    if (error instanceof InvalidCredentialsError)
      return res.status(400).json({ message: error.message });

    res.status(500).json({
      message: "Error en el inicio de sesión: ",
      error: error.message,
    });
  }
};

/**
 * Metodo para permitir cambiar credenciales de acceso al administrador
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundAdmin} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const updateCredentialsController = async (req, res) => {
  const { id } = req.params;
  const { username, email } = req.body;

  try {
    const updatedData = await adminServices.updateCredentials(id, {
      username,
      email,
    });
    res.status(200).json({
      message: "Credenciales de administrador actualizadas.",
      data: {
        username: updatedData.username,
        email: updatedData.email,
      },
    });
  } catch (error) {
    if (error instanceof NotFoundAdmin)
      return res.status(400).json({ message: error.message });

    res.status(500).json({
      message: "Error al actualizar credenciales del administrador: ",
      error: error.message,
    });
  }
};

/**
 * Metodo para permitir la contraseña de acceso a un administrdor
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {InvalidCrendentialsError} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const changePasswordController = async (req, res) => {
  const { id } = req.params;
  const { newPassword } = req.body;

  try {
    await adminServices.changePassword(id, newPassword);
    res.status(200).json({ message: "La contraseña ha sido actualizada exitosamente." });
  } catch (error) {
    if (error instanceof NotFoundAdmin) 
      return res.status(400).json({ message: error.message });

    res.status(500).json({ message: 'Error al cambiar la contraseña: ', error: error.message });
  }
}

/**
 * Metodo para permitir eliminar a un administrador para un mismo administrador
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundAdmin} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const deleteAdmin = async (req, res) => {
  const { id } = req.params;

  try {
    const deletedData = await adminServices.deleteAdmin(id);
    res.status(201).json({
      message: "Administrador eliminado exitosamente.",
      data: deletedData.username,
    });
  } catch (error) {
    if (error instanceof NotFoundAdmin)
      return res.status(400).json({ message: error.message });

    res.status(500).json({
      message: "Error al eliminar el administrador: ",
      error: error.message,
    });
  }
};

/**
 * Metodo que permite al administrador eliminar un usuario registrado en la aplicacion
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundUsers} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const deleteUserByAdmin = async (req, res) => {
  return deleteUser(req, res);
};

/**
 * Metodo que permite al administrador ver todos los usuarios registrados en la aplicacion
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundUsers} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const seeAllUsers = async (req, res) => {
  return getAllUsers(req, res);
};

/**
 * Metodo que permite realizar la gestion para cambiar la contraseña en caso de olvido
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundAdmin || Error} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const recoveryPasswordController = async (req, res) => {
  const { email } = req.body;

  try {
    const token = await adminServices.recoverPassword(email);
    console.log(email);
    console.log(token);
    if (token) {
      res.cookie('recoveryToken', { token }, {
        httpOnly: true,
        secure: process.env.NODE_ENV === 'production',
        maxAge: 3600000
      });
      res.status(200).json({ message: 'Solicitud de recuperación enviada.' });
    } else {
      res.status(500).json({ message: 'La recuperación de la contraseña no pudo ser gestionada o la autenticación ha expirado.' });
    }
  } catch (error) {
    if (error instanceof NotFoundAdmin || error instanceof Error)
      return res.status(400).json({ message: error.message });

    res.status(500).json({ message: 'Ha ocurrido un error al intentar recuperar la contraseña.'});
  }
}

/**
 * Metodo para restablecer la contraseña luego de haber realizado la autenticacion y autorizacion
 * correspondiente para realizar la accion
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundUsers} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const resetPasswordController = async (req, res) => {
  const token = req.cookies.recoveryToken;
  const { newPassword } = req.body;
  console.log(token);
  console.log(newPassword);
  if (!token) 
    return res.status(500).json({ message: 'Ha fallado la autenticación. No se ha podido restablecer la contraseña.' });

  try {
    await adminServices.resetPassword(token, newPassword);
    res.status(200).json({ message: 'Contraseña actualizada exitosamente.' });
  } catch (error) {
    if (error instanceof NotFoundAdmin)
      return res.status(400).json({ message: error.message });

    res.status(500).json({ message: 'Ha ocurrido un error al actualizar la contraseña.' });
  }
}