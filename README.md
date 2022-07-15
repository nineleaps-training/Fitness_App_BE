# FITNESS APP BACKEND #


## Fitness App ##

Our team worked on an app which provides its users with the oppourtunity to improve their health and most imortantly give them a platform where they can find gyms with the equipments, activities, budget pricing plans that they require. Also, any user of the app can register his gym(s) and the services provided by his gyms and the discount offers and pricing/subscription plans.

We also have integrated an admin control where a pre-registered admin can have the control over the web app to read, update or delete the details or track/monitor activities of users.

The name of our app is Fitness App which is a platform for vendors of various cities where an individual or an estabilished organization can collaborate with other users through the help of our platform and provide them the services they have and similary users can purchase the services as per their requirement.

## Features ##

* Sign up or Register yourself as a user if you want to book any gym or sign up as a vendor if you have single or multiple gyms acquired.

* Create your own personalized profile for both user and vendor where you can fill in your details.

Register your gyms and their prices of subscriptions and discounts with the activities that can be provided to the consumer.

A user can search for gyms based on the gym name and most importatly based on interest of area.

A user can rate the vendor and his gym/gyms according to the experience had after purchasing the subscription.

Vendor can rate the user according to the behaviour/experience with the user after his purchase order.

Vendor can mark the attendance of the user i.e. if he/she has attended the day's particular session or not.

Admin can read all the user details who have registered in the web application and can track/montior the activities of the users and delete the user as per his choice.

## Technology Used ##

SpringBoot

Java 17

MySQL

Maven

JPA buddy

Dependencies

Sonar-maven-plugin for sonarqube

Sql Connector for Sql Database

Lombok for removing boiler plate code

MongoDB Atlas for database connectivity

Heroku for deployment of APIs

## Dependencies ##

Lombok for removing boiler plate code

Razorpay for payment handling

Spring security for OAuth and JWT Authentication

## Folder Structure for Backend ##

```
.
├── bin
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── fitness
│       │   │           └── app
│       │   │               ├── config
│       │   │               │   └── WebConfiguration.class
│       │   │               ├── controller
│       │   │               │   ├── LoginController.class
│       │   │               │   └── RegisterController.class
│       │   │               ├── entity
│       │   │               │   ├── BankDetails.class
│       │   │               │   ├── PersonalDetails.class
│       │   │               │   └── VenderUser.class
│       │   │               ├── FitnessAppApplication.class
│       │   │               ├── model
│       │   │               │   ├── LoginUserModel.class
│       │   │               │   └── UserModel.class
│       │   │               ├── repository
│       │   │               │   └── RegisterRepository.class
│       │   │               └── service
│       │   │                   ├── LoginService.class
│       │   │                   └── RegisterService.class
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── register
│                       └── app
│                           └── FitnessAppApplicationTests.class
├── Dockerfile
├── -Dsonar.projectKey=Fitness_App_Backend
├── fitness.log
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
   ├── main
   │   ├── java
   │   │   └── com
   │   │       └── fitness
   │   │           └── app
   │   │               ├── auth
   │   │               │   ├── Authenticate.java
   │   │               │   └── AuthToken.java
   │   │               ├── componets
   │   │               │   └── Components.java
   │   │               ├── config
   │   │               │   ├── JwtAuthenticationEntryPoint.java
   │   │               │   ├── JwtAuthenticationFilter.java
   │   │               │   ├── JwtUtils.java
   │   │               │   ├── WebConfiguration.java
   │   │               │   └── WebCorsConfiguration.java
   │   │               ├── controller
   │   │               │   ├── AdminController.java
   │   │               │   ├── AttendanceController.java
   │   │               │   ├── GymController.java
   │   │               │   ├── LocationController.java
   │   │               │   ├── UserBankDetailsController.java
   │   │               │   ├── UserController.java
   │   │               │   ├── UserDetailsController.java
   │   │               │   ├── UserForgetPasswordController.java
   │   │               │   ├── UserOrderController.java
   │   │               │   ├── UserRatingController.java
   │   │               │   ├── VendorBankDetailsController.java
   │   │               │   └── VendorDetailsController.java
   │   │               ├── entity
   │   │               │   ├── AdminClass.java
   │   │               │   ├── AdminPay.java
   │   │               │   ├── CustomResponse.java
   │   │               │   ├── GymAddressClass.java
   │   │               │   ├── GymClass.java
   │   │               │   ├── GymSubscriptionClass.java
   │   │               │   ├── GymTime.java
   │   │               │   ├── Rating.java
   │   │               │   ├── UserAttendance.java
   │   │               │   ├── UserBankDetails.java
   │   │               │   ├── UserClass.java
   │   │               │   ├── UserDetails.java
   │   │               │   ├── UserOrder.java
   │   │               │   ├── VenderUser.java
   │   │               │   ├── VendorBankDetails.java
   │   │               │   ├── VendorDetails.java
   │   │               │   └── VendorPayment.java
   │   │               ├── exception
   │   │               │   ├── DataNotFoundException.java
   │   │               │   ├── GlobalExceptionHandler.java
   │   │               │   └── IncorrectFileUploadException.java
   │   │               ├── FitnessAppApplication.java
   │   │               ├── image
   │   │               │   ├── Doc.java
   │   │               │   ├── ImgController.java
   │   │               │   ├── ImgRepo.java
   │   │               │   └── ImgService.java
   │   │               ├── model
   │   │               │   ├── AdminPayRequestModel.java
   │   │               │   ├── BookedGymModel.java
   │   │               │   ├── DeleteGymModel.java
   │   │               │   ├── Distance.java
   │   │               │   ├── DResponse.java
   │   │               │   ├── Duration.java
   │   │               │   ├── Elements.java
   │   │               │   ├── EnthusiastLoginModel.java
   │   │               │   ├── EnthusiastUserModel.java
   │   │               │   ├── Geo.java
   │   │               │   ├── GoogleAddress.java
   │   │               │   ├── GymClassModel.java
   │   │               │   ├── GymRepresnt.java
   │   │               │   ├── Location.java
   │   │               │   ├── MarkUserAttModel.java
   │   │               │   ├── PhotoResponse.java
   │   │               │   ├── RatingRequestModel.java
   │   │               │   ├── Response.java
   │   │               │   ├── ResponseModel.java
   │   │               │   ├── Result.java
   │   │               │   ├── Rows.java
   │   │               │   ├── SignUpResponce.java
   │   │               │   ├── UserBankDetailsRequestModel.java
   │   │               │   ├── UserDetailsRequestModel.java
   │   │               │   ├── UserForgot.java
   │   │               │   ├── UserModel.java
   │   │               │   ├── UserOrderModel.java
   │   │               │   └── UserPerfomanceModel.java
   │   │               ├── repository
   │   │               │   ├── AddGymRepository.java
   │   │               │   ├── AdminPayRepo.java
   │   │               │   ├── AdminRepo.java
   │   │               │   ├── AttendanceRepo.java
   │   │               │   ├── BankDetailsRepository.java
   │   │               │   ├── GymAddressRepo.java
   │   │               │   ├── GymSubscriptionRepo.java
   │   │               │   ├── GymTimeRepo.java
   │   │               │   ├── RatingRepo.java
   │   │               │   ├── UserBankDetailsRepo.java
   │   │               │   ├── UserDetailsRepository.java
   │   │               │   ├── UserOrderRepo.java
   │   │               │   ├── UserRepository.java
   │   │               │   ├── VendorDetailsRepository.java
   │   │               │   ├── VendorPayRepo.java
   │   │               │   └── VendorRepository.java
   │   │               ├── security
   │   │               │   └── service
   │   │               │       └── UserDetailsServiceImpl.java
   │   │               └── service
   │   │                   ├── AdminService.java
   │   │                   ├── AttendanceService.java
   │   │                   ├── FilterBySubscription.java
   │   │                   ├── GymService.java
   │   │                   ├── PagingService.java
   │   │                   ├── RatingService.java
   │   │                   ├── UserBankDetailsService.java
   │   │                   ├── UserDetailsService.java
   │   │                   ├── UserOrderService.java
   │   │                   ├── UserService.java
   │   │                   ├── VendorBankDetailsService.java
   │   │                   └── VendorDetailsService.java
   │   └── resources
   │       └── application.properties
   └── test
       └── java
           └── com
               ├── fitness
               │   └── app
               │       ├── componets
               │       │   └── ComponentsTest.java
               │       ├── config
               │       │   ├── JwtAuthenticationFilterTestCase.java
               │       │   ├── JwtAuthenticationFilterTest.java
               │       │   └── JwtUtilsTest.java
               │       ├── controller
               │       │   ├── AdminControllerTest.java
               │       │   ├── AttendanceControllerTest.java
               │       │   ├── GymControllerTest.java
               │       │   ├── LocationControllerTest.java
               │       │   ├── UserBankDetailsControllerTest.java
               │       │   ├── UserControllerTest.java
               │       │   ├── UserDetailsControllerTest.java
               │       │   ├── UserForgetPasswordControllerTest.java
               │       │   ├── UserOrderControllerTest.java
               │       │   ├── UserRatingControllerTest.java
               │       │   ├── VendorBankDetailsControllerTest.java
               │       │   └── VendorDetailsControllerTest.java
               │       ├── exception
               │       │   └── GlobalExceptionHandlerTest.java
               │       ├── image
               │       │   ├── ImgControllerTest.java
               │       │   └── ImgServiceTest.java
               │       ├── security
               │       │   └── service
               │       │       └── UserDetailsServiceImplTest.java
               │       └── service
               │           ├── AdminServiceTest.java
               │           ├── AttendanceServiceTest.java
               │           ├── FilterBySubscriptionTest.java
               │           ├── GymServiceTest.java
               │           ├── PagingServiceTest.java
               │           ├── RatingServiceTest.java
               │           ├── UserBankDetailsServiceTest.java
               │           ├── UserDetailsServiceTest.java
               │           ├── UserOrderServiceTest.java
               │           ├── UserServiceTest.java
               │           ├── VendorBankDetailsServiceTest.java
               │           └── VendorDetailsServiceTest.java
               └── register
                   └── app
                       └── FitnessAppApplicationTests.java
```
