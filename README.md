# Fitness_App_Backend


**Fitness App**

Our team worked on an app which provides its users with the oppourtunity to improve their health and most imortantly give them a platform where they can find gyms with the equipments, activities, budget pricing plans that they require. Also, any user of the app can register his gym(s) and the services provided by his gyms and the discount offers and pricing/subscription plans.

We also have integrated an admin control where a pre-registered admin can have the control over the web app to read, update or delete the details or track/monitor activities of users.

The name of our app is Fitness App which is a platform for vendors of various cities where an individual or an estabilished organization can collaborate with other users through the help of our platform and provide them the services they have and similary users can purchase the services as per their requirement.

**Features**

Sign up or Register yourself as a user if you want to book any gym or sign up as a vendor if you have single or multiple gyms acquired.

Create your own personalized profile for both user and vendor where you can fill in your details.

Register your gyms and their prices of subscriptions and discounts with the activities that can be provided to the consumer.

A user can search for gyms based on the gym name and most importatly based on interest of area.

A user can rate the vendor and his gym/gyms according to the experience had after purchasing the subscription.

Vendor can rate the user according to the behaviour/experience with the user after his purchase order.

Vendor can mark the attendance of the user i.e. if he/she has attended the day's particular session or not.

Admin can read all the user details who have registered in the web application and can track/montior the activities of the users and delete the user as per his choice.

**Technology Used:**

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

**Dependencies**

lombok for removing boiler plate code

Razorpay for payment handling

Spring security for OAuth and JWT Authentication

**Folder Structure for Backend**

├── src                                                                                                                                             
│   ├── main                                                                                      
│   │   ├── java                                                                            
│   │   │   └── com                                                                                               
│   │   │       └── fitness                                                                                                     
│   │   │       └── app                                                                                                     
│   │   │           ├── FitnessAppApplication.java                                                                            
│   │   │           ├── auth                                                                                        
│   │   │           │   ├── Authenticate.java                                                                                   
│   │   │           │   ├── AuthToken.java                                                                                                          
│   │   │           ├── components                                                                                                    
│   │   │           │   ├── Components.java                                                                                                 
│   │   │           ├── config                                                                    
│   │   │           │   ├── JwtAuthenticationEntryPoint.java                                                                  
│   │   │           │   ├── JwtAuthenticationFilter.java                                                                              
│   │   │           │   ├── JwtConfig.java                                                                                                        
│   │   │           │   ├── JwtUtils.java                                                                                             
│   │   │           │   ├── WebConfiguration.java                                                                                                       
│   │   │           │   ├── WebCorsConfiguration.java                                                                     
│   │   │           ├── controller                                                                        
│   │   │           │   ├── AdminController.java          
│   │   │           │   ├──UserForgetPasswordController.java                                                                       
│   │   │           │   ├──VendorDetailsController.java                                                                   
│   │   │           │   ├── AttendanceConntroller.java                                                                          
│   │   │           │   ├──UserBankDetailsController.java                                                                                     
│   │   │           │   ├──UserOrderController.java  
│   │   │           │   ├──LocationController.java                                                                                    
│   │   │           │   ├──UserController.java                                                                                            
│   │   │           │   ├──UserRatingController.java                                                                  
│   │   │           │   ├── GymController.java                                                                              
│   │   │           │   ├──UserDetailsController.java                                                                                                     
│   │   │           │   ├──VendorBankDetailsController.java                                                                               
│   │   │           ├── entity                                                                                    
│   │   │           │   ├── AdminClass.java                                                                                   
│   │   │           │   ├──GymSubscriptionClass.java                                                                
│   │   │           │   ├──Rating.java                                                                  
│   │   │           │   ├──UserClass.java                                                                         
│   │   │           │   ├──VenderUser.java                                                                  
│   │   │           │   ├──VendorPayment.java                                                                             
│   │   │           │   ├──GymAddressClass.java                                                                                   
│   │   │           │   ├──GymTime.java                                                                                         
│   │   │           │   ├──UserAttendance.java                                                                      
│   │   │           │   ├──UserDetails.java                                                                                   
│   │   │           │   ├──VendorBankDetails.java                                                                           
│   │   │           │   ├──GymClass.java                                                                                                
│   │   │           │   ├──PhotoClass.java                                                                                          
│   │   │           │   ├──UserBankDetails.java                                                                               
│   │   │           │   ├──UserOrder.java                                                                                       
│   │   │           │   ├──VendorDetails.java      
│   │   │           │   ├──CustomResponse.java 
│   │   │           ├── exception                                                                                                 
│   │   │           │   ├── DataNotFoundException.java                                                                        
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           ├── image                                                                                                 
│   │   │           │   ├── Doc.java                                                                        
│   │   │           │   ├──ImgController.java                                                             
│   │   │           │   ├──ImgRepo.java                                                                             
│   │   │           │   ├──ImgService.java                                                              
│   │   │           ├── model  
│   │   │           │   ├── AdminPayRequestModel.java   
│   │   │           │   ├── BookedGymModel.java        
│   │   │           │   ├── DeleteGymModel.java 
│   │   │           │   ├── EnthusiastLoginModel.java                                                                    
│   │   │           │   ├── GymRepresnt.java                                                                             
│   │   │           │   ├── Role.java                                                                          
│   │   │           │   ├── UserModel.java                                                                     
│   │   │           │   ├── CustomOAuth2User.java                                                                
│   │   │           │   ├── EnthusiastUserModel.java                                                                         
│   │   │           │   ├── MarkUserAttModel.java                                                                        
│   │   │           │   ├── SignUpResponce.java                                                                            
│   │   │           │   ├── UserOrderModel.java                                                                            
│   │   │           │   ├── DeleteGymModel.java                                                                                        
│   │   │           │   ├── GymClassModel.java                                                                                           
│   │   │           │   ├── PhotoResponse.java   
│   │   │           │   ├── VendorBankDetailsRequestModel.java   
│   │   │           │   ├── VendorDetailsRequestModel.java   
│   │   │           │   ├── UserBankDetailsRequestModel.java   
│   │   │           │   ├── UserDetailsRequestModel.java   
│   │   │           │   ├── Rows.java   
│   │   │           │   ├── Result.java   
│   │   │           │   ├── Duration.java   
│   │   │           │   ├── Elements.java 
│   │   │           │   ├── Distance.java   
│   │   │           │   ├── DResponse.java   
│   │   │           │   ├── UserForgot.java 
│   │   │           │   ├── Geo.java
│   │   │           │   ├── GoogleAddress.java
│   │   │           │   ├── GymRepresnt.java
│   │   │           │   ├── UserPerfomanceModel.java                                                                                           
│   │   │           └── repository                                                                                                
│   │   │           │   ├── AddGymRepository.java                                                                                             
│   │   │           │   ├──GymAddressRepo.java                                                                                                
│   │   │           │   ├──RatingRepo.java                                                                                                
│   │   │           │   ├──UserRepository.java                                                              
│   │   │           │   ├──AdminRepo.java                                                                         
│   │   │           │   ├──GymSubscriptionRepo.java                                                                       
│   │   │           │   ├──UserBankDetailsRepo.java                                                                                     
│   │   │           │   ├──VendorDetailsRepository.java                                                                                           
│   │   │           │   ├──AttendanceRepo.java                                                                                  
│   │   │           │   ├──GymTimeRepo.java                                                                                                     
│   │   │           │   ├──UserDetailsRepository.java                                                                                       
│   │   │           │   ├──VendorPayRepo.java                                                                                                             
│   │   │           │   ├──BankDetailsRepository.java                                                                                           
│   │   │           │   ├──PhotoRepository.java                                                                                               
│   │   │           │   ├──UserOrderRepo.java                                                                                         
│   │   │           │   ├──VendorRepository.java                                                                                              
│   │   │           └── security                                                                          
│   │   │           │          └── service                                                                                
│   │   │           │                     ├──UserDetailsServiceImpl.java                                                                    
│   │   │           └── service                                                                                                 
│   │   │           │   ├──AttendanceService.java                                                                                     
│   │   │           │   ├──GymService.java     
│   │   │           │   ├──PagingService.java 
│   │   │           │   ├──RatingService.java                                                                                                   
│   │   │           │   ├──UserDetailsService.java                                                                    
│   │   │           │   ├──VendorBankDetailsService.java
│   │   │           │   ├──RegisterNewUser.java                                                                                             
│   │   │           │   ├──UserOrderService.java                                                                                                  
│   │   │           │   ├──VendorDetailsService.java                                                                                                
│   │   │           │   ├──FilterBySubscription.java                                                                                      
│   │   │           │   ├──UserBankDetailsService.java                                                                                    
│   │   │           │   ├──UserService.java                                                                                                   
│   │   │           │                                                                                                                         
│   │   └── resources                                                                                                                         
│   │       └── application.properties                                                                                                    
│   └── test                                                                                                                              
│       └── java                                                                                                                              
│           └── com                                                                                                                         
│               └── register                                                                                                                        
│                   └── app                                                                                                                         
                          └──FitnessAppApplicationTests.java                                                                                      
