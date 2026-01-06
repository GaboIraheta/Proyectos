import mongoose from "mongoose";
import bcrypt from 'bcrypt';

//Definición del modelo de usuario
const contractSchema = new mongoose.Schema({
	contract: {
		type: Boolean,
		required: true,
	},
	consultant: {
		type: mongoose.Schema.Types.ObjectId,
		ref: 'Consultant',
		required: false,
	},
});

const userSchema = new mongoose.Schema(
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
			default: 'user',
		},
		checklist: {
			type: [Boolean], 
			required: true,
			default: [false, false, false, false, false, false, false, false, false, false, false]
		},
		form: {
			type: [Boolean],
			required: true,
			default: [false, false, false, false, false]
		},
		contract: {
			type: contractSchema,
			required: true,
			default: { contract: false, consultant: null }
		}
	},
	{timestamps: true}
);

/**
 * Middleware pre que se ejecuta cada vez que que se realiza un save
 * en el modelo de usuario
 * Verifica si la contraseña no ha sido modificada, si se cumple pasa
 * al siguiente middleware y si no entonces encripta la contraseña ingresada
 * con un hash y almacenar la contraseña encriptada en la base de datos
 */
userSchema.pre('save', async function (next) {
	if(!this.isModified('password')) return next();
	this.password = await bcrypt.hash(this.password, 10);
	next();
});

/**
 * Método para el modelo de usuario que sirve para comparar la contraseña
 * ingresada en el login con la contraseña almacenada en la base de datos,
 * la cual utiliza bcrypt para poder comparar tomando en cuenta la encriptacion
 * @param {String} candidatePassword recibe la contraseña ingresada por el usuario en el cliente
 * @returns {Promise<Boolean>} devuelve una promesa resuelta en un valor booleano que indica
 * si la contraseña ingresada por el usuario en el cliente coincide con la contraseña
 * ingresada en la base de datos
 */
userSchema.methods.comparePassword = async function (candidatePassword) {
	return await bcrypt.compare(candidatePassword, this.password);
};

//Inicialización del modelo de usuario
const User = mongoose.model('User', userSchema);

export default User;