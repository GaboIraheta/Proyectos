import mongoose from "mongoose";
import { config } from "./config.js";

export const connectDataBase = async () => {

    try {

        await mongoose.connect(config.MONGO_URI), {
            useNewUrlParser : true,
            useUnifiedTopology : true
        };

        console.log("Connected to MongoDB/SeaShellCalculator");

    } catch (error) {
        console.error('Database connection error: ', error);
		throw error;
    }
}

export const disconnectFromDatabase = async () => {
	try {
		await mongoose.disconnect();
		console.log('Disconnected from MongoDb');
	} catch (error) {
		console.error('Error disconnecting from MongoDB: ', error);
	}
}