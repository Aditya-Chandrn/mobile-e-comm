# Modile E-Commerce Website
### Tech Stack: ReactJS & Spring Boot
_Guides after screenshots_
### - [Frontend Guide](#frontend)
### - [Backend Guide](#backend)
## Screenshots
### 1. Home Page 
![image](https://github.com/Aditya-Chandrn/mobile-e-comm/assets/103370641/0bf0d3ef-c9f0-4350-9f0e-52310ad2219b)
### 2. Product Page
![image](https://github.com/Aditya-Chandrn/mobile-e-comm/assets/103370641/8e7392a9-d0e9-42f7-89c5-360a09ca83d1)
### 3. Cart Page
![image](https://github.com/Aditya-Chandrn/mobile-e-comm/assets/103370641/fadb97d8-7293-4a38-94c0-7327a33dece7)
### 4. Order Page
![image](https://github.com/Aditya-Chandrn/mobile-e-comm/assets/103370641/6f599241-8948-40f5-b60b-7de412c05421)
### 5. Sell Page
![image](https://github.com/Aditya-Chandrn/mobile-e-comm/assets/103370641/00883755-4d4a-42a0-b7cd-f9333791d671)
### 6. Eureka Registry
![image](https://github.com/Aditya-Chandrn/mobile-e-comm/assets/103370641/489b4e48-a007-4b56-b882-4e3126658ef7)

## Frontend
### How to Run: 
1. Go to frontend directory: `cd frontend`
2. Install packages: `npm i <package_name>`
3. Run the website on localhost: `npm start`
### Packages:
- axios
- react-router-dom
### Routes:
1. Home (All Products): /
2. Individual Product Page: /product/:productId
3. Cart: /cart
4. Orders: /orders
5. Sell: /sell
#### *Except 1. and 2. all pages need user to be logged in*
6.
   - Login: /account/login
   - Signup: /account/sign-up

## Backend
### How to Run: 
1. Go to backend directory: `cd backend`
2. There are 5 modules: discovery_server, gateway, user, product, order. In the latter three, go to `/src/main/resources` and create a `.env` file. The structure of the `.env` is given below:
   ```
    MONGO_URI=<MONGODB_URI>
    MONGO_DATABASE=<DATABASE_NAME>
   ```
3. Now run the files in this order using the command `mvn spring-boot:run`, if you have Maven installed: discovery_server, gateway, user, product, order. It will take some time initially to install all the dependencies.

_Now the whole website should run without any issues._
