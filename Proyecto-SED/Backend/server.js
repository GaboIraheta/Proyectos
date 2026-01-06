import http from "http";
import { mainRouter } from "./Routes/main.router.js";
import config from "./Config/config.js";
import { initDB, closeInstanceDB } from "./Database/db.js";
import { jsonParser } from "./Middlewares/parser.middleware.js";
import { sanitizeBody, sanitizeQuery, sanitizeURL } from "./Middlewares/validator.middleware.js";

const PORT = config.port;

const server = http.createServer(async (req, res) => {

    res.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Update");
    res.setHeader("Access-Control-Allow-Credentials", 'true');

    // Responder inmediatamente las peticiones OPTIONS
    if (req.method === "OPTIONS") {
        res.writeHead(204);
        return res.end();
    }

    try {

        jsonParser(req, res, async () => {

            sanitizeURL(req, res, () => {

                sanitizeQuery(req, res, () => {

                    sanitizeBody(req, res, async () => {

                        await mainRouter(req, res);
                    });
                });
            });
        });

    } catch (error) {

        res.writeHead(500, { "Content-Type": "application/json" });
        res.end(JSON.stringify({ error: "Error interno del servidor" }));
    }
});

const startsServer = async () => {

    try {

        await initDB();
        console.log("Base de datos inicializada correctamente.");

        server.listen(PORT, async () => {
            console.log(`Server running on http://localhost:${PORT}`);
        });

        process.on("SIGINT", async () => {
            console.log("Cerrando base de datos... Servidor apagado.");
            await closeInstanceDB();
            process.exit(0);
        });

    } catch (error) {

        console.error(`Fallo en la inicializacion de la base de datos: ${error}`);
        process.exit(1);
    }
};

startsServer();