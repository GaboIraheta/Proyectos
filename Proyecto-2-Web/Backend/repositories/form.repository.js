import Form from "../models/form.model.js";
import convertID from "../utils/utils.js";

//Funciones necesarias para: encontrar, crear, actualizar y eliminar preguntas de formulario.

/**
 * Devuelve todos los documentos de formulario existentes
 * con su informacion (campos) correspondiente
 * @returns {Promise<Array<Form>>} devuelve una coleccion completa 
 * con todos los requerimientos almacenados en la bbdd
 */
export const getForm = async () => {
    return await Form.find({});
};

/**
 * Función encargada de acceder al objeto que contiene el campo de forms
 * utilizando su ID
 * @param {String} id Recibe el id del objeto que contiene la lista de preguntas de formulario
 * @returns {Promise<Form|null>} devuelve el objeto o null en caso de no encontrarlo
 */
export const findFormById = async (id) => {
    return await Form.findById(id);
};

/**
 * Agrega un nuevo objeto de formulario a la lista de forms del modelo
 * @param {String} id recibe el ID del objeto que contiene la lista de formulario 
 * @param {Object} param recibe la data del formulario como un objeto  
 * @returns {Promise<Form>} devuelve el objeto de formulario completo
 */
export const addForm = async (id, { question, image, order }) => {
    const group = await Form.findById(id);
    group.forms.push({ question, image, order });
    return await group.save();
}

/**
 * Actualiza un dato de objeto de formulario almacenado en los forms
 * @param {String} id recibe el ID del objeto que contiene la lista de formulario
 * @param {Object} data recibe el objeto que contiene la data con los nuevos valores
 * del formulario que se va a actualizar 
 * @returns {Promise<Form|null>} devuelve el objeto completo de form
 * con el campo de forms actualizado 
 */
export const updateForms = async (id, data) => {

    const form = await Form.findById(id);

    form.forms.forEach((_form) => {
        const realID = convertID(_form);
        if (realID === data.id) {
            _form.question = data.question,
            _form.image = data.image,
            _form.order = data.order
        }
    });

    return await form.save();
};

/**
 * Elimina un objeto del campo lista de forms segun su ID
 * @param {String} id recibe el ID del objeto que contiene el campo de forms
 * @param {String} formID recibe el ID del formulario que esta almacenado en el campo
 * de forms para poder eliminarlo de la coleccion
 */
export const deleteForm = async (id, formID) => {
    const updatedData = await Form.findByIdAndUpdate(
        id,
        { $pull: { forms: { _id: formID } } },
        { new: true }
    );
    return updatedData;
}