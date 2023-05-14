import seatformation from "../models/Seatformation.js";



export const createseatformation = async (req, res, next) => {
    const newformation = new seatformation(req.body);

    try {
        const savedformation = await newformation.save();
        res.status(200).json(savedformation);
    } catch (err) {
        next(err);
    }
};




export const updateseatformation = async (req, res, next) => {
    try {
        const updatedformation = await seatformation.findByIdAndUpdate(
            req.params.id,
            { $set: req.body },
            { new: true }
        );
        res.status(200).json(updatedformation);
    } catch (err) {
        next(err);
    }
};


export const deleteseatformation = async (req, res, next) => {
    try {
        await seatformation.findByIdAndDelete(req.params.id);
        res.status(200).json("seat formation has been deleted.");
    } catch (err) {
        next(err);
    }
};

export const getseatformation = async (req, res, next) => {
    try {
        const formation = await seatformation.findById(req.params.id);
        res.status(200).json(formation);
    } catch (err) {
        next(err);
    }
};
export const getseatformations = async (req, res, next) => {
    try {
        const formations = await seatformation.find();
        
        
        res.status(200).json(formations);
    } catch (err) {
        next(err);
    }
};