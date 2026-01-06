import { param, body } from "express-validator";

export const AdminLoginValidationRules = [
  body("email")
    .notEmpty()
    .withMessage("Se requiere un correo electronico")
    .isEmail()
    .withMessage("Se requiere un correo electronico válido"),
  body("password").notEmpty().withMessage("Se requiere una contraseña"),
];

export const AdminUpdateCredentialsValidationRules = [
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

export const AdminChangePasswordValidationRules = [
  param("id")
    .notEmpty()
    .withMessage("Se requiere un ID de administrador")
    .isMongoId()
    .withMessage("El ID de administrador no es valido"),
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

export const AdminDeleteValidationRules = [
  param("id")
    .notEmpty()
    .withMessage("Se requiere un ID de administrador")
    .isMongoId()
    .withMessage("El ID de administrador no es valido"),
];

export const adminRecoveryValidationRules = [
  body('email')
    .notEmpty()
    .withMessage('Se requiere ingresar el correo electronico')
    .isEmail()
    .withMessage('El correo electronico ingresado no es valido')
]

export const adminResetPasswordValidationRules = [
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
