import Checklist from "../models/checklist.model.js";
import convertID from '../utils/utils.js';

//Funciones necesarias para: encontrar, crear, actualizar y eliminar requerimientos.

/**
 * Devuelve todos los documentos de requerimientos existentes
 * con su informacion (campos) correspondiente
 * @returns {Promise<Array<Checklist>>} devuelve una coleccion completa 
 * con todos los requerimientos almacenados en la bbdd
 */
export const getChecklist = async () => {
    return await Checklist.find();
};

/**
 * Función encargada de acceder al objeto que contiene el campo de checks
 * utilizando su ID
 * @param {String} id Recibe el id del objeto que contiene la lista de requerimientos
 * @returns {Promise<Checklist|null>} devuelve el objeto o null en caso de no encontrarlo
 */
export const findChecklistById = async (id) => {
    return await Checklist.findById(id);
};

/**
 * Agrega un nuevo objeto de requerimiento a la lista de checks del modelo
 * @param {String} id recibe el ID del objeto que contiene la lista de requerimientos 
 * @param {Object} param recibe la data del requerimiento como un objeto  
 * @returns {Promise<Checklist>} devuelve el objeto de requerimientos completos
 */
export const addCheck = async (id, { name, description, order }) => {
    const checklist = await Checklist.findById(id);
    checklist.checks.push({ name, description, order });
    return await checklist.save();
}

/**
 * Actualiza un dato de objeto de requerimiento almacenado en los checks
 * @param {String} id recibe el ID del objeto que contiene la lista de requerimientos 
 * @param {Object} data recibe el objeto que contiene la data con los nuevos valores
 * del requerimiento que se va a actualizar 
 * @returns {Promise<Checklist|null>} devuelve el objeto completo de requerimientos
 * con el campo de checks actualizado 
 */
export const updateChecklist = async (id, data) => {

    const checklist = await Checklist.findById(id);

    checklist.checks.forEach((check) => {
        const realID = convertID(check);
        if (realID === data.id) {
            check.name = data.name,
            check.description = data.description,
            check.order = data.order
        }
    });

    return await checklist.save();
};

/**
 * Elimina un objeto del campo lista de requerimientos segun su ID
 * @param {String} id recibe el ID del objeto que contiene el campo de checks 
 * @param {String} checkID recibe el ID del requerimiento que esta almacenado en el campo
 * de checks para poder eliminarlo de la coleccion
 */
export const deleteCheck = async (id, checkID) => {
    const updatedData = await Checklist.findByIdAndUpdate(
        id,
        { $pull: { checks: { _id: checkID } } },
        { new: true }
    );
    return updatedData;
}