import User from "../models/user.model.js";

export const findUserById = async (id) => {
    return await User.findById(id)
}

export const findUserByEmail = async (email) => {
    return await User.findOne({ email });
}

export const createUser = async ({ username, email, password }) => {
    const user = new User({ username, email, password });
    return await user.save();
}

export const updatePremium = async (id, _isPremium) => {

    return await User.findByIdAndUpdate(
        { _id : id },
        {
            isPremium : _isPremium
        },
        {
            new : true,
            runValidators : true
        }
    );
}

export const updateCredentials = async (id, credentials) => {

    return await User.findByIdAndUpdate(
        { _id : id },
        {
            username : credentials.username,
            email : credentials.email  
        },
        { 
            new : true,
            runValidators : true
        }
    );
}

export const updatePassword = async (id, newPassword) => {

    const user = await User.findById(id);
    user.password = newPassword;
    
    return await user.save();
} 

export const updateMolarMassList = async (id, newMass) => {

    const user = await User.findById(id);
    user.molarMassList.push(newMass);

    return await user.save();
}

export const deleteMolarMass = async (userId, molarMassId) => {

    const user = await User.findById(userId);

    const length = user.molarMassList.length;

    user.molarMassList = user.molarMassList.filter(
        (mass) => mass._id.toString() != molarMassId
    )

    if (user.molarMassList.length === length) 
        throw new Error("Masa molar no existente en la lista");

    return await user.save();
}