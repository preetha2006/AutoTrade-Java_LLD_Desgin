# AutoMarket — Car Marketplace System

## Overview

AutoMarket is a console-based Java application built as a Low Level Design (LLD) project. It simulates a used car marketplace where buyers, sellers, dealers, and administrators interact within a structured environment to list, search, negotiate, and purchase vehicles.

The project demonstrates object-oriented programming principles, modular architecture, and service-oriented design patterns commonly applied in production software systems.

---

## System Roles

**Buyer**
Browse and filter car listings by brand, fuel type, condition, or price. View detailed car information and vehicle history. Make offers, request test drives, and communicate with sellers or dealers. Process payments and manage EMI or loan schedules. Rate sellers and dealers after transactions.

**Seller**
Add, update, and remove car listings. Manage inventory and review incoming offers. Accept or reject buyer offers. Record vehicle accident history and service details. Communicate directly with buyers.

**Dealer**
Manage multiple car listings and update specifications including price, mileage, and condition. Review and respond to offers. Track inventory and communicate with buyers.

**Admin**
View and manage all registered users. Remove or block accounts. Monitor all car listings, offers, and transactions. Access platform-wide summary statistics.

---

## Core Functionalities

**Car Listing Management**
Users can create, update, and remove listings containing brand, model, year, price, mileage, fuel type, condition, and owner information.

**Vehicle History Tracking**
Each vehicle record stores accident history, ownership details, and last service date.

**Offer System**
Buyers submit offers on active listings. Sellers and dealers review offers and accept or reject them accordingly.

**Messaging System**
Buyers communicate directly with sellers or dealers through an integrated messaging interface.

**Payment System**
Supports full cash payment, loan-based payment, and EMI-based payment. Payment records track duration, monthly installments, and completion status.

**Admin Monitoring**
Administrators access a dashboard displaying total users, total listings, total offers, total transactions, and overall platform activity.

---

## Architecture

The project follows a layered architecture with clear separation between models, services, menus, and storage.

| Layer | Description |
|---|---|
| Models | Core entities representing domain objects |
| Services | Business logic handlers |
| Menus | Console-based UI for each role |
| Storage | In-memory data store using collections |

---

## Project Structure

```
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
```

---

## Technologies

| Component | Details |
|---|---|
| Language | Java |
| Architecture | Object-Oriented Design |
| Design Approach | Low Level Design (LLD) |
| Interface | Console-based |
| Data Storage | In-memory collections |

---

## Getting Started

**1. Clone the repository**
```bash
git clone https://github.com/yourusername/AutoMarket.git
```

**2. Navigate to the project directory**
```bash
cd AutoMarket
```

**3. Compile**
```bash
javac Main.java
```

**4. Run**
```bash
java Main
```

---

## Design Principles

- Encapsulation
- Separation of Concerns
- Modular Design
- Service Layer Architecture
- Role-Based Access Design

---

## Future Enhancements

- Database integration with MySQL or PostgreSQL
- Web interface using Spring Boot and REST APIs
- Authentication and authorization system
- Real-time messaging
- Payment gateway integration
- Car recommendation engine
