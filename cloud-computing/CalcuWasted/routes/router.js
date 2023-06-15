const express = require("express");
const router = express.Router();
const db = require("../config/database");
const { checkToken } = require("../middleware/auth-validation");
const { check, validationResult } = require('express-validator');
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const axios = require("axios");

//Register
router.post("/register",
[
  // Validate Email
  check("email", "Isi dengan email yang valid.").isEmail().normalizeEmail({ gmail_remove_dots: true }),
],
[
  // Validate Password
  check("password", "Password must be filled in at least 8 characters").isLength({ min: 8 })
],
    (req, res, next) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({
                error: true,
                message: "Fill it with a valid input"
            });
        }
        
        // check name, email and password filled
        const { name, email, password } = req.body;
        if (!name || !email || !password) {
          return res.status(400).json({
            error: true,
            message: "Please enter your data!",
          });
        }

    db.query(`SELECT * FROM tableUsers WHERE LOWER(email) = LOWER(${db.escape(req.body.email)});`, (err, result) => {
        if (err) {
          return res.status(500).json({
              error: true,
              message: err,
          });
        }   
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
                    db.query(`INSERT INTO tableUsers (name, email, password) VALUES (${db.escape(req.body.name)}, ${db.escape(req.body.email)}, ${db.escape(hash)});`, (err, result) => {
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

//Login
router.post('/login',
[
  // Validate Email
  check("email", "Isi dengan email yang valid.").isEmail().normalizeEmail({ gmail_remove_dots: true }),
],
    (req, res, next) => {
      const errors = validationResult(req);
      if (!errors.isEmpty()) {
          return res.status(400).json({
              error: true,
              message: "Fill it with a valid input"
          });
      }
    
    // check email and password filled
    const { email, password } = req.body;
    if (!email || !password) {
      return res.status(400).json({
        error: true,
        message: "Please enter your email or password",
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
  
      // Checks if the email is not found in the database
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
          result: { 
            id:result[0].id,
            name:result[0].name,
            email:result[0].email,
            token
          }
        });
      });
    });
  });

//News
router.get('/news', checkToken, async (req, res) => {
  const q = 'sampah plastik'; //Keyword
  const language = 'id'; // Bahasa Indonesia
  const apiKey = process.env.API_NEWS;

  try {
    const response = await axios.get('https://newsapi.org/v2/everything', {
      params: {
        q,
        language,
        apiKey,
      },
    });

    const newsArticles = response.data.articles;
    res.status(200).json({
      success: true,
      news: newsArticles
    });

  } catch (error) {
    console.error('Error fetching news:', error);
    res.status(500).json({
      success: false,
      message: 'An error occurred while fetching news'
    });
  }
});

// Nearby Recycling Bank
router.get('/location', checkToken, async (req, res) => {
  try {
    const apiKey = process.env.API_MAPS;

    // GET Location Coordinate User
    const geolocationResponse = await axios.post(`https://www.googleapis.com/geolocation/v1/geolocate?key=${apiKey}`);
    const { location } = geolocationResponse.data;
    const { lat, lng } = location;
    const radius = 3000; // Radius (Meter)

    // GET Nearby Recycling Bank
    const placesResponse = await axios.get('https://maps.googleapis.com/maps/api/place/nearbysearch/json', {
      params: {
        key: apiKey,
        location: `${lat},${lng}`,
        radius: radius,
        keyword: 'bank sampah'
      }
    });

    const results = placesResponse.data.results;
    res.status(200).json({
      success: true,
      location: results
    });
  } catch (error) {
    console.error('Error:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

module.exports=router;