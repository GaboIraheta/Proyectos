import { body, param } from "express-validator";

export const addFormValidatorRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID del formulario')
        .isMongoId()
        .withMessage('Se requiere un ID de formulario valido'),
    body("question")
        .notEmpty()
        .withMessage('Se requiere agregar la pregunta al formulario'),
    body('image')
        .optional()
        .isString()
        .withMessage('Se requiere un enlace en formato válido.')
        .custom((image) => {
            // const validExtensions = ['.jpg', '.jpeg', '.png'];

            if (image === 'Imagen no disponible') 
                return true;

            function isURL(image) {
                try {
                    new URL(image);
                    return true;
                } catch {
                    return false;
                }
            };

            if (!isURL(image)) 
                throw new Error('El campo imagen debe ser una URL válida.');
            
            // if (!validExtensions.includes(`.${extension}`)) 
            //     throw new Error('La URL debe apuntar a una imagen válida (jpg, jpeg, png).');

            return true; 
        }),
    body("order")
        .notEmpty()
        .withMessage('Se requiere un numero de orden')
        .isNumeric()
        .withMessage('Se requiere un numero de orden valido')
]

export const updateFormValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID del formulario')
        .isMongoId()
        .withMessage('Se requiere un ID de formulario valido'),
    body("question")
        .notEmpty()
        .withMessage('Se requiere agregar la pregunta al formulario'),
    body('image')
        .optional()
        .isString()
        .withMessage('Se requiere un enlace en formato válido.')
        .custom((image) => {
            // const validExtensions = ['.jpg', '.jpeg', '.png'];

            if (image === 'Imagen no disponible') 
                return true;

            function isURL(image) {
                try {
                    new URL(image);
                    return true;
                } catch {
                    return false;
                }
            };

            if (!isURL(image)) 
                throw new Error('El campo imagen debe ser una URL válida.');
            
            // if (!validExtensions.includes(`.${extension}`)) 
            //     throw new Error('La URL debe apuntar a una imagen válida (jpg, jpeg, png).');

            return true; 
        }),
    body("order")
        .notEmpty()
        .withMessage('Se requiere un numero de orden')
        .isNumeric()
        .withMessage('Se requiere un numero de orden valido')
]

export const deleteFormValidationRules = [
    param("id")
        .notEmpty()
        .withMessage('Se requiere el ID del formulario')
        .isMongoId()
        .withMessage('Se requiere un ID de formulario valido'),
    body('formsID')
        .notEmpty()
        .withMessage('Se requiere el ID de la pregunta a eliminar')
        .isMongoId()
        .withMessage('Se requiere un ID de pregunta de formulario valido'),
]