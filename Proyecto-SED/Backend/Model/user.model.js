import productModel from "./product.model.js";

const user_required = ["name", "email", "password", "cart"];

const user_properties = {
    
    name : {
        bsonType : "string",
    },
    email : {
        bsonType : "string",
        pattern : /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
    },
    password : {
        bsonType : "string",
        minLength: 16
    },
    cart : {
        bsonType : "array",
        items : {
            bsonType : "object",
            required : productModel.product_required,
            properties : {
                _id : { bsonType : "ObjectId" },
                ...productModel.product_properties
            }
        }
    }
}

export default { user_required, user_properties };