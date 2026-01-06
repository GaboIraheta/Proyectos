const product_required = ["name", "price", "active", "image"];

const product_properties = {

    name : { bsonType : "string"},
    description : { bsonType : "string" },
    category : { bsonType : "string" },
    price : { 
        bsonType : ["int", "double", "float"],
        minimum : 0,
    },
    stock : { 
        bsonType : ["int", "double"],
        minimum : 0,
    },
    active : { bsonType : "bool" },
    image : { // este campo almacena la ruta de la imagen en el servidor
        bsonType : "string",
    }
}

export default { product_required, product_properties };