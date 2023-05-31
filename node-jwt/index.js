const express=require("express");
const jawt=require("jsonwebtoken");

const app=express();

app.use(express.json())

app.get('/',(req, res)=>{
    res.json({
    message:"anda berada pada root"
    
    })
})

app.post('/', verifyUser,(req,res)=>{
    res.json({
        message:"post tercreate",
        data:req.body
    })

})

app.post('/login',(req,res)=>{
    const user={
        id:1,
        username : 'ujicoba',
        email:"email@example.com"
    }
    jawt.sign(user,'secret',{expiresIn:'30s'},(err,token)=>{
        if(err){
            console.log(err);
            res.sendStatus(304);
            return
        }
        const Token=token;
        res.json({
            user:user,
            token:Token
        });
    })
})

function verifyUser(req,res,next){
    const bearer=req.headers.bearer;
    jawt.verify(bearer,'secret',(err,data)=>{
        if(err){
            console.log(err.message);
            res.json(err);
            return
        }
        req.body= data
        next()
    })
}

app.listen(1500,()=>{
    console.log("anda berada di port 1500");
    "aplikasi berjalan pada port 1500";
})