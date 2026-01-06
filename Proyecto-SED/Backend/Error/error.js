export class DataBaseFailed extends Error {
    constructor(message = "Error del servidor.") {
        super(message);
        this.name = "DataBaseFailed";
        // code : 500 - Internal Server Error
    }
}

export class RegisterNotInserted extends Error {
    constructor(message = "El registro no ha podido ser insertado.") {
        super(message);
        this.name = "RegisterNotInserted";
        // code : 500 - Internal Server Error
    }
}

export class RegisterNotFound extends Error {
    constructor(message = "Registro no encontrado.") {
        super(message);
        this.name = "RegisterNotFound";
        // code : 500 - Internal Server Error
    }
}

export class RegisterNotUpdated extends Error {
    constructor(message = "No se ha podido actualizar el registro.") {
        super(message);
        this.name = "RegisterNotUpdated";
    }
}

export class RegisterNotDeleted extends Error {
    constructor(message = "No se ha podido eliminar el registro.") {
        super(message);
        this.name = "RegisterNotDeleted";
    }
}

export class GetRegistersNull extends Error {
    constructor(message = "No se han podido obtener todos los registros.") {
        super(message);
        this.name = "GetRegistersNull";
    }
}

export class NotEmailValid extends Error {
    constructor(message = "El correo electronico ingresado no es valido.") {
        super(message);
        this.name = "NotEmailValid";
    }
}

export class NotPasswordValid extends Error {
    constructor(
        message = "La contraseña ingresada no cumple con los requisitos minimos.\n8 caracteres minimo.\nAl menos una letra mayuscula.\nAl menos un numero.\nAl menos un caracter especial (!@#$%^&*=)."
    ) {
        super(message);
        this.name = "NotPasswordValid";
    }
}

export class NotAuthorized extends Error {
    constructor(message = "Acceso no autorizado.") {
        super(message);
        this.name = "NotAuthorized";
    }
}

export class EmptyUsers extends Error {
    constructor(message = "Registro de usuarios vacio.") {
        super(message);
        this.name = "EmptyUsers";
    }
}

export class EmptyProducts extends Error {
    constructor(message = "Registro de productos vacio.") {
        super(message);
        this.name = "EmptyProducts";
    }
}

export class InvalidAuthentication extends Error {
    constructor(message = "Autenticacion no valida.") {
        super(message);
        this.name = "InvalidAuthetication";
    }
}

export class RouteNotExists extends Error {
    constructor(message = "Ruta no existente.") {
        super(message);
        this.name = "RouteNotExists"
    }
};

export class RegisterAlreadyExists extends Error {
    constructor(message = "El correo electronico ya se encuentra registrado.") {
        super(message);
        this.name = "RegisterAlreadyExists"
    }
}

export class NotValidFormat extends Error {
    constructor(message = "Campos ingresados no validos.") {
        super(message);
        this.name = "NotValidFormat";
    }
}

export class NotValidID extends Error {
    constructor(message = "Se requiere identificacion valida.") {
        super(message);
        this.name = "NotValidID"
    }
}