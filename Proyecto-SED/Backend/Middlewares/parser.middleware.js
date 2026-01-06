export const jsonParser = (req, res, next) => {
    let data = "";

    req.on("data", chunk => {
        data += chunk;
    });

    req.on("end", () => {
        try {
            req.body = data ? JSON.parse(data) : {};
            next();
        } catch (error) {
            res.writeHead(400, { "Content-Type": "application/json" });
            res.end(JSON.stringify({ error: "Solicitud no valida." }));
        }
    });
};

export const paramParserFactory = (routes) => {
    return (req, res, next) => {

        const reqPath = req.url.split("?")[0];
        const reqParts = reqPath.split("/").filter(Boolean);

        for (const route of routes) {
            const routeParts = route.path.split("/").filter(Boolean);

            if (routeParts.length !== reqParts.length) continue;

            const params = {};
            let matched = true;

            for (let i = 0; i < routeParts.length; i++) {
                if (routeParts[i].startsWith(":")) {
                    const paramName = routeParts[i].slice(1);
                    params[paramName] = reqParts[i];
                } else if (routeParts[i] !== reqParts[i]) {
                    matched = false;
                    break;
                }
            }

            if (matched) {
                // guardamos los parámetros
                req.params = params;

                // guardamos la ruta original del sistema
                req.matchedPath = route.path;

                // guardamos la ruta encontrada
                req.route = route;

                break;
            }
        }

        next();
    };
};

