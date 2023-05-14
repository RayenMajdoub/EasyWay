import mongoose from "mongoose";
const seatSchema = new mongoose.Schema(
  {
  
    vehicle : {
        type : mongoose.Schema.Types.ObjectId , 
        ref : "vehicle" , 
    }
    ,
    DeparturePoint : {
        type : String

    } , 
    ArrivalPoint : { 
        type : String

    },

    DepartureDate : {
        type : Date
    } , 

    ArrivalDate : {
        type : String 
    } , 

    Distance : {
        type : Number
    } , 
    firstclassseatprice : {
        type : Number
    } ,
    economyseatprice : {
        type : Number
    } ,
    businessseatprice : {
        type : Number
    } ,  available : {
        type : [Boolean]
    } 
  }

);

export default mongoose.model("voyage", seatSchema);
