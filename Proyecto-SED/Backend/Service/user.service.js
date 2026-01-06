import bcrypt from 'bcrypt';
import { 
    DataBaseFailed,
    EmptyUsers,
    GetRegistersNull,
    NotAuthorized,
    NotEmailValid, 
    NotPasswordValid,
    NotValidFormat,
    NotValidID,
    RegisterAlreadyExists,
    RegisterNotDeleted,
    RegisterNotFound,
    RegisterNotInserted,
    RegisterNotUpdated, 
} from "../Error/error.js";
import userRepository from "../Repository/user.repository.js";
import { generateTokenNormal } from '../Utils/generate.js';
import { emailRegex, passRegex } from '../Utils/regex.js';
import { isValidID } from '../Utils/verify.js';

// metodo de registro para usuarios normales
const register = async (request) => {

    /* request =
    {
        email = email,
        password = pass,
        demas campos
    }
    */

    try {

        const isEmailValid = emailRegex.test(request.email);
        const isPasswordValid = passRegex.test(request.password);

        if (!isEmailValid) throw new NotEmailValid();
        if (!isPasswordValid) throw new NotPasswordValid();

        const hashedPassword = await bcrypt.hash(request.password, 10);

        const userData = { ...request, password : hashedPassword };

        const result = await userRepository.register(userData); // Unico error posible : DataBaseFailed

        if (!result || !result.insertedId)
            throw new RegisterNotInserted("No se ha podido completar el registro.");

        const token = generateTokenNormal(result.insertedId, request.email);

        const user = await userRepository.find(request.email);

        if (!user)
            throw new RegisterNotFound("El registro de usuario no ha sido encontrado.");

        return { 
            token, 
            user,
            message : "Usuario registrado exitosamente." 
        };

    } catch (error) {

        if (error instanceof NotEmailValid ||
            error instanceof NotPasswordValid ||
            error instanceof DataBaseFailed ||
            error instanceof RegisterNotInserted ||
            error instanceof RegisterAlreadyExists ||
            error instanceof NotValidFormat
        )
            throw error;

        throw new Error(`Error interno en el registro de usuario: ${error.message}.`);
    }

}

// metodo de login para usuarios normales
const login = async (request) => {

    // Request = { email, password }
    let user;

    try {

        user = await userRepository.find(request.email, true);


        if (!user) 
            throw new RegisterNotFound("Usuario no encontrado.");


        const isPasswordValid = await bcrypt.compare(request.password, user.password);

        if (!isPasswordValid)
            throw new NotAuthorized("Credenciales invalidas.");

        user = await userRepository.find(request.email);

        const token = generateTokenNormal(user._id, user.email);

        return { 
            token, 
            user,
            message : `Bienvenido/a, ${user.name}`
        }

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof NotAuthorized)
            throw error;

        if (error instanceof RegisterNotFound) 
            throw new NotAuthorized();

        throw new Error(`Error interno en el inicio de sesion: ${error.message}`);
    }

}

// metodo de busqueda de usuarios para administradores
const find = async (request, login=false) => {

    // request = { email } el token puede venir envuelto en el primer objeto
    // en teoria si un admin usa este metodo su info debe estar cargada en el sistema, por lo que se envia el role
    // para comprobar que tiene permisos de visualizacion de usuarios normales

    try {


        const user = await userRepository.find(request.email);

        if (login) {
            return user;
        }

        if (!user) 
            throw new RegisterNotFound("Usuario no encontrado.");

        return { user };

    } catch (error) {

        if (error instanceof NotAuthorized || error instanceof RegisterNotFound)
            throw error;

        if (error instanceof DataBaseFailed)
            throw new DataBaseFailed(`Error interno del servidor: ${error.message}.`);

        throw new Error(`Error en la busqueda del usuario: ${error.message}.`);
    }
}

// metodo de obtencion de todos los usuarios para administradores
const get = async () => {

    try {

        const users = await userRepository.get();

        if (!users)
            throw new GetRegistersNull("No se ha podido obtener el registro de usuarios.");

        if (users.length == 0)
            throw new EmptyUsers("Registro de usuarios vacio.");

        return { users };

    } catch (error) {

        if (error instanceof DataBaseFailed ||
            error instanceof NotAuthorized ||
            error instanceof GetRegistersNull ||
            error instanceof EmptyUsers
        )
            throw error;

        throw new Error(`Error en la obtencion de los usuarios: ${error.message}.`);
    }

}

// metodo de actualizacion de credenciales para usuario normal
const update = async (request) => {

    // request = { credentials, filter }

    let user;
    let flag = true;

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        if (!request.credentials.email || !request.credentials.password) {

            user = await userRepository.find(request.filter, true);
            
            if (!request.credentials.password) {

                request.credentials.password = user.password;
                flag = false;
            }

            user = await userRepository.find(request.filter);

            if (!request.credentials.email)
                request.credentials.email = user.email;
        }

        if (flag) {

            const hashedPassword = await bcrypt.hash(request.credentials.password, 10);
            request.credentials.password = hashedPassword;
        }

        const result = await userRepository.update(request.filter, request.credentials);

        if (result.modifiedCount == 0)
            throw new RegisterNotUpdated("No se ha podido actualizar las credenciales.");

        return { message : "Credenciales actualizadas exitosamente." };

    } catch (error) {

        if (error instanceof DataBaseFailed || 
            error instanceof RegisterNotUpdated ||
            error instanceof RegisterAlreadyExists ||
            error instanceof NotValidFormat ||
            error instanceof NotValidID
        )
            throw error;

        throw new Error(`Error interno en la actualizacion: ${error.message}.`);
    }
}

// metodo para agregar productos al carrito para usuario normal
const addToCart = async (request) => {

    // request = { filter, item }

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        request.item = {
            ...request.item,
            image : {'pathBBDD' : request.item.image.pathBBDD, 'file': request.item.image.file}
        };


        const result = await userRepository.addToCart(request.filter, request.item);

        if (result.modifiedCount == 0) 
            throw new RegisterNotUpdated("No se ha podido agregar el producto al carrito.");

        return { message : "Producto agregado al carrito!" };

    } catch (error) {     

        if (error instanceof DataBaseFailed || 
            error instanceof RegisterNotUpdated ||
            error instanceof RegisterAlreadyExists ||
            error instanceof NotValidFormat ||
            error instanceof NotValidID
        )
            throw error;

        throw new Error(`Error interno al agregar el producto: ${error.message}.`);
    }
}

const getCart = async (request) => {

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        const user = await userRepository.getCart(request.filter);

        if (!user)
            throw new RegisterNotFound("Usuario no encontrado.");

        const cart = user.cart;

        if (!cart)
            throw new RegisterNotFound("Carrito del usuario no encontrado.");

        return { cart };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotFound || error instanceof NotValidID)
            throw error;

        throw new Error(`Error interno al obtener el carrito: ${error.message}.`);
    }
}

// metodo para eliminar un usuario normal para administradores
// y por si un usuario normal decide eliminar su cuenta
const Delete = async (request) => {

    // request = { filter (email) }

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        const result = await userRepository.Delete(request.filter);

        if (result.deletedCount == 0) 
            throw new RegisterNotDeleted("No se encontro al usuario para eliminar.");

        return { message : "Usuario eliminado exitosamente." };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotDeleted || error instanceof NotValidID)
            throw error;
        
        throw new Error(`Error interno al eliminar el usuario: ${error.message}.`);
    }

}

const deleteFromCart = async (request) => {

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        const result = await userRepository.deleteFromCart(request.filter, request.name);

        if (!result) 
            throw new RegisterNotFound("Registro de carrito no encontrado.");

        if (result.modifiedCount == 0)
            throw RegisterNotDeleted("El registro de producto del carrito no pudo ser eliminado.");

        return { message : "Registro de producto del carrito eliminado." };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotFound || error instanceof RegisterNotDeleted)
            throw error;

        throw new Error(`Error interno al eliminar producto del carrito: ${error.message}.`);
    }
}

export default { register, login, find, get, addToCart, getCart, update, Delete, deleteFromCart };