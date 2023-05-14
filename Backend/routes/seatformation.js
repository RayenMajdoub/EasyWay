import express from "express";

import {
    createseatformation,
    updateseatformation,
    deleteseatformation,
    getseatformation,
    getseatformations,

} from "../controllers/seatformation.js"

const router = express.Router();
//CREATE
router.post("/",  createseatformation);

//UPDATE
router.put("/:id", updateseatformation);
//DELETE
router.delete("/:id/:seatformationid", deleteseatformation);
//GET

router.get("/:id", getseatformation);
//GET ALL

router.get("/", getseatformations);

export default router;


//cus_MxkG2fgPmlPQ1d