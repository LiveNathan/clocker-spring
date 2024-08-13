# Offline Functionality Demo with Java + Spring Boot

This demo project showcases the implementation of offline functionality in a Java + Spring Boot web application. The main focus is on caching POST requests and resending them when the application regains connectivity. The project uses a combination of Java, Spring Boot, EclipseStore, Thymeleaf, DaisyUI, and HTMX.

The use case is a labor management app that needs to handle requests in the case that an employee's device is temporarily offline.

## Features

- **Service Worker Integration:** Caches POST requests made while offline and resends them automatically when back online.
- **Thymeleaf and HTMX:** Provides a dynamic, server-rendered frontend with minimal JavaScript.
- **DaisyUI for Styling:** Utilizes DaisyUI for a modern and responsive user interface.

## Technologies Used

- **Java**
- **Spring Boot**
- **EclipseStore**
- **Thymeleaf**
- **DaisyUI**
- **HTMX**

## Running the Application

1. **Clone the repository:**

2. **Build the front end:**
   ```bash
   npm run build
   ```

3. **Build and run the project:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Open the application:**
   - Open your browser and navigate to `http://localhost:8080`.
   - Open Chrome Developer Tools (F12 or right-click and select "Inspect").

5. **Check Service Worker Installation:**
   - In the Developer Tools, go to the "Application" tab.
   - Under the "Service Workers" section, you should see that the service worker is installed.

## Observing the Offline Functionality

1. **Going Offline:**
   - It's important to note that simply checking the "Offline" box in Chrome DevTools is not sufficient for testing service worker requests. You need to **physically disconnect your internet connection** (e.g., turn off Wi-Fi or disconnect the network cable).

2. **Interacting with the App:**
   - While offline, click the "Clock In/Out" button.
   - The page will not visibly change, but check the console logs in DevTools. You should see a message indicating that the request has been "stashed" for later.

3. **Returning Online:**
   - Reconnect your internet connection.
   - The previously stashed POST request will be automatically resent, and the page will refresh to reflect the new state.

## Additional Details

- **Console Logs:** The application logs actions such as the stashing of requests and the resending process to the console, allowing you to track the behavior of the service worker.
- **Known Issue:** There might be a slight delay in refreshing the page after going back online, depending on the network conditions and how quickly the request is processed.

## Contributing

If you have suggestions or improvements, feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License.
