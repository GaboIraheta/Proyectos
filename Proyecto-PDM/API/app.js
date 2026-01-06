import express from 'express';
//import cors from 'cors';
import router from './routes/user.route.js';
//import { config } from './config/config';
import path from 'path';
import { fileURLToPath } from 'url';

const app = express();

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

app.use(express.json());
app.use(express.static(path.join(__dirname, 'public')));
app.use("/api/SeaShellCalculator", router);

export default app;