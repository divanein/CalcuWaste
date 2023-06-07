const mysql = require("mysql");

// Configure Database
const dbconn = mysql.createConnection({
    port : 3306,
    host: "34.101.116.161",
    user :"root",
    password :"calcuwaste",
    database : "calcuwaste",
    multipleStatements: true,
});

// Connect Database
dbconn.connect((err) => {
    if (err) throw err;
    console.log("Database connected.");
});

module.exports=dbconn;