import express from "express";
import {
  countByCity,
  countByType,
  createvehicle,
  deletevehicle,
  getvehicle,
  getvehicleseats,
  getvehicles,
  updatevehicle,
} from "../controllers/vehicle.js";
import {verifyAdmin} from "../utils/verifyToken.js"
const router = express.Router();

//CREATE
router.post("/",  createvehicle);

//UPDATE
router.put("/:id", verifyAdmin, updatevehicle);
//DELETE
router.delete("/:id", verifyAdmin, deletevehicle);
//GET

router.get("/find/:id", getvehicle);
//GET ALL

router.get("/", getvehicles);
router.get("/countByCity", countByCity);
router.get("/countByType", countByType);
router.get("/seat/:id", getvehicleseats);

export default router;
