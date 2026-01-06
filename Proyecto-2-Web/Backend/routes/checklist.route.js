import express from 'express';
import { 
    getChecklistController,
    addCheckController,
    updateChecksController,
    deleteChecksController
} from "../controllers/checklist.controller.js";
import { 
    addChecksValidationRules,
    updateChecksValidationRules,
    deleteChecksValidationRules
} from '../validators/checklist.validator.js';
import validate from '../middlewares/validation.middleware.js';
import { authenticate } from '../auth/authentication.js';
import { authorizeRole } from '../auth/autorizathion.js';
import { config } from '../config/config.js';

const checklistRouter = express.Router();

checklistRouter.get('/', authenticate, getChecklistController); //TODO funciona ver los checklist con autenticacion 
checklistRouter.post('/add/:id', authenticate, authorizeRole(config.role1), addChecksValidationRules, validate, addCheckController);
checklistRouter.put('/update/:id', authenticate, authorizeRole(config.role1), updateChecksValidationRules, validate, updateChecksController);
checklistRouter.delete('/delete/:id', authenticate, authorizeRole(config.role1), deleteChecksValidationRules, validate, deleteChecksController);

export default checklistRouter;