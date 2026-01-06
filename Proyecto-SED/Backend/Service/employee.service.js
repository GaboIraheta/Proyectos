import {
    DataBaseFailed,
    NotAuthorized,
    NotEmailValid,
    NotPasswordValid,
    RegisterAlreadyExists,
    RegisterNotFound,
    RegisterNotInserted,
    RegisterNotUpdated,
    RegisterNotDeleted,
    NotValidFormat,
    NotValidID
} from "../Error/error.js";
import bcrypt from 'bcrypt';
import employeeRepository from "../Repository/employee.repository.js";
import { generateToken } from "../Utils/generate.js";
import { emailRegex, passRegex } from "../Utils/regex.js";
import { isValidID } from "../Utils/verify.js";

const register = async (request) => {

    // request = { email, password, demas campos }

    try {


        const isEmailValid = emailRegex.test(request.email);
        const isPasswordValid = passRegex.test(request.password);

        if (!isEmailValid) throw new NotEmailValid()
        if (!isPasswordValid) throw new NotPasswordValid();

        const hashedPassword = await bcrypt.hash(request.password, 10);

        const employeeData = { ...request, password: hashedPassword };

        const result = await employeeRepository.insert(employeeData);


        if (!result || !result.insertedId)
            throw new RegisterNotInserted("No se ha podido crear el nuevo empleado.");

        const employee = await employeeRepository.find(request.email);

        if (!employee)
            throw new RegisterNotFound("El registro de empleado no ha sido encontrado.");

        return {
            employee,
            message: "Empleado registrado exitosamente."
        };

    } catch (error) {

        if (error instanceof DataBaseFailed ||
            error instanceof NotEmailValid ||
            error instanceof NotPasswordValid ||
            error instanceof RegisterNotInserted ||
            error instanceof RegisterNotFound ||
            error instanceof RegisterAlreadyExists ||
            error instanceof NotValidFormat
        )
            throw error;

        throw new Error(`Error interno en el registro de empleado: ${error.message}.`);
    }
}

const login = async (request) => {

    // request = { email, password }

    let employee;

    try {

        employee = await employeeRepository.find(request.email, true);

        if (!employee) {
            throw new RegisterNotFound("Empleado no encontrado.");
        }

        const isPasswordValid = await bcrypt.compare(request.password, employee.password);


        if (!isPasswordValid)
            throw new NotAuthorized("Credenciales invalidas.");

        employee = await employeeRepository.find(request.email);

        if (!employee.active) {
            throw new RegisterNotFound("Cuenta desactivada.");
        }

        const token = generateToken(employee._id, employee.email, employee.role);

        return {
            token,
            employee,
            message: `Bienvenido/a, ${employee.name}`
        }

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof NotAuthorized)
            throw error;

        if (error instanceof RegisterNotFound)
            throw new NotAuthorized("Empleado no encontrado.");

        throw new Error(`Error interno en el inicio de sesion: ${error.message}.`);
    }
}

const find = async (request, login = false) => {

    // request = { email }

    try {

        const employee = await employeeRepository.find(request.email);

        if (login)
            return employee;

        if (!employee)
            throw new RegisterNotFound("Empleado no encontrado.");

        return { employee };

    } catch (error) {

        if (error instanceof NotAuthorized || error instanceof RegisterNotFound)
            throw error;

        if (error instanceof DataBaseFailed)
            throw new DataBaseFailed(`Error interno del servidor: ${error.message}.`);

        throw new Error(`Error en la busqueda del empleado: ${error.message}.`);
    }
}

// metodo de obtencion de todos los usuarios para administradores
const get = async () => {

    try {

        const employees = await employeeRepository.get();

        if (!employees)
            throw new GetRegistersNull("No se ha podido obtener el registro de empleados.");

        if (employees.length == 0)
            throw new EmptyUsers("Registro de empleados vacio.");

        return { employees };

    } catch (error) {

        if (error instanceof DataBaseFailed ||
            error instanceof NotAuthorized ||
            error instanceof GetRegistersNull ||
            error instanceof EmptyUsers
        )
            throw error;

        throw new Error(`Error en la obtencion de los empleados: ${error.message}.`);
    }

}

const update = async (request) => {

    // request = { credentials, filter }

    let employee;
    let flag = true;

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        if (!request.credentials.email || !request.credentials.password) {

            employee = await employeeRepository.find(request.filter, true);

            if (!request.credentials.password) {

                request.credentials.password = employee.password;
                flag = false;
            }

            employee = await employeeRepository.find(request.filter);

            if (!request.credentials.email)
                request.credentials.email = employee.email;

        }

        if (flag) {

            const hashedPassword = await bcrypt.hash(request.credentials.password, 10);
            request.credentials.password = hashedPassword;
        }

        const result = await employeeRepository.update(request.filter, request.credentials);

        if (result.modifiedCount == 0)
            throw new RegisterNotUpdated("No se ha podido actualizar las credenciales.");

        return { message: "Credenciales actualizadas exitosamente." };

    } catch (error) {

        if (error instanceof DataBaseFailed ||
            error instanceof RegisterNotUpdated ||
            error instanceof RegisterAlreadyExists ||
            error instanceof NotValidFormat ||
            error instanceof NotValidID
        )
            throw error;

        throw new Error(`Error interno en la actualizacion: ${error.message}.`);
    }
}

const enableDisableAccount = async (request) => {

    let active = request.active;

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        const result = await employeeRepository.enableDisableAccount(request.filter, request.active);


        if (result.modifiedCount == 0)
            throw new RegisterNotUpdated(`No se ha podido ${active ? "activar" : "desactivar"} la cuenta.`);

        return { message: `Cuenta ${active ? "activada" : "desactivada"} exitosamente.` };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotUpdated || error instanceof NotValidID)
            throw error;

        throw new Error(`Error interno al ${active ? "activar" : "desactivar"} la cuenta: ${error.message}.`);
    }
}

const Delete = async (request) => {

    // request = { filter (email) }

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        const result = await employeeRepository.Delete(request.filter);

        if (result.deletedCount == 0) { throw new RegisterNotDeleted("No se encontro al empleado para eliminar."); }

        return { message: "Empleado eliminado exitosamente." };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotDeleted || error instanceof NotValidID)
            throw error;

        throw new Error(`Error interno al eliminar el empleado: ${error.message}.`);
    }

}

export default { register, login, find, get, update, enableDisableAccount, Delete };