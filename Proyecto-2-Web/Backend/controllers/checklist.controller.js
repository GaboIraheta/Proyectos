import * as checkService from "../services/checklist.service.js";
import { GetChecklistFailed, CheckAlreadyExists, NotFoundCheck } from "../errors/errors.js";

/**
 * Metodo para obtener todos los datos de requerimientos
 * @param {Request} req maneja los requerimientos enviados desde el cliente (en este caso no utilizado)
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {GetChecklistFailed} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const getChecklistController = async (req, res) => {

  try {

    const checklist = await checkService.getChecklistService();
    res.status(200).json({ checklist });

  } catch (error) {
    
    if (error instanceof GetChecklistFailed)
      return res.status(400).json({ message: error.message });

    res.status(500).json({
      message: "Error al obtener los datos de requerimientos: ",
      error: error.message,
    });
  }
};

/**
 * Metodo que permite agregar un nuevo requerimiento 
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundCheck || CheckAlreadyExists} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const addCheckController = async (req, res) => {
  const { id } = req.params;
  const { name, description, order } = req.body;

  try {
      const check = await checkService.addCheckService(id, { name, description, order });
      res.status(201).json({ message: 'Requerimiento agregado correctamente.', data: check });
  } catch (error) {
      
      if (error instanceof NotFoundCheck || error instanceof CheckAlreadyExists)
          return res.status(400).json({ message: error.message });

      res.status(500).json({ message: 'Error al agregar el nuevo requerimiento: ', error: error.message });
  }
}

/**
 * Metodo que permite actualizar los campos de un requerimiento especifico
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundCheck || Error} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const updateChecksController = async (req, res) => {
  const { id } = req.params;
  const { _id, name, description, order } = req.body;

  try {
      const checks = await checkService.updateChecksService(id, {
        id: _id,
        name, description, order
      });
      res.status(201).json({ message: 'Requerimiento actualizado.', data: checks });
  } catch (error) {

      if (error instanceof NotFoundCheck || error instanceof Error)
          return res.status(400).json({ message: error.message });

      res.status(500).json({ message: 'Error al actualizar el requerimiento: ', error: error.message });
  }
};

/**
 * Metodo que permite eliminar un requerimiento
 * @param {Request} req maneja los requerimientos enviados desde el cliente
 * @param {Response} res maneja las respuestas que se envian al cliente
 * @returns {Response} si ocurre un tipo de error {NotFoundCheck || Error} envia una respuesta
 * Si ocurre otro tipo de error envia otra respuesta
 */
export const deleteChecksController = async (req, res) => {
  const { id } = req.params;
  const { checksID } = req.body;

  try {
      console.log(checksID);
      const checks = await checkService.deleteChecksService(id, checksID);
      console.log(checks);
      res.status(201).json({ message: 'Requerimiento eliminado exitosamente.', data: checks });
  } catch (error) {

      if (error instanceof NotFoundCheck || error instanceof Error)
          return res.status(400).json({ message: error.message });

      res.status(500).json({ message: 'Error al eliminar requerimiento: ', error: error.message });
  }
};