# Truck-loading

The Truck Loading Application is a lightweight Android application made with Java language in Android Studio. It uses Google Firebase for database management, login authentication, and to store other information. The application is divided into three different modules: Customer Modules, Driver module, and Admin module..

## Screens
<div style="display: flex; justify-content: center">
   <img src="https://github.com/atisamhaq123/Truck-loading/blob/main/Screens/2.jpg" width="" height="500">
   <img src="https://github.com/atisamhaq123/Truck-loading/blob/main/Screens/3.jpg" width="" height="500">
   <img src="https://github.com/atisamhaq123/Truck-loading/blob/main/Screens/4.jpg" width="" height="500">
</div>
<br>

## Features
The application has the following features:
 - Login authentication
 - Maps to locate positions
 - Distance calculation
 - Fare calculation
 - Availability of various trucks

## Installation
 - Clone the repository to your local machine.
 - Extract Zip files from "Code" folder
 - Open project in Android Studio
 - Build the project and ensure all dependencies are downloaded
 - To use this Android app project with Firebase, you'll need to set up your own Firebase project
 - Download your own google-services.json file. Follow the steps in the Firebase console
 - Replace the google-services.json file in the project directory.
 - Setup Firebase Cloud storage and add keys for "customer", "driver" and "admin"
 - Set Read/Write permissions 

# Usage
## Customer Version
The customer version allows the user to:
 - Login to the application
 - Select the destination and source locations on a map
 - Select a truck to use for transportation
 - It automatically calculates the distance between the selected locations
 - It automatically calculates the fare for transportation based on the selected truck and distance
  
## Driver Version
The driver version allows the user to:
 - Login to the application
 - View all assigned transportation jobs
 - Update the status of the assigned jobs (pending, in progress, completed)
 - View the details of each job, including source and destination locations, selected truck, distance, and fare
 - 
## Admin Version
The admin version allows the user to:
 - Add, edit, or delete trucks available for transportation
 - Add, edit, or delete details of users  (both driver and customers)
 - View and manage all transportation jobs
 - View the details of each job, including source and destination locations, selected truck, distance, and fare
 - 
## Contributing
If you would like to contribute to the project, please fork the repository, make your changes, and create a pull request.

## License
This project is licensed under the MIT license. Please see the LICENSE file for more information.
