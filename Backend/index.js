import express from "express";
import dotenv from "dotenv";
import mongoose from "mongoose";
import authRoute from "./routes/auth.js";
import seatformationRoute from "./routes/seatformation.js"
import usersRoute from "./routes/users.js";
import vehiclesRoute from "./routes/vehicles.js";
import seatsRoute from "./routes/seats.js";
import voyageroute from "./routes/voyage.js"
import reservationroute from "./routes/reservation.js"
import cookieParser from "cookie-parser";
import payementroute from "./routes/payement.js"
import dayjs from "dayjs"
import cors from "cors";
import ISODate from "isodate" ; 
import morgan from "morgan";

const PORT = process.env.PORT ||  3000
const app = express();
dotenv.config();
app.use(morgan("tiny"));
export const day= dayjs(new Date ).format('YYYY-MM-DDThh:mm:ss.00Z') ;
export const connect = async () => {
  try {
    mongoose.set("strictQuery", false);
    await mongoose.connect(process.env.MONGO);
    console.log("Connected to mongoDB.");
  } catch (error) {
    throw error;
  }
};

mongoose.connection.on("disconnected", () => {
  console.log("mongoDB disconnected!");
});







//middlewares
app.use(cors())
app.use(cookieParser())
app.use(express.json());

app.use("/api/auth", authRoute);
app.use("/api/users", usersRoute);
app.use("/api/vehicles", vehiclesRoute);
app.use("/api/seats", seatsRoute);
app.use("/api/seatformation",seatformationRoute );
app.use("/api/voyage",voyageroute );
app.use("/api/reservation",reservationroute );
app.use("/api/payement",payementroute );

app.use((err, req, res, next) => {
  const errorStatus = err.status || 500;
  const errorMessage = err.message || "Something went wrong!";
  return res.status(errorStatus).json({
    success: false,
    status: errorStatus,
    message: errorMessage,
    stack: err.stack,
  });
});

app.listen(PORT,'0.0.0.0',() => {
  connect();
  console.log("Connected to backend.",ISODate(new Date ) , new Date().getTime()  );
});
