import User from "../models/user.model.js";

//Funciones necesarias para: encontrar, crear, actualizar y eliminar usuarios.
/**
 * Función encargada de poder encontrar al usuario utilizando su email
 * @param {String} email Recibe el email del usuario a buscar
 * @returns {Promise<User|null>} Retorna al usuario o null en caso de no existir
 */
export const findUserByEmail = async (email) => {
  return await User.findOne({ email });
};

/**
 * Función encargada de buscar un usuario utilizando su ID
 * @param {String} id -Recibe el id del usuario a buscar
 * @returns {Promise<User|null>} -Retorna al usuario o null en caso de no encontrarlo
 */
export const findUserById = async (id) => {
  return await User.findOne({ _id: id });
};

/**
 * Función encargada de crear un nuevo usuario
 * @param {Object} userData Recibe los datos del usuario y crea un nuevo objeto User con estos datos
 * @returns {Promise<User>} Retorna un User con los datos ingresados
 */
export const createUser = async ({ username, email, password }) => {
  const user = new User({ username, email, password });
  return await user.save();
};

/**
 * Actualiza los campos de progreso de un usuario según su ID
 * @param {String} id Recibe el id del usuario al cual se le actualizará la información
 * @param {Object} updates Objeto con los valores y los campos a actualizar
 * @returns {Promise<User|null>} Retorna el usuario actualizado o null en caso de no encontrarlo
 */
export const updateUserById = async (id, updates) => {
  return await User.findByIdAndUpdate(
    { _id: id },
    { 
      checklist: updates.checklist,
      form: updates.form,
      contract: updates.contract
    },
    {
      new: true,
      runValidators: true,
    }
  );
};

/**
 * Actualiza los campos de username y email de un usuario segun el ID
 * @param {String} id recibe el ID del usuario al que se va a actualizar las credenciales
 * @param {Object} updates recibe un objeto con los datos que contienen los nuevos valores
 * @returns {Promise<User>} devuelve un objeto con los datos del usuario actualizado
 */
export const updateCredentialsByID = async (id, updates) => {
  return await User.findByIdAndUpdate(
    { _id: id },
    {
      username: updates.username,
      email: updates.email,
    },
    {
      new: true,
      runValidators: true
    }
  );
}

/**
 * Actualiza el campo de contraseña de un usuario segun su ID
 * @param {String} id recibe el ID del usuario al que se va a actualizar la contraseña
 * @param {String} newPassword recibe el nuevo valor de la contraseña
 */
export const changePasswordToUser = async (id, newPassword) => {
  const user = await User.findById(id);
  user.password = newPassword;
  await user.save();
}

/**
 * Elimina a un usuario por su ID
 * @param {String} id Recibe el id del usuario a eliminar
 * @returns {Promise<User|null>} El usuario eliminado, o null si hubo un error o no se encuentra
 */
export const deleteUserById = async (id) => {
  return await User.findByIdAndDelete(id);
};

/**
 * Devuelve todos los documentos de usuarios existentes, es decir,
 * todos los objetos de usuarios registrados con su informacion
 * correspondiente, en este caso se selecciona los campos que se 
 * requieren del usuario que son username y email
 * @returns {Promise<Array<user>>} devuelve una coleccion completa 
 * con todos los usuarios registrados en la bbdd
 */
export const getUsers = async () => {
  return await User.find({}).select('username email');
}