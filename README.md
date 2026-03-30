AutoMarket – Car Marketplace System (Low Level Design Project)

Overview

AutoMarket is a Low Level Design (LLD) based Java console application that simulates a used car marketplace platform. The system allows buyers, sellers, dealers, and administrators to interact within a structured marketplace environment where vehicles can be listed, searched, negotiated, and purchased.

The goal of this project is to demonstrate object-oriented programming principles, modular architecture, and service-oriented design typically used in real-world software systems.

The application is built as a console-based system and focuses on clear separation of responsibilities across models, services, menus, and data storage components.


System Roles

Buyer

- Browse all available cars
- Search and filter cars by brand, fuel type, condition, or price
- View detailed car information
- View vehicle history
- Make offers on cars
- Request test drives
- Send messages to sellers or dealers
- View conversations
- Make payments for accepted offers
- View and pay EMI or loan schedules
- Rate sellers or dealers

Seller

- Add car listings
- Update existing car listings
- Remove car listings
- Manage inventory
- View offers received
- Accept or reject offers
- Add vehicle accident history and service details
- Communicate with buyers

Dealer

- Manage multiple car listings
- Update car details including price, mileage, condition, and other specifications
- View and manage offers
- Track inventory
- Communicate with buyers

Admin

- View all registered users
- Remove or block users
- View all car listings
- Remove car listings
- Monitor all offers
- Monitor all transactions
- View platform summary statistics


Core Functionalities

Car Listing Management

Users can add, update, and remove car listings. Each listing contains information such as:

- Brand
- Model
- Year
- Price
- Mileage
- Fuel Type
- Condition
- Owner Information

Vehicle History Tracking

Each vehicle has a history record that stores:

- Accident history
- Ownership details
- Last service date

Offer System

Buyers can place offers on cars listed by sellers or dealers. Sellers can review and accept or reject these offers.

Messaging System

Buyers can communicate directly with sellers or dealers through a simple messaging system.

Payment System

The platform supports multiple payment methods including:

- Full cash payment
- Loan based payment
- EMI based payment

Payments track duration, monthly installments, and completion status.

Admin Monitoring

Administrators can monitor the platform through dashboards that display:

- Total users
- Total cars listed
- Total offers
- Total transactions
- Overall platform activity


System Architecture

The project follows a layered architecture structure.

Models

These classes represent the core entities of the system:

- User
- Buyer
- Seller
- Dealer
- Car
- Offer
- Payment
- Message
- VehicleHistory

Services

Service classes handle the business logic of the application:

- CarService
- OfferService
- PaymentService
- MessageService
- UserService

Menus

Menu classes implement the console based user interface for each role:

- AdminMenu
- BuyerMenu
- SellerMenu
- DealerMenu

Storage

Data is managed through an in-memory storage system:

- DataStore

This class acts as a central repository for users, cars, offers, payments, and messages.


Technologies Used

Language: Java
Architecture: Object Oriented Design
Design Approach: Low Level Design (LLD)
Interface: Console Based Application
Data Storage: In-memory storage using collections


Project Structure

src
├── models
│   ├── User.java
│   ├── Buyer.java
│   ├── Seller.java
│   ├── Dealer.java
│   ├── Car.java
│   ├── Offer.java
│   ├── Payment.java
│   ├── Message.java
│   └── VehicleHistory.java
│
├── services
│   ├── CarService.java
│   ├── OfferService.java
│   ├── PaymentService.java
│   ├── MessageService.java
│   └── UserService.java
│
├── menus
│   ├── AdminMenu.java
│   ├── BuyerMenu.java
│   ├── SellerMenu.java
│   └── DealerMenu.java
│
├── storage
│   └── DataStore.java
│
└── Main.java


How to Run the Project

1. Clone the repository

git clone https://github.com/yourusername/AutoMarket.git

2. Navigate to the project directory

cd AutoMarket

3. Compile the Java files

javac Main.java

4. Run the application

java Main


Design Principles Used

This project demonstrates the following software design principles:

- Encapsulation
- Separation of Concerns
- Modular Design
- Service Layer Architecture
- Role Based Access Design


Future Improvements

Possible future enhancements include:

- Database integration using MySQL or PostgreSQL
- Web based interface using Spring Boot
- REST API based architecture
- Authentication and authorization system
- Real time messaging
- Payment gateway integration
- Advanced car recommendation system
