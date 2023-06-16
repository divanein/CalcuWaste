const mysql = require("mysql");

// Configure Database
const pool = mysql.createConnection({
    port : process.env.DB_PORT,
    host : process.env.DB_HOST,
    user : process.env.DB_USER,
    password : process.env.DB_PASSWORD,
    database : process.env.DB_DATABASE,
    multipleStatements: true,
});

// Connect Database
pool.connect((err) => {
    if (err) throw err;
    console.log("Database connected.");
});

module.exports=pool;