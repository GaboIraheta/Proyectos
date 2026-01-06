import checklistRouter from "./checklist.route.js";
import userRouter from "./user.route.js";
import consultantRouter from "./consultant.route.js";
import formRouter from "./form.route.js";
import express from 'express';
import PaymentRouter from "./payments.routes.js";
import adminRouter from "./admin.route.js";
import { findAdminByEmail } from "../repositories/admin.repository.js";
import jwt from 'jsonwebtoken';
import { config } from "../config/config.js";

const mainRouter = express.Router();

mainRouter.use('/user', userRouter);
mainRouter.use('/checklist', checklistRouter);
mainRouter.use('/form', formRouter);
mainRouter.use('/consultant', consultantRouter);
mainRouter.use('/payment', PaymentRouter);
mainRouter.use('/admin', adminRouter);
mainRouter.post('/verify', async (req, res) => {
    const { email } = req.body;

    if (await findAdminByEmail(email)) {
        console.log(await findAdminByEmail(email));
        return res.status(201).json({ role: 'admin' });
    }

    res.json({ role: 'user' });
});
mainRouter.post('/obtain-auth', async (req, res) => {
    const { id, role } = req.body;

    const token = jwt.sign(
        { id, role },
        role === 'admin' ? config.role1 : config.role2,
        { expiresIn: '1h' } 
    );

    res.status(200).json({ token });
})

export default mainRouter;