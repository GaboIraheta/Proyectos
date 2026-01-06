import * as formRepository from '../repositories/form.repository.js';
import { GetFormFailed, NotFoundForm, FormAlreadyExists } from '../errors/errors.js';

export const getFormService = async () => {

    const form = await formRepository.getForm();

    if (!form) 
        throw new GetFormFailed();

    return form;
};

export const addFormService = async (id, { question, image, order }) => {
    const form = await formRepository.findFormById(id);

    if (!form)
        throw new NotFoundForm();

    form.forms.forEach((form) => {
        if (form.order === order) 
            throw new FormAlreadyExists();
    });

    const newForm = await formRepository.addForm(id, { question, image, order });
    return newForm.forms.slice(-1)[0];
}

export const updateFormService = async (id, data) => {
    const form = await formRepository.findFormById(id);

    if (!form)
        throw new NotFoundForm();

    let formToUpdate = null;

    form.forms.forEach((_form) => {
        const realID = _form._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1')
        if (realID === data.id)
            formToUpdate = _form;
    });

    if (!formToUpdate)
        throw new Error('Pregunta de formulario a actualizar no encontrada');

    const dataUpdated = await formRepository.updateForms(id, data);
    return dataUpdated;
};

export const deleteFormService = async (id, formID) => {
    const form = await formRepository.findFormById(id);

    if (!form)
        throw new NotFoundForm();

    let formToDelete = null;

    form.forms.forEach((form) => {
        console.log(form._id);
        const realID = form._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1');
        if (realID === formID) //se valida que el consultor que se va a eliminar exista dentro del array de consultores
            formToDelete = form;
    });

    if (!formToDelete)
        throw new Error('Pregunta de formulario a eliminar no encontrada');

    const deletedData = formRepository.deleteForm(id, formID);
    return deletedData;
}