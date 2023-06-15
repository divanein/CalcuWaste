const { verify } = require("jsonwebtoken");

module.exports = {
// Validate Token
checkToken: (req, res, next) => {
    let token = req.get("authorization");
    if(token){
       token = token.slice(7);
       verify(token, 'secret_key', (err, decoded) => {
           if(err){
               res.json({
                 success:0,
                 message:"Invalid token"
               });
           } else{
               req.decoded = decoded;
               next();
           }
       })
    } else {
       res.json({
           success:0,
           message:"Access Denied! unauthorization user"
       }); 
    }
}
};
