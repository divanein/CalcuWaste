<p align="center">
  <img src="https://github.com/divanein/CalcuWaste/assets/100034010/13586daa-cd2c-465e-aa71-3fa2fb964655" width="600" height="303-"/>
</p>

# Cloud Computing Implementation
The Cloud Computing learning path part of the app is building APIs for authentication for the registration and login for the user. Other than that, the application expected to catch the image captured and able to prcess using the classification model that already made by the Machine Learning path.

## API Url
[Calcuwaste](https://backend-dot-bangkit-project-386513.et.r.appspot.com) <br>
[Machine Learning](https://calcuwastepredict-awxuqbcrua-et.a.run.app)

## Calcuwaste Endpoint Documentation
### [Calcuwaste](https://backend-dot-bangkit-project-386513.et.r.appspot.com) <br>
The API code for Calcuwaste is built using Node.js with the Express framework. We use several dependencies such as bcryptjs to encrypt passwords, jsonwebtoken to generate tokens, and mysql to establish a connection to Cloud SQL. For deployment, we use App Engine as a computing service and Cloud SQL as a database. <br><br>
|  Endpoint |  Method	     |      Query Request |           Description          |
| :----: | :------------: | :-----------------: | :------------------------: |
| /api/register | POST   | name, email and password      | POST request for Register to Calcuwaste |
| /api/login | POST   | email and password      | POST request for Login to Calcuwaste |
| /api/news | GET   | q, language, and apiKey      | GET request for news related to Environment |
| /api/location | GET   | keyword, lat, lng, radius and apiKey      | GET request for nearest recycling bank |

*For the /api/news and /api/location we use a third party, namely the News API and API Google Maps because it is very easy to use and free so there is no need to incur costs and it is very effective to use*

### [Machine Learning](https://calcuwastepredict-awxuqbcrua-et.a.run.app) <br>
The API for machine learning is built using Python with the Flask framework. For deployment, we use App Engine as a computing service. <br><br>
|  Endpoint |  Method	     |      Query Request |           Description          |
| :----: | :------------: | :-----------------: | :------------------------: |
| /predict | POST   | weight      | POST request for calculate the waste |

### Security
For security, the protection used namely the JWT Token, will expire every time the user logs out and will receive a new token when logging in.
