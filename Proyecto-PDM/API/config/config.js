import dotenv from 'dotenv';

dotenv.config();

export const config = {
    PORT : process.env.PORT || 3000,
    jwtSecret : process.env.JWT_SECRET,
    MONGO_URI : process.env.MONGODB_URI,
    EMAIL_USER : process.env.EMAIL,
    RESET_URI : process.env.RESET_PASSWORD_URI,
    API_KEY : process.env.API_KEY,
    FETCH_URI : process.env.FETCH_URI
}