import express from "express";
import {
  createseat,
  deleteseat,
  getseat,
  getseats,
  updateseat,
  updateseatAvailability,
} from "../controllers/seat.js";
import { verifyAdmin } from "../utils/verifyToken.js";

const router = express.Router();
//CREATE
router.post("/:vehicleid", verifyAdmin, createseat);

//UPDATE
router.put("/availability/:id", updateseatAvailability);
router.put("/:id", updateseat);
//DELETE
router.delete("/:id/:vehicleid",  deleteseat);
//GET

router.get("/:id", getseat);
//GET ALL

router.get("/", getseats);

export default router;
