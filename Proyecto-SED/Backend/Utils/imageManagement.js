import fs from 'fs/promises';
import fsAsync from 'fs';
import path from 'path';

// Recibe el directorio en donde se va a poner la imagen y recibe la imagen directamente de la request 
// como un tipo de dato file
const sanitize = str => str.toLowerCase().replace(/[^a-z0-9]/g, "_");

export const saveImage = (directory, name, imageFile) => {
    
    const folder = path.join(process.cwd(), directory);

    if (!fsAsync.existsSync(folder))
        fsAsync.mkdirSync(folder, { recursive: true });

    const mimeType = imageFile.match(/data:(image\/.+);base64/)[1];
    const extension = mimeType.split("/")[1];

    const fileName = `${sanitize(name)}_${Date.now()}.${extension}`;

    const base64 = imageFile.split(",")[1];
    const buffer = Buffer.from(base64, "base64");

    const filePath = path.join(folder, fileName);
    
    fsAsync.writeFileSync(filePath, buffer);

    return `${directory}/${fileName}`;
}

export const getImage = async (_path) => {

    const imagePath = path.join(process.cwd(), _path);
    const imageFile = await fs.readFile(imagePath);

    return imageFile; // esto envia una imagen como Buffer, podriamos decir que se envian los bytes del archivo
}

// para manejarlo en el cliente
// se hace el fetch, a la response obtenida se le hable un .blob()
// se utiliza URL.createObjectURL(blob) para crear una ruta temporal en el cliente

export const deleteImage = async (_path) => {

    try {

        const imagePath = path.join(process.cwd(), _path);
        await fs.unlink(imagePath);

    } catch (error) {
        throw new Error("No se ha podido completar la operacion.");
    }
}