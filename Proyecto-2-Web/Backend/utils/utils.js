const convertID = (item) => {
    return item._id.toString().replace(/^new ObjectId\('(.+?)'\)$/, '$1');
} 

export default convertID;