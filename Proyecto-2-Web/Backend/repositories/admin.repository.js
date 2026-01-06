import Admin from "../models/admin.model.js";

//Funciones encargadas de gestionar la base de datos segun las necesidades de las
//acciones a realizar
/**
 * Función encargada de crear un nuevo administrador
 * @param {Object} userData Recibe los datos del administrador y crea un nuevo objeto Admin con estos datos
 * @returns {Promise<Admin>} Retorna un Admin con los datos ingresados
 */
export const createAdmin = async ({ username, email, password }) => {
  const admin = new Admin({ username, email, password });
  return await admin.save();
}

/**
 * Función encargada de poder encontrar al administrador utilizando su email
 * @param {String} email Recibe el email del administrador a buscar
 * @returns {Promise<Admin|null>} Retorna el administrador o null en caso de no existir
 */
export const findAdminByEmail = async (email) => {
  return await Admin.findOne({ email });
};

/**
 * Función encargada de buscar un administrador utilizando su ID
 * @param {String} id Recibe el id del administrador a buscar
 * @returns {Promise<Admin|null>} Retorna el administrador o null en caso de no encontrarlo
 */
export const findAdminByID = async (id) => {
  return await Admin.findById(id);
};

/**
 * Actualiza los campos de username y email de un usuario segun el ID
 * @param {String} id recibe el ID del usuario al que se va a actualizar las credenciales
 * @param {Object} updates recibe un objeto con los datos que contienen los nuevos valores
 * @returns {Promise<Admin>} devuelve un objeto con los datos del administrador actualizados
 */
export const updateCredentialesToAdmin = async (id, updates) => {
  return await Admin.findByIdAndUpdate(
    { _id: id },
    {
      username: updates.username,
      email: updates.email,
    },
    {
      new: true,
      runValidators: true,
    }
  );
};

/**
 * Actualiza el campo de contraseña de un administrador segun su ID
 * @param {String} id recibe el ID del administrador al que se va a actualizar la contraseña
 * @param {String} newPassword recibe el nuevo valor de la contraseña
 */
export const changePasswordToAdmin = async (id, newPassword) => {
  const admin = await Admin.findById(id);
  admin.password = newPassword;
  await admin.save();
}

/**
 * Elimina a un administrador por su ID
 * @param {String} id Recibe el id del administrador a eliminar
 * @returns {Promise<User|null>} devuelve el administrador eliminado, o null si hubo un error o no se encuentra
 */
export const deleteAdminByID = async (id) => {
  return await Admin.findByIdAndDelete(id);
};
