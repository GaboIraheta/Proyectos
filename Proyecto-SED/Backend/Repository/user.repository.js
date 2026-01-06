import { ObjectId } from "mongodb";
import { getInstanceDB } from "../Database/db.js";
import { 
    DataBaseFailed,
    NotValidFormat,
    RegisterAlreadyExists, 
} from "../Error/error.js";

// metodo de registro para usuario normal
const register = async (userData) => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const result = await db.collection("User").insertOne(userData);

        /* result =
        {
            acknowledge : true, (operacion exitosa)
            insertedId : ObjectId("id de mongo")
        }
        */

        return result;

    } catch (error) {

        if (error.code === 11000)
            throw new RegisterAlreadyExists();

        if (error.name === "ValidationError" || error.name === "CastError")
            throw new NotValidFormat();

        if (error instanceof DataBaseFailed) 
            throw error;

        throw new DataBaseFailed(`Error interno en el registro: ${error.message}.`);
    }
}

// metodo para encontrar un usuario normal
const find = async (filter, pass=false) => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        if (ObjectId.isValid(filter)) 
            filter = { _id : new ObjectId(filter) }
        else 
            filter = { email : filter }

        const user = await db.collection("User").findOne(
            filter,
            { projection : { password : pass ? 1 : 0 }}
        );

        // User : { campos de modelo User }

        return user;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error interno en la busqueda del usuario: ${error.message}.`);
    }
}

// metodo para obtener todos los usuarios normales registrados
const get = async () => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const users = await db.collection("User").find({}, { projection : { password : 0 } }).toArray();

        return users;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al obtener los usuarios: ${error.message}.`);
    }
}

// metodo para actualizar credenciales de un usuario normal
const update = async (filter, credentials) => {

    // filter : { email : email }
    // Credentials : { email : "", password : "" }
    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();

        const result = await db.collection("User").updateOne(
            { _id : new ObjectId(filter) },
            { $set: credentials }
        );

        /* result =
        {
            acknowledge : true, (operacion exitosa)
            matchedCount : 1 (coincidencias con el filtro)
            modifiedCount : 1 (cuantos se actualizaron, solo deberia ser uno claramente)
            upsertedCount : 1
            upsertedId : null
        }
        */

        return result;

    } catch (error) {

        if (error.code === 11000)
            throw new RegisterAlreadyExists();

        if (error.name === "ValidationError" || error.name === "CastError")
            throw new NotValidFormat();

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al actualizar credenciales: ${error.message}.`);
    }

}

// metodo para agregar productos al carrito para usuario normal
const addToCart = async (filter, product) => {

    // filter : { id }
    // product : { item }

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const result = await db.collection("User").updateOne(
            { _id : new ObjectId(filter) },
            { $push : { cart : product } }
        );

        // misma estructura que el anterior

        return result;

    } catch (error) {

        if (error.code === 11000)
            throw new RegisterAlreadyExists("El producto ya se encuentra en el carrito.");

        if (error.name === "ValidationError" || error.name === "CastError")
            throw new NotValidFormat("No se pudo agregar el producto al carrito.");

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al agregar producto al carrito: ${error.message}`);
    }
}

const getCart = async (id) => {

    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();

        const user = await db.collection("User").findOne(
            { _id : new ObjectId(id) },
            { projection : { cart : 1 } }
        );

        return user;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al obtener el carrito: ${error.message}.`);
    }
} 

// metodo para eliminar un usuario normal
const Delete = async (filter) => {

    // filter : { email : email }

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const result = await db.collection("User").deleteOne(
            { _id : new ObjectId(filter) }
        );

        /* result =
        {
            acknowledge : true,
            deletedCount : 1
        }
        */

        return result;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al eliminar el usuario: ${error.message}`);
    }

}

const deleteFromCart = async (id, name) => {

    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();

        const result = await db.collection("User").updateOne(
            { _id : new ObjectId(id) },
            { $pull : { cart : { name } } }
        );

        return result;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al eliminar el producto del carrito: ${error.message}.`);
    }
}

export default { register, find, get, update, addToCart, getCart, Delete, deleteFromCart };