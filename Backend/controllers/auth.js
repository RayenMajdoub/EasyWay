import User from "../models/user.js";
import bcrypt from "bcryptjs";
import { createError } from "../utils/error.js";
import jwt from "jsonwebtoken";
import nodemailer from 'nodemailer';
import fs from 'fs';
import handlebars from 'handlebars';
import path from 'path';
import mime from 'mime-types';

export const register = async (req, res, next) => {
  try {
    console.log(req.body)
    const salt = bcrypt.genSaltSync(10);
    const hash = bcrypt.hashSync(req.body.password, salt);
    console.log(req.body)
    const usernamecheck = await User.findOne({ username: req.body.username });
    const emailcheck = await User.findOne({  email : req.body.email});
    const message = "User has been created."
   console.log("hello im first log")
    const newUser = new User({  
      ...req.body,
      password: hash,
    });
    if (usernamecheck) return next(createError(404,"username alredy exist ")) ; 
    if (emailcheck) return next(createError(404,"email alredy exist ")) ; 
   let user = await newUser.save();
     console.log(user +"user saved ")

    res.status(200).json(user);
  } catch (err) {
    next(err);
  }
};
export const login = async (req, res, next) => {
  try {
    const user = await User.findOne({ email: req.body.email },{createdAt:0,updatedAt:0,__v:0});
    if (!user) return next(createError(404, "User not found!"));

    const isPasswordCorrect = await bcrypt.compare(
      req.body.password,
      user.password
      
    );
    if (!isPasswordCorrect)
      return next(createError(400, "Wrong password or email!"));

    const token = jwt.sign(
      { id: user._id },
      process.env.JWT
    );
    const { password, ...otherDetails } = user._doc;
    const userr=user._doc;
    var data = {
      username :user.username,token , role:user.role
    };
    console.log(data)

    res
      .cookie("access_token", token, {
        httpOnly: true,
      })
      .status(200)
      .json( data );
  } catch (err) {
    next(err);
  }
};

const transporter = nodemailer.createTransport({
  service: 'Gmail',
  auth: {
    user: 'noreplyeasywayapp@gmail.com',
    pass: 'shqzllrqkymmgvnv'
  }
});
function generateCode() {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let code = '';
  for (let i = 0; i < 5; i++) {
    code += chars.charAt(Math.floor(Math.random() * chars.length));
  }
  return code;
}
// Register the Handlebars view engine with nodemailer

export const SendResetCode = async (req, res) =>{
  const email = req.body.email;
  const code = generateCode();
  const html = fs.readFileSync('C:/Users/Rayen/Desktop/backendminiprojet_versionfinal/mail/verifCode.html', 'utf-8');
 // const  template1 = embedImagesIntoHtmlTemplate('C:/Users/Rayen/Desktop/backendminiprojet_versionfinal/mail/verifCode.html','C:/Users/Rayen/Desktop/backendminiprojet_versionfinal/mail/images')
  // Compile the template using Handlebars
  const emailcheck = await User.findOne({  email : email});
  if (emailcheck) {
    const template = handlebars.compile(html, { noEscape: true });
    const mailOptions = {
    from: 'noreplyeasywayapp@gmail.com',
    to: email,
    subject: 'Reset password code',
    template: template({ Code: code }),
    context: {
      Code: code
    },
  };
  transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
      res.status(500).send({ success: false, message: error,code: code });
    } else {
      res.status(200).json({ success: true, message: 'Code sent successfully.' , code: code});
    }
  });
  }else
  {
    return next(createError(404,"email alredy exist ")) ; 
  }
  
}

export const ResetPassword = async (req, res)=>{
  try {
  const email = req.body.email;
  const newpassword = req.body.newpassword;
  const salt = bcrypt.genSaltSync(10);
  const hash = bcrypt.hashSync(newpassword, salt);
  console.log(hash)
  const user = await User.findOne({ email: email });
if (user) {
  user.password = hash;
  await user.save();
  res.status(200).json({message:`User with email ${email} updated successfully.`});
} else {
  res.status(404).json({message : `User with email ${email} not found.`});
}
  }catch(err)
  {
    res.status(500).json(err);
  }
}
export const UpdateProfile = async (req,res)=>
{  try{
  const email = req.body.email;
  const username = req.body.username
  const password = req.body.password
  const salt = bcrypt.genSaltSync(10);
  const hash = bcrypt.hashSync(password, salt);
 const user = await User.findOne({ email: email });
 if (user) {
  user.password = hash;
  user.username = username;
  await user.save();
  res.status(200).json({message:`User with email ${email} updated successfully.`});
} else {
  res.status(404).json({message : `User with email ${email} not found.`});
}

  }catch(err)
  {
    res.status(500).json(err);
  }
}

