import express from 'express';
import {
    getAllConsultants, 
    addConsultantController,
    updateConsultantsController,
    deleteConsultantController
} from '../controllers/consultant.controller.js';
import { 
    addConsultantValidationRules,
    updateConsultantValidationRules,
    deleteConsultantValidationRules,
} from '../validators/consultants.validator.js';
import validate from '../middlewares/validation.middleware.js';
import { authenticate } from '../auth/authentication.js';
import { authorizeRole } from '../auth/autorizathion.js';
import { config } from '../config/config.js';

const consultantRouter = express.Router();

consultantRouter.get('/', authenticate, getAllConsultants); 
consultantRouter.post('/add/:id', authenticate, authorizeRole(config.role1), addConsultantValidationRules, validate, addConsultantController);
consultantRouter.put('/update/:id', authenticate, authorizeRole(config.role1), updateConsultantValidationRules, validate, updateConsultantsController);
consultantRouter.delete('/delete/:id', authenticate, authorizeRole(config.role1), deleteConsultantValidationRules, validate, deleteConsultantController);

export default consultantRouter;
