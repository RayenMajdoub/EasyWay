import mongoose from "mongoose";
const seatSchema = new mongoose.Schema(
  {

    vehicle :{
        type : String ,
    }
     ,
    BusinessClassSeats : {
        type : Number

    } , 
    FirstClassSeats : { 
        type : Number

    },

    EconomyClassSeats : {
        type : Number
    } , 

    fullrows : {
        type : Number 
    } , 

    FullLines : {
        type : Number
    } , 

    EmptyRows : [  {
        type : Number 
    }] ,
    EmptyLines : [  {
        type : Number 
    }]



  },

);

export default mongoose.model("seatformation", seatSchema);
