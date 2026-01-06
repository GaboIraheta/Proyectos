import { Router } from "express";
import {
    createOrder,
    captureOrder,
} from "../controllers/payment.controller.js"

const paymentRouter = Router();

paymentRouter.post('/create-order', createOrder);
paymentRouter.get('/capture-order', captureOrder);

export default paymentRouter;