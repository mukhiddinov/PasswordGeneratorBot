# Telegram Password Generator Bot

## Overview

This project is a Telegram bot that generates secure passwords based on a given domain and a salt. The bot interacts with users through Telegram and provides functionality to generate and manage passwords.

## Features

- Generates a secure password for a given domain.
- Allows setting and updating a salt value for password generation.
- Clears bot messages from the chat for privacy and security.

## Technologies Used

- Java
- TelegramBots API
- SHA-256 for hashing
- Base64 encoding

## Project Structure


src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── Main.java
│   │           ├── PasswordGenerator.java
│   │           └── TelegramBot.java
│   └── resources/
│       └── application.properties
```

## Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/telegram-password-generator-bot.git
   cd telegram-password-generator-bot
   ```

2. **Add your bot credentials:**

   Create a file named `application.properties` in the `src/main/resources` directory with the following content:

   ```properties
   bot.username=YOUR_BOT_USERNAME
   bot.token=YOUR_BOT_TOKEN
   ```

3. **Build and run the project:**

   Use Maven to build and run the project:

   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass=com.example.Main
   ```

## Usage

### Commands

- **/start:** Initializes the bot and sets the salt to the chat ID.
- **/clear:** Clears all messages sent by the bot in the chat.
- **setSALT [SALT]:** Updates the salt value used for password generation.
- **[domain]:** Enter a domain to generate a password for that domain.

### Example

1. **Start the bot:**
   ```
   /start
   ```

2. **Generate a password:**
   ```
   example.com
   ```

   The bot will respond with a generated password for `example.com`.

3. **Set a custom salt:**
   ```
   setSALT myCustomSalt
   ```

   The salt will be updated and used for subsequent password generation.

4. **Clear chat history:**
   ```
   /clear
   ```

   The bot will delete all its messages from the chat for privacy.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Acknowledgements

- [TelegramBots API](https://github.com/rubenlagus/TelegramBots) for providing the Java library to interact with the Telegram Bot API.
```

Feel free to customize the content as per your specific project details and requirements.
