import { body, param } from "express-validator";

export const addChecksValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID de los requerimientos')
        .isMongoId()
        .withMessage('Se requiere un ID de requerimientos valido'),
    body("name")
        .notEmpty()
        .withMessage('Se requiere el nombre del requerimiento')
        .isString()
        .withMessage('Se requiere un nombre valido'),
    body('description')
        .notEmpty()
        .withMessage('Se requiere una descripcion del requirimiento')
        .isString()
        .withMessage('Se requiere una descripcion valida'),
    body('order')
        .notEmpty()
        .withMessage('Se requiere el orden del requerimiento')
        .isNumeric()
        .withMessage('Se requiere un numero de orden valido')
]

export const updateChecksValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID de los requerimientos')
        .isMongoId()
        .withMessage('Se requiere un ID de requerimientos valido'),
    body("name")
        .notEmpty()
        .withMessage('Se requiere el nombre del requerimiento')
        .isString()
        .withMessage('Se requiere un nombre valido'),
    body('description')
        .notEmpty()
        .withMessage('Se requiere una descripcion del requirimiento')
        .isString()
        .withMessage('Se requiere una descripcion valida'),
    body('order')
        .notEmpty()
        .withMessage('Se requiere el orden del requerimiento')
        .isNumeric()
        .withMessage('Se requiere un numero de orden valido')
]

export const deleteChecksValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID de la lista de requerimientos')
        .isMongoId()
        .withMessage('Se requiere un ID de requerimientos valido'),
    body('checksID')
        .notEmpty()
        .withMessage('Se requiere el ID del requerimiento a eliminar')
        .isMongoId()
        .withMessage('Se requiere un ID de requerimiento valido'),
]