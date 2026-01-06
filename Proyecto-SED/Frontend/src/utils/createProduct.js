export default async function createProduct(product) {
    // product may come from the backend (image as URL string or {file, pathBBDD})
    // or from a file input (image: { file: File, ... }).
    try {
        if (!product) return null;

        const {
            name,
            description,
            category,
            price,
            stock,
            active,
            image,
            _id,
        } = product;

        let url = null;
        let pathBBDD = null;

        // Handle different image formats:
        // 1. image is a string (from cart API: direct base64 or URL)
        // 2. image is an object {file: string, pathBBDD: string} (from products API)
        if (typeof image === 'string') {
            // Direct string URL or base64
            url = image.startsWith('data:') ? image : `data:image/jpeg;base64,${image}`;
        } else if (typeof image === 'object' && image !== null) {
            // Object with file and pathBBDD
            if (typeof image.file === 'string') {
                const base64Data = image.file;
                url = base64Data.startsWith('data:') ? base64Data : `data:image/jpeg;base64,${base64Data}`;
            }
            pathBBDD = image.pathBBDD;
        }

        return {
            _id,
            name,
            description,
            category,
            price,
            stock,
            active,
            image: { 'file': url, 'pathBBDD': pathBBDD },
        };
    } catch (error) {
        console.error('Error creando el objeto product:', error);
        return null;
    }
}