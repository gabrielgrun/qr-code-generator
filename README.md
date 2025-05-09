# QR Code Generator

Java Spring Boot project for QR Code generation and AWS S3 integration for storage.

## Technologies

- Java 21
- Spring Boot
- Maven
- AWS S3

## How to run locally


1. **Set environment variables:**
    - Create a `.env` file at the project root with the following variables:
      ```
      AWS_ACCESS_KEY_ID=YOUR_ACCESS_KEY
      AWS_SECRET_ACCESS_KEY=YOUR_SECRET_KEY
      ```
    - The `.env` file is already in `.gitignore` and will not be versioned.

2. **Build the project with Docker:**
   ```sh
   docker build -t qrcode-generator .
   ```

3. **Run the container:**
   ```sh
   docker run --env-file .env -p 8080:8080 qrcode-generator
   ```

## Configuration

S3 access settings are in `src/main/resources/application.properties`:

- `aws.s3.region`
- `aws.s3.bucket-name`

These variables can be overridden via environment variables.

## Endpoint

### Generate and store QR Code

- **URL:** `POST /api/qrcode`
- **Description:** Generates a QR Code from the provided text and stores the image in the configured S3 bucket.
- **Request Body (JSON):**
  ```json
  {
    "text": "Text or URL to generate the QR Code"
  }
  ```
- **Response (JSON):**
    ```json
  {
  "url": "https://<bucket-name>.s3.<region>.amazonaws.com/<qrcode-file>.png"
  }
    ```
  
- **Example usage with curl:**
    ```sh
  curl -X POST http://localhost:8080/api/qrcode \
  -H "Content-Type: application/json" \
  -d '{"text": "https://example.com"}'
    ```