const employee_required = ["name", "email", "password", "role", "active"];

const employee_properties = {

    name : { bsonType : "string" },
    email : {
        bsonType : "string",
        pattern : "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$",
    },
    password : {
        bsonType : "string",
        minLength: 16
    },
    role : { bsonType : "bool" }, //es admin o empleado normal
    active : { bsonType : "bool" },
}

export default { employee_required, employee_properties };