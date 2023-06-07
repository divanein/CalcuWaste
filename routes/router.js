const express = require("express");
const router = express.Router();
const db = require("../config/database");
const { registerValidation, loginValidation } = require("../middleware/auth-validation");
const { validationResult } = require('express-validator');
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

//Registrasi
router.post("/register", 
    registerValidation,
    (req, res, next) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({
                error: true,
                message: "Fill it with a valid email"
            });
        }
        
        // check email and password filled
        const { email, password } = req.body;
        if (!email || !password) {
          return res.status(400).json({
            error: true,
            message: "Please enter email and password",
          });
        }

    db.query(`SELECT * FROM tableUsers WHERE LOWER(email) = LOWER(${db.escape(req.body.email)});`, (err, result) => {
        // Email already registered!
        if (result.length) {
            return res.status(409).send({
                error: true,
                message: "This account already exists!",
            });
        } else {
            bcrypt.hash(req.body.password, 10, (err, hash) => {
                if (err) {
                    return res.status(500).send({
                        error:true,
                        message: err,
                    });
                } else {
                    // Hash Password -> Add to DB
                    db.query(`INSERT INTO tableUsers (email, password) VALUES (${db.escape(req.body.email)}, ${db.escape(hash)});`, (err, result) => {
                        if (err) {
                            throw err;
                            return res.status(400).send({
                                message: err,
                            });
                        }
                        return res.status(201).send({
                            error: false,
                            message: "Account successfully registered",
                        });
                    });
                }
            });
        }
    });
});
router.post('/login', 
    loginValidation,
    (req, res, next) => {
      const errors = validationResult(req);
      if (!errors.isEmpty()) {
          return res.status(400).json({
              error: true,
              message: "Fill it with a valid email"
          });
      }
    
    // check email and password filled
    const { email, password } = req.body;
    if (!email || !password) {
      return res.status(400).json({
        error: true,
        message: "Please enter email and password",
      });
    }
  
    // Check the match of email and password in the database
    db.query(`SELECT * FROM tableUsers WHERE email = ${db.escape(email)};`, (err, result) => {
      if (err) {
        console.error("Error fetching user data: ", err);
        return res.status(500).json({
          error: true,
          message: "An error occurred while processing the request",
        });
      }
  
      // Checks if the email is found in the database
      if (result.length === 0) {
        return res.status(401).json({
          error: true,
          message: "Wrong email or password",
        });
      }
  
      // Verification password
      const user = result[0];
      bcrypt.compare(password, user.password, (bcryptErr, isMatch) => {
        if (bcryptErr) {
          console.error("Error verifying password: ", bcryptErr);
          return res.status(500).json({
            error: true,
            message: "An error occurred while processing the request",
          });
        }
  
        if (!isMatch) {
          return res.status(401).json({
            error: true,
            message: "Wrong email or password",
          });
        }
  
        
        // Make token JWT
        const token = jwt.sign({ id: user.id }, 'secret_key');
  
        // Send a token in response
        return res.status(200).json({
          error: false,
          message: "Successfully logged in",
          result: 
          { id:result[0].id,
          email:result[0].email,
          token
          }
        });
      });
    });
  });
  
module.exports=router;