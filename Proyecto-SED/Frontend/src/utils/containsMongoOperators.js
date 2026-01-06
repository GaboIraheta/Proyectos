export default function containsMongoOperators(input) {
    const mongoOperators = ['$ne', '$eq', '$gt', '$lt', '$gte', '$lte', '$in', '$nin',
        '$or', '$and', '$not', '$nor', '$exists', '$type', '$expr', '$jsonSchema', '$mod',
        '$regex', '$text', '$where', '$geoIntersects', '$geoWithin', '$near', '$nearSphere',];
    return mongoOperators.some(op => input.includes(op));
}