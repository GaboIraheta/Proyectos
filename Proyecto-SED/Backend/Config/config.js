import dotenv from "dotenv";

dotenv.config();

const config = {
    mongo_uri : process.env.MONGO_URI,
    db_name : process.env.DB_NAME,
    jwtSecret : process.env.JWT_SECRET,
    port : process.env.PORT || 3000
}

export default config;