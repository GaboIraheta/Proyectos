import { MongoClient } from "mongodb";
import { DataBaseFailed } from "../Error/error.js";

import config from "../Config/config.js";
import create_schemas from "../Utils/schemas.js";

import userModel from "../Model/user.model.js";
import employeeModel from "../Model/employee.model.js";
import productModel from "../Model/product.model.js";

const uri = config.mongo_uri;
const db_name = config.db_name;

let clientInstance = null;
let dbInstance = null; 
 
export const initDB = async () => {

    const db = await getInstanceDB();

    try {

        // Se trata del modelo para usuario general que compra en el mini super
        if (!(await existsCollection(db, "User"))) {

            await db.createCollection(
                "User", 
                create_schemas(userModel.user_required, userModel.user_properties)
            ).catch(() => console.log("La coleccion usuarios ya existe."));

            await db.collection("User").createIndex({ email : 1 }, { unique : true });
        }

        /** Se trata tanto de empleado general como de administrador, identificados por el campo role del modelo
        El administrador tiene permisos sobre los empleados, ademas, de productos y usuarios
        Empleado normal solo tiene permisos sobre los productos **/
        if (!(await existsCollection(db, "Employee"))) {
        
            await db.createCollection(
                "Employee", 
                create_schemas(employeeModel.employee_required, employeeModel.employee_properties)
            ).catch(() => console.log("Coleccion empleados ya existe."));

            await db.collection("Employee").createIndex({ email : 1 }, { unique : true });
        }   

        // Todos los productos se almacenan bajo este modelo
        // Se debe lograr guardar la imagen sin necesidad de tener acceso a internet
        if (!(await existsCollection(db, "Product"))) {
        
            await db.createCollection(
                "Product",
                create_schemas(productModel.product_required, productModel.product_properties)
            ).catch(() => console.log("Coleccion de productos ya existe.")); 

            await db.collection("Product").createIndex({ name : 1 }, { unique : true });
        }

    } catch (error) {
        throw new Error(`Error del servidor: ${error.message}.`);
    }
}

export const getInstanceDB = async () => {

    try {

        if (dbInstance) return dbInstance;

        clientInstance = new MongoClient(uri);
        await clientInstance.connect();
        
        dbInstance = clientInstance.db(db_name);

        return dbInstance;

    } catch (error) {
        throw new DataBaseFailed(`Error del servidor: ${error.message}.`);
    }
}

export const closeInstanceDB = async () => {

    if (clientInstance) {

        await clientInstance.close();

        clientInstance = null;
        dbInstance = null;
    }
}

const existsCollection = async (db, name) => {

    const collections = await db.listCollections({ name : name }).toArray();

    if (collections.length > 0) return true;

    return false;
}