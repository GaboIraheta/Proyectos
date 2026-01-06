import * as checksRepository from  "../repositories/checklist.repository.js";
import { GetChecklistFailed, NotFoundCheck, CheckAlreadyExists } from "../errors/errors.js";
import { check } from "express-validator";

export const getChecklistService = async () => {

  const checklist = await checksRepository.getChecklist();

  if (!checklist) 
    throw new GetChecklistFailed();

  return checklist;
};

export const addCheckService = async (id, { name, description, order }) => {
  const checklist = await checksRepository.findChecklistById(id);

  if (!checklist)
      throw new NotFoundCheck();

  checklist.checks.forEach((check) => {
    if (check.order === order) 
      throw new CheckAlreadyExists();
  });

  const newCheck = await checksRepository.addCheck(id, { name, description, order });
  return newCheck.checks.slice(-1)[0];
}

export const updateChecksService = async (id, data) => {
  const checks = await checksRepository.findChecklistById(id);

  if (!checks)
      throw new NotFoundCheck();

  let checklistToUpdate = null;

  checks.checks.forEach((check) => {
    const realID = check._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1')
    console.log(realID === data.id);
    console.log(realID);
    console.log(data.id);
    if (realID === data.id) 
      checklistToUpdate = check;
  });

  if (!checklistToUpdate)
    throw new Error ('Check a actualizar no encontrado');

  const dataUpdated = await checksRepository.updateChecklist(id, data);
  return dataUpdated;
};

export const deleteChecksService = async (id, checksID) => {
  const checklist = await checksRepository.findChecklistById(id);

  if (!checklist)
      throw new NotFoundCheck();

  let checkToDelete = null;

  checklist.checks.forEach((check) => {
      const realID = check._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1');
      if (realID === checksID) //se valida que el consultor que se va a eliminar exista dentro del array de consultores
          checkToDelete = check;
  });

  if (!checkToDelete)
      throw new Error('Check a eliminar no encontrado');

  const deletedData = checksRepository.deleteCheck(id, checksID);
  return deletedData;
}
