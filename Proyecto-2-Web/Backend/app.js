import express from "express";
import cors from 'cors';
import mainRouter from "./routes/mainRouter.js";
import userRoutes from './routes/user.route.js';
import paymentRoutes from "./routes/payments.routes.js";
import { config } from "./config/config.js";
import cookieParser from "cookie-parser";

const app = express();

app.use(cors({
    origin: config.client,
    credentials: true
}));
app.use(cookieParser());
app.use(express.json());
app.use(paymentRoutes);
app.use('/api/users', userRoutes);
app.use('/api', mainRouter);
export default app;