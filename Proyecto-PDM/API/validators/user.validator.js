import { body, param } from "express-validator";

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

export const UserIdValidationRules = [
    param("id")
      .notEmpty()
      .withMessage("Se requiere un ID de usuario")
      .isMongoId()
      .withMessage("El ID de usuario no es valido"),
]

export const deleteMolarMassValidationRules = [
      param("userId")
        .notEmpty()
        .withMessage("Se requiere un ID de usuario")
        .isMongoId()
        .withMessage("El ID de usuario no es valido"),
      param("molarMassId")
        .notEmpty()
        .withMessage("Se requiere un ID de masa mollar")
        .isMongoId()
        .withMessage("El ID de masa molar no es valido"),
]

export const emailValidationRules = [
    body("email")
      .isEmail()
      .withMessage("El correo electronico no es valido")
      .notEmpty()
      .withMessage("Se requiere un email válido"),
]

export const resetPasswordValidationRules = [
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
      )
]