// en el caso de la eliminacion de un producto se debe tambien eliminar del carrito de cualquier usuario que lo haya guardado
// y en el cliente se debe verificar que el producto exista en el mostrador del carrito
import productRepository from '../Repository/product.repository.js';
import { deleteImage, getImage, saveImage } from '../Utils/imageManagement.js';
import { 
    DataBaseFailed, 
    EmptyProducts, 
    GetRegistersNull, 
    NotAuthorized, 
    NotValidFormat, 
    NotValidID, 
    RegisterAlreadyExists, 
    RegisterNotDeleted, 
    RegisterNotFound, 
    RegisterNotInserted, 
    RegisterNotUpdated 
} from '../Error/error.js';
import { isValidID } from '../Utils/verify.js';

const add = async (request) => {

    // request = { data }

    try {

        // validaciones previas en middlewares
        // validar que toda la data de producto sea correcta
        // que el role sea empleado normal, no administrador ni usuario
        // que el empleado este autenticado

        const pathImage = saveImage("Uploads", request.name, request.image); // image debe ser tipo file object

        const productData = { ...request, image : pathImage };

        const result = await productRepository.add(productData);

        if (!result || !result.insertedId)
            throw new RegisterNotInserted("No se ha podido guardar el nuevo registro de producto.");

        const products = await productRepository.get();

        if (!products)
            throw new GetRegistersNull("No se ha podido obtener el registro de productos.");

        if (products.length == 0)
            throw new EmptyProducts("Registro de productos vacio.");

        return {
            products,
            message : "Producto agregado exitosamente." 
        };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotInserted ||
            error instanceof GetRegistersNull || error instanceof EmptyProducts || 
            error instanceof NotAuthorized || error instanceof RegisterAlreadyExists ||
            error instanceof NotValidFormat
        )
            throw error;

        throw new Error(`Error interno al agregar nuevo producto: ${error.message}.`);
    }
}

const update = async (request) => {     

    // request = { filter (puede ser el id de mongo email), updatedData }

    let updatedData;

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        // validaciones previas
        // que la data que se va a actualizar sea correcta
        // que el rol sea el de empleado normal
        // que este autenticado

        const productToUpdate = await productRepository.find(request.filter);
            
        if (!productToUpdate) 
            throw new RegisterNotFound("El registro de producto no existe.");

        if (!request.updatedData.image.pathBBDD) {

            deleteImage(productToUpdate.image);
            // el objeto image que devuelva el server con el get puede ser { pathBBDD, file }
            // del cliente vendria el producto con su campo image definido de la misma forma

            const newPath = saveImage("Uploads", request.updatedData.name ,request.updatedData.image.file);
            updatedData = { ...request.updatedData, image : newPath };
        
        } else updatedData = { ...request.updatedData, image : request.updatedData.image.pathBBDD };

        // en la request se manda la nueva info y el id como filter
        // se busca el registro en la bbbdd respecto del id
        // se accede a la ruta guardada y se compara con el campo pathBBB, que si es nueva se pone null desde el cliente
        // si es null entonces deleteImage
        // luego se toma el campo de image desde la request como file object y se convierte como en el caso de add
        // se realiza la modificacion en la bbdd con el repo de producto

        const result = await productRepository.update(request.filter, updatedData);

        if (result.modifiedCount == 0)
            throw new RegisterNotUpdated("No se ha podido actualizar los campos del registro.");

        return { message : "Producto actualizado exitosamente." };
        
    } catch (error) {

        if (error instanceof DataBaseFailed || 
            error instanceof RegisterNotUpdated || 
            error instanceof RegisterNotFound ||
            error instanceof RegisterAlreadyExists ||
            error instanceof NotValidFormat ||
            error instanceof NotValidID
        )
            throw error;

        throw new Error(`Error interno en la actualizacion del producto: ${error.message}.`);
    }
}

const get = async () => {

    try {

        // validaciones previas
        // que el rol sea de empleado normal
        // que este autenticado

        let products = await productRepository.get();

        if (!products)
            throw new GetRegistersNull("No se ha podido obtener el registro de productos.");

        if (products.length == 0)
            throw new EmptyProducts("Registro de productos vacio.");

        products = await Promise.all(products.map(async product => {

            const buffer = await getImage(product.image);

            return {
                ...product,
                image : {
                    pathBBDD : product.image,
                    file : buffer.toString("base64")
                }
            };
        }));

        return { products };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof NotAuthorized ||
            error instanceof GetRegistersNull || error instanceof EmptyProducts
        )
            throw error;

        throw new Error(`Error interno en la obtencion de los productos: ${error.message}.`);
    }
}

const enableDisableProduct = async (request) => {

    let active = request.active;

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();
        
        const result = await productRepository.enableDisableProduct(request.filter, request.active);


        if (result.modifiedCount == 0)
            throw new RegisterNotUpdated(`No se ha podido ${active ? "activar" : "desactivar"} el producto.`);

        return { message : `Producto ${active ? "activado" : "desactivado"} exitosamente.`};

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotUpdated || error instanceof NotValidID)
            throw error;

        throw new Error(`Error interno al ${active ? "activar" : "desactivar"} el producto: ${error.message}.`);
    }
}

const Delete = async (request) => {

    // request = { filter (id de mongo) }

    try {

        if (!isValidID(request.filter))
            throw new NotValidID();

        // validaciones previas
        // que el filter para acceder al registro sea valido de mongo
        // que el rol sea de empleado normal
        // que este autenticado

        const productToDelete = await productRepository.find(request.filter);

        deleteImage(productToDelete.image);

        const result = await productRepository.Delete(request.filter);


        if (result.deletedCount == 0) 
            throw new RegisterNotDeleted("No se encontro el producto para eliminar.");

        return { message : "Registro de producto eliminado exitosamente." };

    } catch (error) {

        if (error instanceof DataBaseFailed || error instanceof RegisterNotDeleted || 
            error instanceof NotAuthorized || error instanceof NotValidID
        )
            throw error;

        throw new Error(`Error en la eliminacion del registro de producto: ${error.message}.`);
    }
}

export default { add, update, get, enableDisableProduct, Delete };