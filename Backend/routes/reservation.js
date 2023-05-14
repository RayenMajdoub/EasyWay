import express from "express";
import {
  createreservation,
  deletereservation,
  getreservation,
  getAll
} from "../controllers/reservation.js";

const router = express.Router();

//CREATE
router.post("/",  createreservation);


//DELETE
router.delete("/:id",  deletereservation);
//GET

router.get("/find/:id", getreservation);

//GET ALL
//           
router.get("/allreservations/:user_id", getAll);

export default router;
