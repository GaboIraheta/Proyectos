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

const insert = async (employeeData) => {

    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();
        const result = await db.collection("Employee").insertOne(employeeData);

        return result;

    } catch (error) {

        if (error.code === 11000)
            throw new RegisterAlreadyExists();
        
        if (error.name === "ValidationError" || error.name === "CastError")
            throw new NotValidFormat();

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al registrar nuevo empleado: ${error.message}.`);
    }
}

const find = async (filter, pass=false) => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        if (ObjectId.isValid(filter)) 
            filter = { _id : new ObjectId(filter) }
        else 
            filter = { email : filter }

        const employee = await db.collection("Employee").findOne(
            filter,
            { projection : { password : pass ? 1 : 0 } }
        );

        return employee;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error interno en la busqueda del empleado: ${error.message}.`);
    }
}

const get = async () => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const employees = await db.collection("Employee").find({}, { projection : { password : 0 } }).toArray();


        return employees;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al obtener los empleados: ${error.message}.`);
    }
}

const update = async (filter, credentials) => {

    try {

        const db = await getInstanceDB();

        if (!db)
            throw new DataBaseFailed();

        const result = await db.collection("Employee").updateOne(
            { _id : new ObjectId(filter) },
            { $set: credentials }
        );

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

const enableDisableAccount = async (filter, active) => {
    
    try {

        const db = await getInstanceDB()    ;

        if (!db)
            throw new DataBaseFailed();

        const result = await db.collection("Employee").updateOne(
            { _id : new ObjectId(filter) },
            { $set : { active } }
        );

        return result;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al ${active ? "activar" : "desactivar"} cuenta: ${error.message}.`);
    }
}

const Delete = async (filter) => {

    try {

        const db = await getInstanceDB();

        if (!db) 
            throw new DataBaseFailed();

        const result = await db.collection("Employee").deleteOne(
            { _id : new ObjectId(filter) }
        );

        return result;

    } catch (error) {

        if (error instanceof DataBaseFailed)
            throw error;

        throw new DataBaseFailed(`Error al eliminar el empleado: ${error.message}`);
    }

}

export default { insert, find, get, update, enableDisableAccount, Delete };