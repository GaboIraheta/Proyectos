import dotenv from "dotenv";

dotenv.config();

/**
 * @config es un objeto que contiene como campos todas las variables de entorno necesarias 
 * y utilizadas en alguna parte del proyecto 
 */
export const config = {
  port: process.env.PORT || 3000,
  mongoUri: process.env.MONGODB_URI,
  jwtSecretUser: process.env.JWT_SECRET_USER,
  jwtSecretAdmin: process.env.JWT_SECRET_ADMIN,
  paypalClientKey: process.env.PAYPAL_API_CLIENT,
  paypalSecretKey: process.env.PAYPAL_API_SECRET,
  paypalApi: "https://api-m.sandbox.paypal.com",
  HOST: `${process.env.HOST}${process.env.PORT}`,
  role1: process.env.ROLE_ADMIN,
  role2: process.env.ROLE_USER,
  user: process.env.EMAIL_USER,
  pass: process.env.EMAIL_PASS,
  hostNodemailer: process.env.EMAIL_HOST,
  recovery: process.env.RECOVERY_TOKEN,
  recoveryURL: process.env.RECOVERY_URI,
  client: process.env.CLIENT
};
