import mongoose from "mongoose";

//Definición del modelo de consultor
const valueConsultantSchema = new mongoose.Schema(
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
        phone: {
            type: String,
            required: true,
            unique: true,
        },
        price: {
            type: Number,
            required: true,
        }
    }
);

const consultantSchema = new mongoose.Schema(
    {
        consultants: {
            type: [valueConsultantSchema],
            required: true,
            unique: true,
        },
    },
    {
        timestamps: true
    }
)

//Inicialización del modelo de consultor
const Consultant = mongoose.model('Consultant', consultantSchema);

export default Consultant;