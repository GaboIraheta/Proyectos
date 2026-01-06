import * as userService from "../services/user.services.js";
import {
  UserAlreadyExistError,
  InvalidCredentialsError,
  NotFoundUsers,
} from "../errors/errors.js";
import { config } from "../config/config.js";

export const register = async (req, res) => {
  const { username, email, password } = req.body;

  try {
    const result = await userService.registerUser({
      username,
      email,
      password,
    });
    res
      .status(201)
      .json({ message: "Usuario registrado con éxito.", data: result });
  } catch (error) {

    if (error instanceof UserAlreadyExistError) {
      return res.status(400).json({ message: error.message });
    }

    res
      .status(500)
      .json({ message: "Error al registrar el usuario: ", error: error.message });
  }
};

export const login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const loginData = await userService.loginUser({ email, password });
    res.status(201).json({
      message: `Bienvenido, ${loginData.user.username}`,
      data: loginData,
    });
  } catch (error) {
    if (error instanceof InvalidCredentialsError) {
      return res.status(400).json({ message: error.message });
    }

    res
      .status(500)
      .json({ message: "Error al iniciar sesión: ", error: error.message });
  }
};

export const update = async (req, res) => {
  const { id } = req.params;
  const { checklist, form, contract } = req.body;

  try {
    const updatedData = await userService.updateDataUser(id, {
      checklist,
      form,
      contract,
    });
    res.status(200).json({
      message: "Progreso de usuario actualizado exitosamente.",
      data: {
        username: updatedData.username,
        checklist: updatedData.checklist,
        form: updatedData.form,
        contract: updatedData.contract,
      },
    });
  } catch (error) {
    if (error instanceof NotFoundUsers)
      return res.status(400).json({ message: error.message });

    res.status(500).json({
      message: "Error al actualizar el progreso del usuario: ",
      error: error.message,
    });
  }
};

export const updateCredentials = async (req, res) => {
  const { id } = req.params;
  const { username, email } = req.body;

  try {
    const updatedData = await userService.updateCredentialsService(id, {
      username,
      email,
    });
    res.status(200).json({
      message: "Credenciales actualizadas.",
      data: {
        username: updatedData.username,
        email: updatedData.email,
      },
    });
  } catch (error) {
    if (error instanceof NotFoundUsers)
      return res.status(400).json({ message: error.message });

    res.status(500).json({
      message: "Error al actualizar credenciales: ",
      error: error.message,
    });
  }
};

export const changePasswordController = async (req, res) => {
  const { id } = req.params;
  const { newPassword } = req.body;

  try {
    await userService.changePassword(id, newPassword);
    res
      .status(200)
      .json({ message: "La contraseña ha sido actualizada correctamente." });
  } catch (error) {
    if (error instanceof NotFoundUsers)
      return res.status(400).json({ message: error.message });

    res
      .status(500)
      .json({
        message: "Error al cambiar la contraseña del usuario: ",
        error: error.message,
      });
  }
};

export const deleteUser = async (req, res) => {
  const { id } = req.params;
  const headerRole = req.headers["role"];
  const role = headerRole && headerRole.split(" ")[1];

  try {
    const deletedData = await userService.deleteUserAndData(id);

    let message = "Gracias por usar nuestra aplicación, esperamos haya sido de gran ayuda!";
    if (role === "admin") message = "Usuario eliminado correctamente.";

    res.status(201).json({
      message: message,
      data: deletedData.username,
    });
  } catch (error) {
    if (error instanceof NotFoundUsers)
      return res.status(400).json({ message: error.message });

    res.status(500).json({
      message: "Error al finalizar la suscripción: ",
      error: error.message,
    });
  }
};

export const getAllUsers = async (req, res) => {
  try {
    const users = await userService.getUsersService();
    res.status(200).json({ users });
  } catch (error) {
    if (error instanceof NotFoundUsers)
      res.status(400).json({ message: error.message });

    res
      .status(500)
      .json({
        message: "Error al obtener los usuarios: ",
        error: error.message,
      });
  }
};

export const recoveryPasswordController = async (req, res) => {
  const { email } = req.body;

  try {
    const token = await userService.recoverPassword(email);
    console.log(token);

    if (token) {
      res.cookie("recoveryToken", token, {
        httpOnly: true,
        secure: process.env.NODE_ENV === "production",
        maxAge: 3600000,
      });
      res
        .status(200)
        .json({
          message: "Solicitud de recuperación enviada.",
        });
    } else {
      res
        .status(500)
        .json({
          message:
            "La recuperación de la contraseña no pudo ser gestionada o la autenticación ha expirado.",
        });
    }
  } catch (error) {
    if (error instanceof NotFoundUsers || error instanceof Error)
      return res.status(400).json({ message: error.message });

    res
      .status(500)
      .json({
        message: "Ha ocurrido un error al intentar recuperar la contraseña.",
      });
  }
};

export const resetPasswordController = async (req, res) => {
  const token = req.cookies.recoveryToken;
  const { newPassword } = req.body;

  if (!token)
    return res.status(500).json({ message: "Ha fallado la autenticacion. No se ha podido restablecer la contraseña." });

  try {
    await userService.resetPassword(token, newPassword);
    res.status(200).json({ message: "Contraseña actualizada exitosamente." });
  } catch (error) {
    if (error instanceof NotFoundUsers)
      return res.status(400).json({ message: error.message });

    res
      .status(500)
      .json({ message: "Ha ocurrido un error al actualizar la contraseña." });
  }
};
