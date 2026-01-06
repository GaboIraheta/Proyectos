import mongoose from "mongoose"
import User from "./user.model.js";

/**
 * Definición del modelo de requerimientos
 */
const valueChecklistSchema = new mongoose.Schema(
    [
        {
            name: {
                type: String,
                required: true,
            },
            description: {
                type: String,
                required: true,
            },
            order: {
                type: Number,
                required: true,
            },
        }
    ]
);

const checklistSchema = new mongoose.Schema(
    {
        checks: {
            type: [valueChecklistSchema],
            required: true,
        },
    }, 
    {
        timestamps: true
    }
);

/**
 * Middleware post para el esquema de checklist el cual ejecuta una funcion
 * cada vez que se ejecuta un save en el modelo
 * La accion que realiza es la de actualizar las dimensiones del campo
 * lista de requerimientos del modelo usuario, ajustando los requerimientos
 * realizados o no del usuario a los existentes
 */
checklistSchema.post('save', async function() {
    const newChecksLength = this.checks.length;

    try {
        const users = await User.find();

        for (const user of users) {
            const current = user.checklist;
            let updatedChecklist = [...current];

            if (newChecksLength > updatedChecklist.length) {
                for (let i = updatedChecklist.length ; i < newChecksLength ; ++i) {
                    updatedChecklist.push(false);
                }
            } else if (newChecksLength < updatedChecklist.length) {
                updatedChecklist.length = newChecksLength;
            }

            if (current.length != updatedChecklist.length) {
                user.checklist = updatedChecklist;
            }
            await user.save();
        }

    } catch (error) {
        throw new Error(error);
    }
});

//Inicialización del modelo de requerimientos 
const Checklist = mongoose.model('CheckList', checklistSchema);

export default Checklist;   