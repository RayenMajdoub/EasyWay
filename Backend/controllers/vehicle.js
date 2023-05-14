import vehicle from "../models/Vehicle.js";

export const createvehicle = async (req, res, next) => {
  const newvehicle = new vehicle(req.body);

  try {
    const savedvehicle = await newvehicle.save();
    res.status(200).json(savedvehicle);
  } catch (err) {
    next(err);
  }
};
export const updatevehicle = async (req, res, next) => {
  try {
    const updatedvehicle = await vehicle.findByIdAndUpdate(
      req.params.id,
      { $set: req.body },
      { new: true }
    );
    res.status(200).json(updatedvehicle);
  } catch (err) {
    next(err);
  }
};
export const deletevehicle = async (req, res, next) => {
  try {
    await vehicle.findByIdAndDelete(req.params.id);
    res.status(200).json("vehicle has been deleted.");
  } catch (err) {
    next(err);
  }
};
export const getvehicle = async (req, res, next) => {
  try {
    const vehicle = await vehicle.findById(req.params.id);
    res.status(200).json(vehicle);
  } catch (err) {
    next(err);
  }
};
export const getvehicles = async (req, res, next) => { //To Do Filter by 
  const { min, max, ...others } = req.query;
  try {
    const vehicles = await vehicle.find({
      ...others,
      cheapestPrice: { $gt: min | 1, $lt: max || 999 },
    }).limit(req.query.limit);
    res.status(200).json(vehicles);
  } catch (err) {
    next(err);
  }
};


export const countByCity = async (req, res, next) => {
  const cities = req.query.cities.split(",");
  try {
    const list = await Promise.all(
      cities.map((city) => {
        return vehicle.countDocuments({ city: city });
      })
    );
    res.status(200).json(list);
  } catch (err) {
    next(err);
  }
};
export const countByType = async (req, res, next) => {
  try {
    const vehicleCount = await vehicle.countDocuments({ type: "vehicle" });
    const busCount = await vehicle.countDocuments({ type: "bus" });
    const trainCount = await vehicle.countDocuments({ type: "train" });
    const MotocycleCount = await vehicle.countDocuments({ type: "Motocycle" });
    

    res.status(200).json([
      { type: "vehicle", count: vehicleCount },
      { type: "bus", count: apartmentCount },
      { type: "trains", count: trainCount },
      { type: "Motocycles", count: MotocycleCount },
    ]);
  } catch (err) {
    next(err);
  }
};

export const getvehicleseats = async (req, res, next) => {
  try {
    const vehicle = await vehicle.findById(req.params.id);
    const list = await Promise.all(
      vehicle.seats.map((seat) => {
        return seat.findById(seat);
      })
    );
    res.status(200).json(list)
  } catch (err) {
    next(err);
  }
};
