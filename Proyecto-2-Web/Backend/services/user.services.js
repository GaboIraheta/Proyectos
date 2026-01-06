import * as userRepository from "../repositories/user.repository.js";
import jwt from "jsonwebtoken";
import { config } from "../config/config.js";
import {
  UserAlreadyExistError,
  InvalidCredentialsError,
  NotFoundUsers,
} from "../errors/errors.js";

export const registerUser = async ({ username, email, password }) => {
  const userExists = await userRepository.findUserByEmail(email);
  if (userExists) {
    throw new UserAlreadyExistError();
  }
  const newUser = await userRepository.createUser({
    username,
    email,
    password,
  });

  const token = jwt.sign(
    { id: newUser._id, role: newUser.role },
    config.jwtSecretUser,
    {
      expiresIn: "1h"
    }
  );
  
  return {
    user: {
      id: newUser._id,
      username: newUser.username,
      email: newUser.email,
      role: newUser.role, 
      checklist: newUser.checklist,
      form: newUser.form,
      contract: newUser.contract
    },
    token
  };
};

export const loginUser = async ({ email, password }) => {
  const user = await userRepository.findUserByEmail(email);
  if (!user || !(await user.comparePassword(password))) {
    throw new InvalidCredentialsError();
  }
  const token = jwt.sign(
    { id: user._id, role: user.role },
    config.jwtSecretUser,
    {
      expiresIn: "1h",
    }
  );
  return {
    user: {
      id: user._id,
      username: user.username,
      email: user.email,
      role: user.role,
      checklist: user.checklist,
      form: user.form,
      contract: user.contract,
    },
    token,
  };
};

export const updateDataUser = async (id, dataUpdated) => {
  const user = await userRepository.findUserById(id);

  if (!user) throw new NotFoundUsers();

  const updatedUser = await userRepository.updateUserById(
    user._id,
    dataUpdated
  );

  return updatedUser;
};

export const updateCredentialsService = async (id, dataUpdated) => {
  const user = await userRepository.findUserById(id);

  if (!user) throw new NotFoundUsers();

  const updatedCredentials = await userRepository.updateCredentialsByID(id, dataUpdated);
  return updatedCredentials;
}

export const changePassword = async (id, newPassword) => {
  const user = await userRepository.findUserById(id);

  if (!user) throw new NotFoundUsers();

  await userRepository.changePasswordToUser(id, newPassword);
}

export const deleteUserAndData = async (id) => {
  const user = await userRepository.findUserById(id);

  if (!user) throw new NotFoundUsers();

  const deletedUser = await userRepository.deleteUserById(user._id);
  return deletedUser;
};

export const getUsersService = async () => {
  const users = userRepository.getUsers();

  if (!users) throw new NotFoundUsers();

  return users;
}

export const recoverPassword = async (email) => {
  const user = await userRepository.findUserByEmail(email);

  if (!user) throw new NotFoundUsers();

  const token = jwt.sign(
    { userID: user._id },
    config.recovery,
    { expiresIn: '1h' }
  );

  return token;
}

export const resetPassword = async (token, newPassword) => {
  const decoded = jwt.verify(token, config.recovery);
  const userID = decoded.userID;

  const user = await userRepository.findUserById(userID);

  if (!user) throw new NotFoundUsers();

  await userRepository.changePasswordToUser(userID, newPassword);
}
