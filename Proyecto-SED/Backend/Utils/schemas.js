const create_schemas = (_required, _properties) => {

    return {
        validator : {
            $jsonSchema : {
                bsonType : "object",
                required : _required,
                properties : _properties
            }
        }
    }
}

export default create_schemas;