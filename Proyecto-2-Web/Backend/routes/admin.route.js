import express from "express";
import {
  login,
  updateCredentialsController,
  changePasswordController,
  deleteUserByAdmin,
  seeAllUsers,
  recoveryPasswordController,
  resetPasswordController
} from "../controllers/admin.controller.js";
import { UserDeleteValidationRules } from "../validators/user.validator.js";
import {
  AdminLoginValidationRules,
  AdminUpdateCredentialsValidationRules,
  AdminChangePasswordValidationRules,
  adminRecoveryValidationRules,
  adminResetPasswordValidationRules,
} from "../validators/admin.validator.js";
import validate from "../middlewares/validation.middleware.js";
import { authenticate } from "../auth/authentication.js";
import { authorizeRole } from "../auth/autorizathion.js";
import { config } from "../config/config.js";

const adminRouter = express.Router();

adminRouter.post("/login", AdminLoginValidationRules, validate, login); 
adminRouter.put(
  "/update-credentials/:id",
  authenticate,
  authorizeRole(config.role1),
  AdminUpdateCredentialsValidationRules,
  validate,
  updateCredentialsController
);
adminRouter.put(
  "/change-password/:id",
  authenticate,
  authorizeRole(config.role1),
  AdminChangePasswordValidationRules,
  validate,
  changePasswordController
);
adminRouter.delete(
  "/delete-user/:id",
  authenticate,
  authorizeRole(config.role1),
  UserDeleteValidationRules,
  validate,
  deleteUserByAdmin
);
adminRouter.get(
  "/all-users",
  authenticate,
  authorizeRole(config.role1),
  validate,
  seeAllUsers
);
adminRouter.post(
  "/recover-password",
  adminRecoveryValidationRules,
  validate,
  recoveryPasswordController,
);
adminRouter.post(
  '/reset-password', 
  adminResetPasswordValidationRules, 
  validate, 
  resetPasswordController
);

export default adminRouter;
