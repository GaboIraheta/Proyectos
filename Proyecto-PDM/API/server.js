import app from "./app.js";
import { 
    connectDataBase,
    disconnectFromDatabase
} from "./config/database.js";
import { config } from "./config/config.js";
import http from 'http';

const server = http.createServer(app);

const startServer = async () => {

    try {

        await connectDataBase();

        server.listen(config.PORT, () => {
            console.log(`Server running on http://localhost:${config.PORT}`);
        });


        process.on('SIGINT', async () => {
            console.log('Gracefully shutting down');
            await disconnectFromDatabase();
            server.close(() => {
                console.log('Server closed');
                process.exit(0);
            });
        });

    } catch (error) {
        console.error('Failed to start server: ', error);
        process.exit(1);
    }
}

startServer();