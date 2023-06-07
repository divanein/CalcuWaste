const { check } = require("express-validator");

// Validate Register
exports.registerValidation = [
    check("email", "Fill it with a valid email").isEmail().normalizeEmail({ gmail_remove_dots: true }),
    check("password", "Password must be filled in at least 8 characters").isLength({ min: 8 }),
];

// Validate Login
exports.loginValidation = [
    check("email", "Fill it with a valid email").isEmail().normalizeEmail({ gmail_remove_dots: true }), 
    check("password","Password must be filled in at least 8 characters").isLength({ min: 8 }),
];