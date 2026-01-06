import * as userServices from '../services/user.service.js';
import { 
    UserAlreadyExistError,
    NotFoundUsers,
    InvalidCredentialsError
} from '../errors/errors.js';

export const registerController = async (req, res) => {

    const { username, email, password } = req.body;

    try {

        const data = await userServices.register({ username, email, password });

        res
            .status(201)
            .json({ message : "Usuario registrado exitosamente", user : data });

    } catch (error) {

        if (error instanceof UserAlreadyExistError) {

            return res
                    .status(400).json({ message : error.message });
        }

        res
            .status(500).json({ message : "Error: ", error : error.message });
    }
} 

export const loginController = async (req, res) => {

    const { email, password } = req.body;

    try {

        const data = await userServices.login({ email, password });
        console.log(data)

        res
            .status(201)
            .json({ 
                message : `Bienvenido a SeaShellCalculator, ${data.user.username}`,
                user : data
            });

    } catch (error) {

        if (error instanceof InvalidCredentialsError) 
            
            return res
                    .status(400).json({ message : error.message });

        res
            .status(500).json({ message : "Error: ", error : error.message });
    }
}

export const updateIsPremiumController = async (req, res) => {

    const { id } = req.params;
    const { isPremium } = req.body;

    console.log(id)

    try {

        const data = await userServices.updateIsPremium(id, isPremium);

        res 
            .status(200).json({ message : "Pago ejecutado exitosamente", user : data });

    } catch (error) {

        if (error instanceof NotFoundUsers) 

            return res 
                    .status(400).json({ message : error.message });

        res
            .status(500).json({ message : "Error al comprar SeaShellCalculator Premium: ", error : error.message });
    }
} 

export const updateCredentialsController = async (req, res) => {

    const { id } = req.params;
    const { username, email } = req.body;

    try {

        const data = await userServices.changeCredentials(id, { username, email });

        res
            .status(200).json({ message : "Credenciales actualizadas", user : data });

    } catch (error) {

        if (error instanceof NotFoundUsers) 

            return res 
                    .status(400).json({ message : error.message });

        res
            .status(500).json({ message : "Error actualizando credenciales: ", error : error.message });
    }
}

export const updatePasswordController = async (req, res) => {

    const { id } = req.params;
    const { newPassword } = req.body;

    try {

        const data = await userServices.changePassword(id, newPassword);

        res
            .status(200).json({ message : "Contraseña actualizada exitosamente", user : data });

    } catch (error) {

        if (error instanceof NotFoundUsers) 

            return res 
                    .status(400).json({ message : error.message });

        res
            .status(500).json({ message : "Error: ", error : error.message });
    }
}

export const addNewMolarMassController = async (req, res) => {

    const { id } = req.params;
    const { newMolarMass } = req.body;

    try {

        const data = userServices.addMolarMassToList(id, newMolarMass);

        res 
            .status(200).json({ message : "Masa molar añadida a la lista", user : data });

    } catch (error) {

        if (error instanceof NotFoundUsers) 

            return res 
                    .status(400).json({ message : error.message });

        res 
            .status(500).json({ message : "Error: ", error : error.message });
    }
}

export const getMolarMassListController = async (req, res) => {

    const { id } = req.params;

    try {

        const molarMassList = await userServices.getMolarMassList(id);

        console.log(molarMassList);

        res
            .status(200).json({ data : molarMassList });

    } catch (error) {

        if (error instanceof NotFoundUsers) 

            return res 
                    .status(400).json({ message : error.message });

        res 
            .status(500).json({ message : "Error: ", error : error.message });
    }
}

export const deleteMolarMassToListController = async (req, res) => {

    const { userId, molarMassId } = req.params;

    try {

        const data = await userServices.deleteMolarMassToList(userId, molarMassId);

        res
            .status(200).json({ message : "Masa molar eliminada de la lista", user : data });

    } catch (error) {

        if (error instanceof NotFoundUsers) 

            return res 
                    .status(400).json({ message : error.message });

        res
            .status(500).json({ message : "Error: ", error : error.message });
    }
}

export const requestRecoveryController = async (req, res) => {

    try {

        const { email } = req.body;

        await userServices.recoverPassword(email);

        res.json({ message : "Correo de recuperacion enviado. Revisar la bandeja de spam." });

    } catch (error) {

        if (error instanceof NotFoundUsers) 
            
            return res 
                    .status(400).json({ message : error.message });

        res.status(500).json({ error : error.message });
    }
}

export const resetPasswordController = async (req, res) => {

    try {

        const { token, newPassword } = req.body;

        const data = userServices.resetPassword(token, newPassword);

        res
            .status(200).json({ message : "Restablecimiento exitoso", user : data });

    } catch (error) {

        if (error instanceof NotFoundUsers) 

            return res
                    .status(400).json({ message : error.message });

        if (error.name === 'TokenExpiredError' || error.name === 'JsonWebTokenError')

            return res
                    .status(400).json({ message : "Fallo en la autenticacion. Solicite restablecimiento nuevamente."})

        res.status(500).json({ message : "Error : ", error : error.message });
    }
}