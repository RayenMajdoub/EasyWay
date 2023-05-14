import express from "express";
import { login, register ,SendResetCode,ResetPassword,UpdateProfile } from "../controllers/auth.js";

const router = express.Router();

router.post("/register", register)
router.post("/login", login)
router.post("/sendCode", SendResetCode)
router.put("/resetPassword",ResetPassword)
router.put("/updateProfile",UpdateProfile)
export default router
