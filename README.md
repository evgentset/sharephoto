# 📸 SharePhoto

**SharePhoto** is a sample Android application that allows users to explore and share photos.  
This project demonstrates a modern approach to Android app development using **Clean Architecture**,
**Jetpack Compose**,  **Hilt** for dependency injection and other modern approaches.

---

## 🧱 Architecture

The project is based on **Clean Architecture**, structured into three main layers:

- **Presentation** – UI and user interactions (Jetpack Compose)
- **Domain** – Business logic and use cases
- **Data** – Repositories, API, and local database (Room)

---

## 🚀 Tech Stack

| Area                   | Technology                |
|------------------------|---------------------------|
| Language               | Kotlin                    |
| Architecture           | MVVM + Clean Architecture |
| UI                     | Jetpack Compose           |
| Dependency Injection   | Hilt (may switch to Koin) |
| Local Database         | Room                      |
| Networking             | Retrofit + OkHttp         |
| Image Loading          | Coil                      |
| Caching / Offline Mode | Room + Repository Pattern |

---

## 🌐 Data Source

Photos are fetched from the **[Lorem Picsum API](https://picsum.photos)** – a free service providing
random, high-quality placeholder images.

> Special thanks to **David Marby** & **Nijiko Yonskai**  
> GitHub: [DMarby/picsum-photos](https://github.com/DMarby/picsum-photos)

---

## 🎯 Goals

- Demonstrate Clean Architecture principles in a real-world Android app
- Implement a modular, testable, and scalable codebase
- Showcase modern Android development tools and practices
- Use local database (Room) primarily for caching and offline support
- Use dependency injection (Hilt) to manage dependencies
-

---

## 📹 Preview

<div align="center">
  <video src="https://github.com/user-attachments/assets/815b424e-6d26-4894-acdf-108abb85d88e" />
</div>

---

## 🛠️ Setup Instructions

1. Clone the repository  
   `git clone https://github.com/evgentset/sharephoto.git`

2. Open in Android Studio (Arctic Fox or later)

3. Build & Run the app on an emulator or device (API 24+ recommended)

---

## 📌 Notes

- This project is under active development.
- Future plans include switching DI from Hilt to Koin, adding real user auth, and AI-powered photo
  enhancements.

---

## 📄 License

MIT License – see the [LICENSE](LICENSE) file for details.



