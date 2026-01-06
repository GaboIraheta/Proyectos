import { body, param } from "express-validator";
import Consultant from "../models/consultant.model.js";
import mongoose from "mongoose";

export const userRegisterValidationRules = [
  body("username")
    .isString()
    .withMessage("El nombre de usuario no es valido")
    .notEmpty()
    .withMessage("El nombre de usuario es requerido"),
  body("email")
    .isEmail()
    .withMessage("El correo electronico no es valido")
    .notEmpty()
    .withMessage("Se requiere un email válido"),
  body("password")
    .isLength({ min: 8 })
    .withMessage("La contraseña debe tener almenos 8 caracteres")
    .matches(/[A-Z]/)
    .withMessage("La contraseña debe tener al menos una letra mayúscula")
    .matches(/[$@#%&*]/)
    .withMessage(
      "La contraseña debe contener al menos uno de los siguientes caracteres especiales: $ @ # % & *"
    ),
];

export const UserLoginValidationRules = [
  body("email")
    .notEmpty()
    .withMessage("Se requiere un correo electronico")
    .isEmail()
    .withMessage("Se requiere un correo electronico válido"),
  body("password")
    .notEmpty()
    .withMessage("Se requiere una contraseña"),
];

export const userUpdateCredentialsValidationRules = [
  body("username")
    .isString()
    .withMessage("El nombre de usuario no es valido")
    .notEmpty()
    .withMessage("El nombre de usuario es requerido"),
  body("email")
    .isEmail()
    .withMessage("El correo electronico no es valido")
    .notEmpty()
    .withMessage("Se requiere un email válido"),
];

export const userChangePasswordValidationRules = [
  param("id")
    .notEmpty()
    .withMessage("Se requiere un ID de usuario")
    .isMongoId()
    .withMessage("El ID de usuario no es valido"),
  body("newPassword")
    .notEmpty()
    .withMessage("Se requiere ingresar la nueva contraseña")
    .isLength({ min: 8 })
    .withMessage("La contraseña debe tener almenos 8 caracteres")
    .matches(/[A-Z]/)
    .withMessage("La contraseña debe tener al menos una letra mayúscula")
    .matches(/[$@#%&*]/)
    .withMessage(
      "La contraseña debe contener al menos uno de los siguientes caracteres especiales: $ @ # % & *"
    ),
];

export const UserUpdateValidationRules = [
  param("id")
    .notEmpty()
    .withMessage("Se requiere un ID de usuario")
    .isMongoId()
    .withMessage("El ID de usuario no es valido"),
  body("checklist")
    .isArray()
    .withMessage(
      "El campo de progreso en checklist no representa una coleccion"
    )
    .notEmpty()
    .withMessage("El campo de checklist es requerido")
    .custom((checks) => {
      let message = "El campo de checklist no representa una coleccion valida"; 

      checks.forEach((step) => {
        if (typeof step !== 'boolean')
          throw new Error(message);
      });

      return true;
    }),
  body("form")
    .isArray()
    .withMessage(
      "El campo de progreso en formulario no representa una coleccion"
    )
    .notEmpty()
    .withMessage("El campo de formulario es requerido")
    .custom((forms) => {
      let message = "El campo de formulario no representa una coleccion valida";

      forms.forEach((form) => {
        if (typeof form !== 'boolean')
          throw new Error(message);
      });

      return true;
    }),
  body("contract")
    .notEmpty()
    .withMessage("Se requiere el campo de contrato")
    .isObject()
    .withMessage("El campo de contrato no representa un objeto valido")
    .custom(async (contract) => {
      
      if (typeof contract.contract != "boolean")
        throw new Error("El campo de contrato no representa un objeto valido");

      if (contract.consultant) {
        if (!mongoose.Types.ObjectId.isValid(contract.consultant))
          throw new Error(
            "El campo de contrato no posee un ID de consultor valido"
          );

        let flag = true;

        const consultant = await Consultant.find();

        consultant.forEach((consultant) => {
          if (consultant._id === contract.consultant)
            flag = false;
        });

        if (!flag)
          throw new Error("El ID de consultor asignado no existe");
      }

      if (
        (contract.contract && !contract.consultant) ||
        (!contract.contract && contract.consultant)
      )
        throw new Error("El campo contrato no posee un valor asignado");

      return true;
    }),
];

export const UserDeleteValidationRules = [
  param("id")
    .notEmpty()
    .withMessage("Se requiere un ID de usuario")
    .isMongoId()
    .withMessage("El ID de usuario no es valido"),
];

export const userRecoveryValidationRules = [
  body('email')
    .notEmpty()
    .withMessage('Se requiere ingresar el correo electronico')
    .isEmail()
    .withMessage('El correo electronico ingresado no es valido')
]

export const userResetPasswordValidationRules = [
  body("newPassword")
    .notEmpty()
    .withMessage("Se requiere ingresar la nueva contraseña")
    .isLength({ min: 8 })
    .withMessage("La contraseña debe tener almenos 8 caracteres")
    .matches(/[A-Z]/)
    .withMessage("La contraseña debe tener al menos una letra mayúscula")
    .matches(/[$@#%&*]/)
    .withMessage(
      "La contraseña debe contener al menos uno de los siguientes caracteres especiales: $ @ # % & *"
    ),
]