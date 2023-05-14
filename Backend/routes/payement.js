import express from "express";
import {
  payement
} from "../controllers/payement.js";
const router = express.Router();

//payement

router.post("/payment-sheet/:amount",payement);

export default router;
