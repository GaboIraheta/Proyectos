let attempts = {}; 

export const limitLoginAttempts = (maxAttempts = 5, windowMs = 5 * 60 * 1000) => {

    return (req, res, next) => {

        const ip = req.socket.remoteAddress;

        if (!attempts[ip]) 
            attempts[ip] = { count: 0, lastAttempt: Date.now() };

        const now = Date.now();

        if (now - attempts[ip].lastAttempt > windowMs) 
            attempts[ip].count = 0;
        

        attempts[ip].lastAttempt = now;
        attempts[ip].count++;

        if (attempts[ip].count > maxAttempts) {

            res.writeHead(429, { "Content-Type": "application/json" })
            return res.end(JSON.stringify({ error: "Demasiados intentos. Intenta más tarde." }));
        }

        next();
    };
};

export const sanitizeURL = (req, res, next) => {

    try {

        const url = req.url;

        if (!url || typeof url !== "string") {
            
            res.writeHead(400, { "Content-Type": "application/json" });
            return res.end(JSON.stringify({ error: "Solicitud invalida." }));
        }

        if (url.includes("..") || /<|>|script/gi.test(url) || /[\{\}\[\];]/.test(url)) {
            
            res.writeHead(400, { "Content-Type": "application/json" });
            return res.end(JSON.stringify({ error: "Solicitud inválida." }));
        }

        next();

    } catch {

        res.writeHead(400, { "Content-Type": "application/json" });
        res.end(JSON.stringify({ error: "Error al procesar la solicitud." }));
    }
};

export const sanitizeQuery = (req, res, next) => {

    try {

        const qIndex = req.url.indexOf("?");

        if (qIndex === -1) return next();

        const queryString = req.url.substring(qIndex + 1);

        if (/<|>|script/gi.test(queryString) || /[\{\}\[\]]/.test(queryString)) {

            res.writeHead(400, { "Content-Type": "application/json" });
            return res.end(JSON.stringify({ error: "Se detecto actividad sospechosa en la solicitud." }));
        }

        next();

    } catch {
        
        res.writeHead(400, { "Content-Type": "application/json" });
        res.end(JSON.stringify({ error: "Error al procesar la solicitud." }));
    }
};

export const sanitizeBody = (req, res, next) => {
    
    try {

        if (req.body && typeof req.body === "object") {

            for (const key in req.body) {

                const value = req.body[key];

                if (typeof value === "string") {

                    req.body[key] = value
                        .replace(/<script.*?>.*?<\/script>/gi, "")
                        .replace(/<.*?>/g, "")
                        .replace(/[\{\}\[\]]/g, "");

                    if (key.startsWith("$")) delete req.body[key];
                }

                if (typeof key === "string" && key.startsWith("$")) 
                    delete req.body[key];
            }
        }

        next();

    } catch {

        res.writeHead(400, { "Content-Type": "application/json" });
        res.end(JSON.stringify({ error: "Error al procesar la solicitud." }));
    }
};

export const validateStrongPassword = (req, res, next) => {

    try {

        const password = req.body.password;

        if (!password || typeof password !== "string") 
            return res.end(JSON.stringify({ error: "La contraseña es obligatoria." }));

        const minLength = 12; 

        const regex = {
            upper: /[A-Z]/,
            number: /[0-9]/,
            special: /[!@#$%^&*(),.?":{}|<>_\-\\\/\[\]]/
        };

        if (password.length < minLength) {

            res.writeHead(400, { "Content-Type": "application/json" });
            return res.end(JSON.stringify({ error: `La contraseña debe tener al menos ${minLength} caracteres.` }));
        }

        if (!regex.upper.test(password)) {

            res.writeHead(400, { "Content-Type": "application/json" });
            return res.end(JSON.stringify({ error: "La contraseña debe contener al menos una letra mayúscula." }));
        }
        

        if (!regex.number.test(password)) {

            res.writeHead(400, { "Content-Type": "application/json" });
            return res.end(JSON.stringify({ error: "La contraseña debe contener al menos un número." }));
        }
        

        if (!regex.special.test(password)) {

            res.writeHead(400, { "Content-Type": "application/json" });
            return res.end(JSON.stringify({ error: "La contraseña debe contener al menos un carácter especial." }));
        }

        next();

    } catch {

        res.writeHead(400, { "Content-Type": "application/json" });
        res.end(JSON.stringify({ error: "Contraseña inválida." }));
    }
};

export const validateEmail = (req, res, next) => {

    const email = req.body.email;

    if (!email || typeof email !== "string") {

        res.writeHead(400, { "Content-Type": "application/json" });
        return res.end(JSON.stringify({ error: "El correo electronico es obligatorio." }));
    }

    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!regex.test(email)) {

        res.writeHead(400, { "Content-Type": "application/json" });
        return res.end(JSON.stringify({ error: "Formato de correo electronico inválido." }));
    }

    next();
};