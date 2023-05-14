import mongoose from "mongoose";
const reservationSchema = new mongoose.Schema(
  {
  
    user : {
        type : mongoose.Schema.Types.ObjectId , 
        ref : "User"  
    }
    ,
    Seatnumbers :{ 
         type :[Number] 
     } 
    , 
    voyage : {
        type : mongoose.Schema.Types.ObjectId ,
        ref: "voyage" 
    } , 
    qr : {
        type : String 
    } , 
    totaleprice : {
        type : Number 
    }

  }

);

export default mongoose.model("reservation", reservationSchema);
