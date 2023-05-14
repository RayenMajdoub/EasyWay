import seat from "../models/Seat.js";
import vehicle from "../models/Vehicle.js";
export const createseat = async (req, res, next) => {
  const vehicleId = req.params.vehicleid;
  const newseat = new seat(req.body);

  try {
    const savedseat = await newseat.save();
    try {
      await vehicle.findByIdAndUpdate(vehicleId, {
        $push: { seats: savedseat._id },
      });
    } catch (err) {
      next(err);
    }
    res.status(200).json(savedseat);
  } catch (err) {
    next(err);
  }
};

export const updateseat = async (req, res, next) => {
  try {
    const updatedseat = await seat.findByIdAndUpdate(
      req.params.id,
      { $set: req.body },
      { new: true }
    );
    res.status(200).json(updatedseat);
  } catch (err) {
    next(err);
  }
};
export const updateseatAvailability = async (req, res, next) => {
  try {
    await seat.updateOne(
      { "seatNumbers._id": req.params.id },
      {
        $push: {
          "seatNumbers.$.unavailableDates": req.body.dates
        },
      }
    );
    res.status(200).json("seat status has been updated.");
  } catch (err) {
    next(err);
  }
};
export const deleteseat = async (req, res, next) => {
  const vehicleId = req.params.vehicleid;
  try {
    await seat.findByIdAndDelete(req.params.id);
    try {
      await vehicle.findByIdAndUpdate(vehicleId, {
        $pull: { seats: req.params.id },
      });
    } catch (err) {
      next(err);
    }
    res.status(200).json("seat has been deleted.");
  } catch (err) {
    next(err);
  }
};
export const getseat = async (req, res, next) => {
  try {
    const seat = await seat.findById(req.params.id);
    res.status(200).json(seat);
  } catch (err) {
    next(err);
  }
};
export const getseats = async (req, res, next) => {
  try {
    const seats = await seat.find();
    res.status(200).json(seats);
  } catch (err) {
    next(err);
  }
};
