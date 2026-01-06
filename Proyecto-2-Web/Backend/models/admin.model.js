import mongoose from "mongoose";
import bcrypt from 'bcrypt';

//Definición del modelo de administrador
const adminSchema = new mongoose.Schema(
    {
        username: {
            type: String,
            required: true,
            unique: true,
        },
        email: {
            type: String,
            required: true,
            unique: true,
        },
        password: {
            type: String,
            required: true,
        },
        role: {
            type: String,
            required: true,
            default: 'admin'
        }
    },
    { timestamps: true }
);

/**
 * Middleware pre para el modelo de administrador que se ejecuta
 * cada vez que se realiza un save, el cual verifica si la no contraseña 
 * ha sido cambiada para ejecutar el siguiente middleware, si lo ha sido
 * entonces encripta la contraseña con un hash y manda a llamar a next
 */
adminSchema.pre('save', async function (next) {
    if(!this.isModified('password')) return next();
    this.password = await bcrypt.hash(this.password, 10);
    next();
});

/**
 * Método para el modelo de administrador que sirve para comparar la contraseña
 * ingresada en el login con la contraseña almacenada en la base de datos,
 * la cual utiliza bcrypt para poder comparar tomando en cuenta la encriptacion
 * @param {String} candidatePassword recibe la contraseña ingresada por el usuario en el cliente
 * @returns {Promise<Boolean>} devuelve una promesa resuelta en un valor booleano que indica
 * si la contraseña ingresada por el usuario en el cliente coincide con la contraseña
 * ingresada en la base de datos
 */
adminSchema.methods.comparePassword = async function (candidatePassword) {
    return await bcrypt.compare(candidatePassword, this.password);
};

//Inicialización del modelo de administrador
const Admin = mongoose.model('admin', adminSchema);

export default Admin;