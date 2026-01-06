import express from 'express';
import { 
    getFormController,
    addFormController,
    updateFormController,
    deleteFormController
} from '../controllers/form.controller.js';
import { 
    addFormValidatorRules,
    updateFormValidationRules,
    deleteFormValidationRules
} from '../validators/form.validator.js';
import validate from '../middlewares/validation.middleware.js';
import { authenticate } from '../auth/authentication.js';
import { authorizeRole } from '../auth/autorizathion.js';
import { config } from '../config/config.js';

const formRouter = express.Router();

formRouter.get('/', authenticate, getFormController);
formRouter.post('/add/:id', authenticate, authorizeRole(config.role1), addFormValidatorRules, validate, addFormController);
formRouter.put('/update/:id', authenticate, authorizeRole(config.role1), updateFormValidationRules, validate, updateFormController);
formRouter.delete('/delete/:id', authenticate, authorizeRole(config.role1), deleteFormValidationRules, validate, deleteFormController);

export default formRouter;