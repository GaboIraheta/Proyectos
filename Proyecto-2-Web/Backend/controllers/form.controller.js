import * as formService from '../services/form.services.js';
import { FormAlreadyExists, GetFormFailed, NotFoundForm } from '../errors/errors.js';

/**
 * Metodo que permite obtener todas las preguntas de formulario
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {GetFormFailed} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const getFormController = async (req, res) => {

    try {

        const form = await formService.getFormService();
        res.status(200).json({ form })

    } catch (error) {

        if (error instanceof GetFormFailed) 
            return res.status(400).json({ message: error.message });

        res.status(500).json({
            message: "Error al obtener los datos del formulario: ",
            error: error.message
        });
    }
};

/**
 * Metodo que permite agregar una nueva pregunta al formulario
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundForm || FormAlreadyExists} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const addFormController = async (req, res) => {
    const { id } = req.params;
    const { question, image, order } = req.body;
  
    try {
        const form = await formService.addFormService(id, { question,image,  order });
        res.status(201).json({ message: 'Pregunta de formulario agregada correctamente.', data: form });
    } catch (error) {
        
        if (error instanceof NotFoundForm || error instanceof FormAlreadyExists)
            return res.status(400).json({ message: error.message });
  
        res.status(500).json({ message: 'Error al agregar la nueva pregunta: ', error: error.message });
    }
  }
  
  /**
 * Metodo que permite actualizar una pregunta de formulario
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundForm || Error} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
  export const updateFormController = async (req, res) => {
    const { id } = req.params;
    const { _id, question, image, order } = req.body;
  
    try {
        const form = await formService.updateFormService(id, {
            id: _id,
            question, image, order
        });
        res.status(201).json({ message: 'Preguntas de formulario actualizada', data: form });
    } catch (error) {
  
        if (error instanceof NotFoundForm || error instanceof Error)
            return res.status(400).json({ message: error.message });
  
        res.status(500).json({ message: 'Error al actualizar la pregunta de formulario: ', error: error.message });
    }
  };
  
  /**
 * Metodo que permite eliminar una pregunta de formulario
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundForm || Error} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
  export const deleteFormController = async (req, res) => {
    const { id } = req.params;
    const { formsID } = req.body;
  
    try {
        const form = await formService.deleteFormService(id, formsID);
        res.status(201).json({ message: 'Pregunta de formulario eliminada exitosamente.', data: form });
    } catch (error) {
  
        if (error instanceof NotFoundForm || error instanceof Error)
            return res.status(400).json({ message: error.message });
  
        res.status(500).json({ message: 'Error al eliminar la pregunta del formulario: ', error: error.message });
    }
  };