import * as consultantService from '../services/consultant.services.js';
import { ConsultantAlreadyExist, NotFoundConsultants } from '../errors/errors.js';

/**
 * Metodo que permite obtener todos los consultores disponibles
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundConsultants} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const getAllConsultants = async (req, res) => {
    try {

        const consultants = await consultantService.getAllConsultants();
        res.status(200).json({ consultants });

    } catch (error) {

        if (error instanceof NotFoundConsultants)
            return res.status(400).json({ message: error.message });

        res.status(500).json({ message: 'Error al obtener los consultores', error: error.message });
    }
};

/**
 * Metodo que permite agregar nuevos consultores
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundConsultants || ConsultantAlreadyExist} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const addConsultantController = async (req, res) => {
    const { id } = req.params;
    const { username, email, phone, price } = req.body;

    try {
        const consultant = await consultantService.addConsultantsService(id, { username, email, phone, price });
        res.status(201).json({ message: 'Consultor agregado correctamente', data: consultant });
    } catch (error) {
        
        if (error instanceof NotFoundConsultants || error instanceof ConsultantAlreadyExist)
            return res.status(400).json({ message: error.message });

        res.status(500).json({ message: 'Error al agregar el nuevo consultor.', error: error.message });
    }
}

/**
 * Metodo que permite actualizar los campos de un consultor
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundConsultants || Error} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const updateConsultantsController = async (req, res) => {
    const { id } = req.params;
    const { _id, username, email, phone, price } = req.body;

    try {
        const consultants = await consultantService.updateConsultantsService(id, {
            id: _id,
            username,
            email,
            phone,
            price
        });
        res.status(201).json({ message: 'Consultor actualizado', consultants: consultants });
    } catch (error) {

        if (error instanceof NotFoundConsultants || error instanceof Error)
            return res.status(400).json({ message: error.message });

        res.status(500).json({ message: 'Error al actualizar el consultor: ', error: error.message });
    }
};

/**
 * Metodo que permite eliminar un consultor
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundConsultants || Error} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const deleteConsultantController = async (req, res) => {
    const { id } = req.params;
    const { consultantID } = req.body;

    try {
        const consultants = await consultantService.deleteConsultantService(id, consultantID);
        res.status(201).json({ message: 'Consultor eliminado exitosamente.', data: consultants });
    } catch (error) {

        if (error instanceof NotFoundConsultants || error instanceof Error)
            return res.status(400).json({ message: error.message });

        res.status(500).json({ message: 'Error al eliminar connsultor: ', error: error.message });
    }
}