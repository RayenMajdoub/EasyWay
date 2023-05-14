import mongoose from "mongoose";
const vehicleSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
  },
  type: {
    type: String,
    required: true,
  },
   /*photos: {
    type: [String],
  },

  rating: {
    type: Number,
    min: 0,
    max: 5,
  },*/

  /* cheapestPrice: {
    type: Number,
    required: true,
  },
 featured: {
    type: Boolean,
    default: false,
  },*/
  gridformationid : {
    type : String 
  }
});

export default mongoose.model("vehicle", vehicleSchema)