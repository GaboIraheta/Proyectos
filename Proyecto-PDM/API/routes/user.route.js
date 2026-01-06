import {
    registerController,
    loginController,
    updateIsPremiumController,
    updateCredentialsController,
    updatePasswordController,
    addNewMolarMassController,
    getMolarMassListController,
    deleteMolarMassToListController,
    requestRecoveryController,
    resetPasswordController
} from '../controllers/user.controller.js';
import express from 'express';
import validate from '../middlewares/validator.js';
import {
    UserLoginValidationRules,
    userRegisterValidationRules,
    userUpdateCredentialsValidationRules,
    userChangePasswordValidationRules,
    UserIdValidationRules,
    deleteMolarMassValidationRules,
    emailValidationRules,
    resetPasswordValidationRules
} from '../validators/user.validator.js';
import { authenticate } from '../middlewares/auth.js';

const router = express.Router();

router.post("/register", 
    userRegisterValidationRules, validate, 
    registerController
);
router.post("/login", 
    UserLoginValidationRules, validate, 
    loginController
);

router.put("/premium/:id", 
    authenticate, 
    UserIdValidationRules, validate, 
    updateIsPremiumController
);
router.put("/credentials/:id", 
    authenticate, 
    UserIdValidationRules, userUpdateCredentialsValidationRules, validate, 
    updateCredentialsController
);
router.put("/change-password/:id", 
    authenticate, UserIdValidationRules, userChangePasswordValidationRules, validate, 
    updatePasswordController
);

router.put("/addMolarMass/:id", 
    authenticate, 
    UserIdValidationRules, validate, 
    addNewMolarMassController
);
router.get("/getMolarMassList/:id", 
    authenticate, 
    UserIdValidationRules, validate, 
    getMolarMassListController
);
router.delete("/deleteMolarMass/:userId/:molarMassId",
    authenticate, 
    deleteMolarMassValidationRules, validate, 
    deleteMolarMassToListController
);

router.post("/request-recovery",
    emailValidationRules, validate, 
    requestRecoveryController
);
router.put("/reset-password", 
    resetPasswordValidationRules, validate, 
    resetPasswordController
);

export default router;