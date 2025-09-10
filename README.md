# 📚 Booklender

**Booklender** is a learning project in Java that implements a simple online library system.  
It is written **without Spring**, using the built-in `HttpServer` from the JDK and the **FreeMarker** templating engine to generate HTML pages.  

## 🚀 Features
- User registration and authentication.
- Browse the library catalog.
- View detailed book information (author, description, cover image).
- Borrow books (up to 2 books per user).
- Return books.
- User profile:
  - see books currently borrowed;
  - view history of returned books in the current session.
- Session handling with **cookies**.

## 🛠 Technologies
- **Java SE (HttpServer API)**
- **FreeMarker** (templating engine)
- **Gson** (JSON serialization/deserialization)
- **Bootstrap 5** (UI styling)
- Data stored in **JSON files**:
  - `data/dataBase/library.json` — book database
  - `data/dataBase/users.json` — users

## 📂 Project structure
src/

└── kg/attractor/java

├── Main.java # Entry point

├── lesson44/Booklender.java # Core server and routing

├── library/ # Book models and services

├── server/ # HTTP server, routing, cookies

├── user/ # User models and services

└── utils/Utils.java # Helper utilities

data/

├── *.ftlh # FreeMarker templates

├── css/ # Stylesheets

└── dataBase/ # JSON files with books and users


## ▶️ How to run
1. Make sure you have **Java 17+** installed.
2. Clone the repository:
   ```bash
   git clone https://github.com/qnarly/Booklender.git
   cd Booklender
Compile and run the project:
(clean cookies before start)

🔑 Demo accounts
Some test users are already available in users.json, for example:

email: attractor@gmail.com

password: 12345678
