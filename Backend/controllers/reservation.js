import reservation from "../models/Reservation.js";
import voyage from "../models/Voyage.js";
import User from "../models/user.js";




export const createreservation = async (req, res, next) => {

  try {
    var savedid =""
    const user = await User.findOne({email:req.body.user}) 
    console.warn(user._id)
    const newreservation = new reservation({Seatnumbers:req.body.Seatnumbers
      ,user:user._id
      ,voyage:req.body.voyage,totaleprice:req.body.totaleprice});
    var updatevoyage = await voyage.findById(req.body.voyage);
    for (const index2 in newreservation.Seatnumbers) {
          if (updatevoyage.available[newreservation.Seatnumbers[index2]] == false) {
            console.log("already reserved")  ;
          } else {
         var savedreservation = await newreservation.save();
            let stJSON = JSON.stringify(savedreservation);
              const update = await reservation.findByIdAndUpdate(savedreservation._id, {qr:stJSON});
            updatevoyage.available[newreservation.Seatnumbers[index2]] = false;
            const updatedvoyage =   await  updatevoyage.save();
            savedid=savedreservation._id
          }
        }
  
    return res.status(200).json({id:savedid});
  } catch (err) {
    next(err);
  }
  
};

export const updatereservation = async (req, res, next) => {
  try {
    const updatedreservation = await reservation.findByIdAndUpdate(
      req.params.id,
      { $set: req.body },
      { new: true }
    );
    res.status(200).json(updatedreservation);
  } catch (err) {
    next(err);
  }
};
export const deletereservation = async (req, res, next) => {
  try {
    await reservation.findByIdAndDelete(req.params.id);
    res.status(200).json("reservation has been deleted.");
  } catch (err) {
    next(err);
  }
};
export const getreservation = async (req, res, next) => {
  console.log(req.params.id)
  const reservations = await reservation
    .findById(req.params.id)
    .populate("user")
    .populate({
      path: "voyage",
      ref:"voyage",
      populate: {
        path: "vehicle",
        model: "vehicle",
      },
    })
    .exec()
    .then((reservations) => {
    return res.status(200).json(reservations);
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({ error: err });
    });
};
export const getAll = async (req, res, next) => {
  console.log("aaaaaaaaa");
  const user = await User.find({email:req.params.user_id})
  console.log(user)
  const voy = await reservation
    .find({})
    .where('user._id').equals(user._id)
    .populate("user")
    .populate({
      path: "voyage",
      populate: {
        path: "vehicle",
        model: "vehicle",
      },
    })
    .exec()
    .then((voy) => {
      console.log(voy)
      res.status(200).json(voy);
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({ error: err });
    });
};

function encode_base64(code) {
return  Buffer.from(code, 'utf8').toString('base64')
}

// from base64 to actual image 
function base64_decode(base64Image, file) {
  const buffer = Buffer.from(base64Image,"base64");
  fs.writeFileSync(file,buffer);
   console.log('******** File created from base64 encoded string ********');

}
