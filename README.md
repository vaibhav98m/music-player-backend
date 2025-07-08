# Spotify API Project

A comprehensive music streaming API built with Spring Boot that provides user management, music catalog access, and album browsing capabilities. The project integrates with Clerk for authentication and JWT token validation, ensuring secure user onboarding and data synchronization between Clerk and the database.

## Features

### 🔐 Authentication & User Management
- **Clerk Integration**: Seamless JWT token validation and user authentication
- **Dual Synchronization**: User data synchronized between Clerk and local database
- **User CRUD Operations**: Complete user lifecycle management

### 🎵 Music Catalog
- **Song Management**: Browse and search through music catalog
- **Genre Filtering**: Filter songs by genre with pagination support
- **Year-based Search**: Find songs by release year
- **Individual Song Details**: Get detailed information about specific tracks

### 💿 Album Management
- **Album Browsing**: View all available albums with pagination
- **Album Details**: Get complete album information including track listings
- **Album-specific Songs**: Retrieve all songs within a specific album

## API Endpoints

### User Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/users/user` | Get current user details |
| `GET` | `/users/save` | Create new user |
| `PUT` | `/users/update` | Update user information |
| `DELETE` | `/users/delete` | Delete user account |
| `GET` | `/users/all` | Get all users (admin) |

### Music Catalog
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/music/songs` | Get all songs with pagination |
| `GET` | `/music/song/{id}` | Get specific song by ID |
| `GET` | `/music/{genre}/genre` | Get songs by genre |
| `GET` | `/music/{year}/year` | Get songs by year |
| `GET` | `/music/genre` | Get available genres |
| `GET` | `/music/year` | Get available years |

### Album Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/album/detail/all` | Get all albums with pagination |
| `GET` | `/album/detail/{albumId}` | Get album details and songs |

## Request/Response Models

### User Models
```json
{
  "UserModel": {
    "status": "string",
    "message": "string",
    "userId": "string",
    "username": "string",
    "firstName": "string",
    "lastName": "string",
    "email": "string"
  }
}
```

### Song Models
```json
{
  "SongDetails": {
    "id": "integer",
    "title": "string",
    "album": "string",
    "artist": "string",
    "duration": "string",
    "year": "string",
    "genre": "string",
    "filePath": "string",
    "imageIcon": "string"
  }
}
```

### Album Models
```json
{
  "AlbumModel": {
    "albumId": "string",
    "albumName": "string",
    "albumFullName": "string",
    "image": "string"
  }
}
```

## Authentication

The API uses **Clerk** for authentication and authorization:

- **JWT Token Validation**: All requests are validated using Clerk's JWT tokens
- **Header Authentication**: Pass `uniqueId` in request headers (defaults to "test-123" for development)
- **User Onboarding**: Automatic user creation and synchronization with Clerk
- **Dual Updates**: User modifications are reflected in both Clerk and local database

## Query Parameters

Most endpoints support pagination and filtering:

- `limit` - Number of results per page (default varies by endpoint)
- `offset` - Page offset for pagination (default: 1)
- `uniqueId` - User identification header (default: "test-123")

## Getting Started

### Prerequisites
- Java 11 or higher
- Spring Boot 2.x+
- Database (PostgreSQL/MySQL recommended)
- Clerk account and API keys

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd spotify-api
```

2. Configure Clerk credentials
```properties
# application.properties
clerk.secret.key=your_clerk_secret_key
clerk.publishable.key=your_clerk_publishable_key
```

3. Set up database configuration
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/spotify_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Run the application
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8081/spotify`

## API Documentation

Once the application is running, you can access:
- **Swagger UI**: `http://localhost:8081/spotify/swagger-ui.html`
- **OpenAPI Spec**: `http://localhost:8081/spotify/v3/api-docs`

## Error Handling

The API returns consistent error responses:
- **404 Not Found**: Resource not found
- **400 Bad Request**: Invalid request parameters
- **401 Unauthorized**: Invalid or missing authentication
- **500 Internal Server Error**: Server-side errors

## Security Features

- **JWT Token Validation**: All endpoints protected with Clerk JWT validation
- **User Context**: Requests tied to authenticated user context
- **Data Synchronization**: Consistent user data across Clerk and database
- **Secure Headers**: Authentication via request headers

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue in the GitHub repository
- Check the API documentation at `/swagger-ui.html`
- Review Clerk documentation for authentication issues
