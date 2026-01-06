import mongoose from 'mongoose';
import User from './user.model.js';

//Definición del modelo de formulario
const valueFormSchema = new mongoose.Schema(
    [
        {
            question: {
                type: String,
                required: true,
                unique: true,
            },
            image: {
                type: String,
                unique: true,
                default: "Imagen no disponible"
            },
            order: {
                type: Number,
                required: true, 
                unique: true,
            },
        },
    ]
);

const formSchema = new mongoose.Schema(
    {
        forms: {
            type: [valueFormSchema],
            required: true,
            unique: true,
        }
    },
    { timestamps: true }
);

/**
 * Middleware post que se ejecuta cada vez que se realiza un save
 * en el modelo de formulario
 * Actualiza las dimensiones del campo form del modelo de usuario
 * ajustando las preguntas del formulario marcadas al formulario
 * existente
 */
formSchema.post('save', async function() {
    const newFormsLength = this.forms.length;

    try {
        const users = await User.find();

        for (const user of users) {
            const current = user.form;
            let updatedForms = [...current];

            if (newFormsLength > updatedForms.length) {
                for (let i = updatedForms.length ; i < newFormsLength ; ++i) {
                    updatedForms.push(false);
                }
            } else if (newFormsLength < updatedForms.length) {
                updatedForms.length = newFormsLength;
            }

            if (current.length != updatedForms.length) {
                user.form = updatedForms;
            }
            await user.save();
        }

    } catch (error) {
        throw new Error(error);
    }
});

//Inicialización del modelo de formulario
const Form = mongoose.model('Form', formSchema);

export default Form;