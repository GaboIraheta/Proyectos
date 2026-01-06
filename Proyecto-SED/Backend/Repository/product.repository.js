import { ObjectId } from "mongodb";
import { getInstanceDB } from "../Database/db.js";
import { 
    DataBaseFailed, 
    RegisterNotInserted, 
    RegisterNotFound, 
    RegisterNotUpdated,
    RegisterNotDeleted,
    RegisterAlreadyExists
} from "../Error/error.js";

const add = async (productData) => {

    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();

        const result = await db.collection("Product").insertOne(productData);

        return result;

    } catch (error) {

        if (error.code === 11000)
            throw new RegisterAlreadyExists("El producto ya se encuentra registrado.");

        if (error.name === "ValidationError" || error.name === "CastError")
            throw new NotValidFormat();

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al agregar nuevo producto: ${error.message}.`);
    }
}

const find = async (id) => {

    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();

        const product = await db.collection("Product").findOne({ 
            _id : new ObjectId(id) 
        });

        return product;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error en la busqueda del producto: ${error.message}.`);
    }
}

const update = async (filter, updatedData) => {

    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();

        const result = await db.collection("Product").updateOne(
            { _id : new ObjectId(filter) },
            { $set: updatedData }
        );

        return result;

    } catch (error) {

        if (error.code === 11000)
            throw new RegisterAlreadyExists("El producto ya se encuentra registrado.");
        
        if (error.name === "ValidationError" || error.name === "CastError")
            throw new NotValidFormat();

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al actualizar campos del producto: ${error.message}.`);
    }

}

// la busqueda dinamica debe ser desde el front, dado que ya se obtuvieron todos los productos
const get = async () => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const products = await db.collection("Product").find({}).toArray();

        return products;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al obtener los productos: ${error.message}.`);
    }
}

const enableDisableProduct = async (filter, active) => {
    
    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();


        const result = await db.collection("Product").updateOne(
            { _id : new ObjectId(filter) },
            { $set : { active }}
        );

        return result;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al ${active ? "activar" : "desactivar"} producto: ${error.message}.`);
    }
}

const Delete = async (filter) => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const result = await db.collection("Product").deleteOne(
            { _id : new ObjectId(filter) }
        );

        return result;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al eliminar el producto: ${error.message}`);
    }

}

export default { add, find, update, get, enableDisableProduct, Delete };