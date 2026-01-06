import { param, body } from "express-validator";

export const addConsultantValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID de la lista de consultores')
        .isMongoId()
        .withMessage('Se requiere un ID de consultores valido'),
    body("username")
        .notEmpty()
        .withMessage('Se requiere el nombre del consultor')
        .isString()
        .withMessage('Se requiere un nombre valido'),
    body('email')
        .notEmpty()
        .withMessage('Se requiere un correo electronico')
        .isEmail()
        .withMessage('Se requiere un correo electronico valido'),
    body('phone')
        .notEmpty()
        .withMessage('Se requiere el numero de telefono del consultor')
        .isString()
        .withMessage('Se requiere un numero de telefono en formato valido')
        .custom((phone) => {
            if (!(/^\d{4} \d{4}$/.test(phone)))
                throw new Error('Formato de numero de telefono no valido');
            return true;
        }),
    body('price')
        .notEmpty()
        .withMessage('Se requiere el precio de contratacion del consultor')
        .isNumeric()
        .withMessage('Se requiere un precio valido')
]

export const updateConsultantValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID de la lista de consultores')
        .isMongoId()
        .withMessage('Se requiere un ID de consultores valido'),
    body("username")
        .notEmpty()
        .withMessage('Se requiere el nombre del consultor')
        .isString()
        .withMessage('Se requiere un nombre valido'),
    body('email')
        .notEmpty()
        .withMessage('Se requiere un correo electronico')
        .isEmail()
        .withMessage('Se requiere un correo electronico valido'),
    body('phone')
        .notEmpty()
        .withMessage('Se requiere el numero de telefono del consultor')
        .isString()
        .withMessage('Se requiere un numero de telefono en formato valido')
        .custom((phone) => {
            if (!(/^\d{4} \d{4}$/.test(phone)))
                throw new Error('Formato de numero de telefono no valido');
            return true;
        }),
    body('price')
        .notEmpty()
        .withMessage('Se requiere el precio de contratacion del consultor')
        .isNumeric()
        .withMessage('Se requiere un precio valido')
] 

export const deleteConsultantValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID de la lista de consultores')
        .isMongoId()
        .withMessage('Se requiere un ID de consultores valido'),
    body('consultantID')
        .notEmpty()
        .withMessage('Se requiere el ID del consultor a eliminar')
        .isMongoId()
        .withMessage('Se requiere un ID de consultor valido'),
]