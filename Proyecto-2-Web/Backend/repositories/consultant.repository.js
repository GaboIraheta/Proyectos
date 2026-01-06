import Consultant from "../models/consultant.model.js";

//Funciones necesarias para: encontrar, crear, actualizar y eliminar consultores.

/**
 * Devuelve todos los documentos de consultores existentes
 * con su informacion (campos) correspondiente
 * @returns {Promise<Array<Consultant>>} devuelve una coleccion completa 
 * con todos los consultores almacenados en la bbdd
 */
export const getAllConsultants = async () => {
    return await Consultant.find({});
};

/**
 * Función encargada de poder encontrar al consultor utilizando su email
 * @param {String} email Recibe el email del consultor a buscar
 * @returns {Promise<Consultant|null>} Retorna al consultor o null en caso de no existir
 */
export const findConsultantByEmail = async (email) => {
    return await Consultant.findOne({ email });
};

/**
 * Función encargada de buscar un consultor utilizando su ID
 * @param {String} id Recibe el id del consultor buscar
 * @returns {Promise<Consultant|null>} Retorna al consultor o null en caso de no encontrarlo
 */
export const findConsultantById = async (id) => {
    return await Consultant.findById(id);
};

/**
 * Agrega un nuevo objeto de consultor a la lista de consultants del modelo
 * @param {String} id recibe el ID del objeto que contiene la lista de consultores
 * @param {Object} param recibe la data del consultor como un objeto  
 * @returns {Promise<Consultant>} devuelve el objeto de consultores completo
 */
export const addConsultant = async (id, { username, email, phone, price }) => {
    const group = await Consultant.findById(id);
    group.consultants.push({ username, email, phone, price });
    return await group.save();
}

/**
 * Actualiza un dato de objeto de consultor almacenado en los consultores
 * @param {String} id recibe el ID del objeto que contiene la lista de consultores
 * @param {Object} data recibe el objeto que contiene la data con los nuevos valores
 * de los campos del consultor que se va a actualizar 
 * @returns {Promise<Consultant|null>} devuelve el objeto completo de consultor
 * con el campo de consultores actualizado 
 */
export const updateConsultants = async (id, data) => {

    const consultants = await Consultant.findById(id);

    consultants.consultants.forEach((consultant) => {
        const realID = consultant._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1')
        if (realID === data.id) {
            consultant.username = data.username,
            consultant.email = data.email,
            consultant.phone = data.phone,
            consultant.price = data.price
        }
    });

    return await consultants.save();
};

/**
 * Elimina un objeto del campo lista de consultores segun su ID
 * @param {String} id recibe el ID del objeto que contiene el campo de consultores
 * @param {String} consultantID recibe el ID del consultor que esta almacenado en el campo
 * de consultants para poder eliminarlo de la coleccion
 */
export const deleteConsultant = async (id, consultantID) => {
    const updatedData = await Consultant.findByIdAndUpdate(
        id,
        { $pull: { consultants: { _id: consultantID } } },
        { new: true }
    );
    return updatedData;
}