import * as consultantRepository from "../repositories/consultant.repository.js";
import { NotFoundConsultants, ConsultantAlreadyExist } from "../errors/errors.js";

/**
 * Función para obtener todos los consultores
 * @returns {Promise<Array<Consultant>>} - Retorna una lista de todos los consultores
 */
export const getAllConsultants = async () => {
    const consultants = await consultantRepository.getAllConsultants();

    if (!consultants)
        throw new NotFoundConsultants();

    return consultants;
};

export const addConsultantsService = async (id, { username, email, phone, price }) => {
    const consultants = await consultantRepository.findConsultantById(id);

    if (!consultants)
        throw new NotFoundConsultants();

    consultants.consultants.forEach((consultant) => {
        if (consultant.email === email) 
            throw new ConsultantAlreadyExist();
    });

    const newConsultant = await consultantRepository.addConsultant(id, { username, email, phone, price });
    return newConsultant.consultants.slice(-1)[0];
}

export const updateConsultantsService = async (id, data) => {
    const consultants = await consultantRepository.findConsultantById(id);

    if (!consultants)
        throw new NotFoundConsultants();

    let consultantToUpdate = null;

    consultants.consultants.forEach((consultant) => {
        const realID = consultant._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1')
        if (realID === data.id) //se valida que el consultor que se va a eliminar exista dentro del array de consultores
            consultantToUpdate = consultant;
    });

    if (!consultantToUpdate) 
        throw new Error('Consultor a actualizar no encontrado');

    const dataUpdated = await consultantRepository.updateConsultants(id, data);
    return dataUpdated;
};

export const deleteConsultantService = async (id, consultantID) => {
    const consultants = await consultantRepository.findConsultantById(id);

    if (!consultants)
        throw new NotFoundConsultants();

    let consultantToDelete = null;

    consultants.consultants.forEach((consultant) => {
        const realID = consultant._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1')
        if (realID === consultantID) //se valida que el consultor que se va a eliminar exista dentro del array de consultores
            consultantToDelete = consultant;
    });

    if (!consultantToDelete)
        throw new Error('Consultor a eliminar no encontrado');

    const deletedData = consultantRepository.deleteConsultant(id, consultantID);
    return deletedData;
}