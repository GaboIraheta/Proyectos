import mongoose from "mongoose";
import { config } from "./config.js";
import { initializeData } from "./data.js";

/**
 * Metodo que se encarga de iniciar la conexion a la base de datos
 * que contiene todos los datos de la aplicacion 
 * @throw error - en caso de que la conexion a la bbdd falle lanzara una excepcion
 * que sera manejada en server.js
 */
export const connectToDatabase = async () => {
	try {
		await mongoose.connect(config.mongoUri), {
			useNewUrlParser: true,
			useUnifiedTopology: true
		};

		await initializeData();

		console.log('Connected to MongoDB');
	} catch (error) {
		console.error('Database connection error: ', error);
		throw error;
	}
};

/**
 * Metodo que se encarga de finalizar la conexion a la base de datos
 * cuando el servidor deje de correr
 */
export const disconnectFromDatabase = async () => {
	try {
		await mongoose.disconnect();
		console.log('Disconnected from MongoDb');
	} catch (error) {
		console.error('Error disconnecting from MongoDB: ', error);
	}
}