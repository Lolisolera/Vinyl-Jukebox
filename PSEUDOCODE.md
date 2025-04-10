🎛️ Vinyl-Jukebox – Full Stack Project Pseudocode

📦 BACKEND (Spring Boot / Java)
🧱 Entities
@Entity User
- id: Integer
- username: String
- password: String
- email: String
- COLLECTION: List<Record>

@Entity Record
- id: Integer
- title: String
- releaseYear: Integer
- artist: Artist
- genres: Set<Genre>
- albumCover: AlbumCover

@Entity Artist
- id: Integer
- name: String
- bio: String
- country: String
- records: List<Record>

@Entity Genre
- id: Integer
- name: String
- records: Set<Record>

@Entity AlbumCover
- id: Integer
- imageUrl: String
- record: Record

@Entity Collection
- id: Integer
- user: User
- record: Record
- dateAdded: Date

🗃️ Repositories
UserRepository
- findByUsername(String username): Optional<User>

RecordRepository
- findByTitle(String title): List<Record>
- findByGenres(Genre genre): List<Record>
- findByArtist(Artist artist): List<Record>

GenreRepository
- findAll(): List<Genre>

ArtistRepository
- findAll(): List<Artist>

CollectionRepository
- findByUser(User user): List<Collection>

🧠 Services
UserService
- registerUser(UserDTO): User
- authenticateUser(Credentials): Boolean

RecordService
- getAllRecords(): List<Record>
- getRecordById(int id): Record
- searchRecords(String query): List<Record>

CollectionService
- addToCollection(int userId, int recordId): Collection
- getUserCollection(int userId): List<Collection>
  🌐 Controllers
  UserController
- POST /users/register → register new user
- POST /users/login → validate credentials

RecordController
- GET /records → get all records
- GET /records/{id} → get record by ID
- GET /records/search?q=term → search by title/genre/artist

CollectionController
- POST /collections → add record to user's collection
- GET /collections/{userId} → get user’s collection

🧩 DATABASE (MySQL)
💽 Tables and Relationships
TABLE users
- id PRIMARY KEY
- username
- password
- email

TABLE records
- id PRIMARY KEY
- title
- release_year
- artist_id FOREIGN KEY → artists.id
- album_cover_id FOREIGN KEY → album_covers.id

TABLE artists
- id PRIMARY KEY
- name
- bio
- country

TABLE genres
- id PRIMARY KEY
- name

TABLE record_genres
- record_id FOREIGN KEY → records.id
- genre_id FOREIGN KEY → genres.id

TABLE album_covers
- id PRIMARY KEY
- image_url

TABLE collections
- id PRIMARY KEY
- user_id FOREIGN KEY → users.id
- record_id FOREIGN KEY → records.id
- date_added


💻 FRONTEND (React + TypeScript + SCSS)
🧩 Components
<App>
- Renders Header, Routes, Footer

<HomePage>
  - Insert Coin animation → redirect to RecordList

<RecordList>
  - useEffect(): fetch /records
  - Search bar, Filter dropdown
  - Display RecordCard

<RecordCard>
  - Props: record
  - OnClick: navigate to RecordDetail

<RecordDetail>
  - useEffect(): fetch /records/{id}
  - Show record info
  - Add to collection → POST /collections

<UserCollection>
  - useEffect(): fetch /collections/{userId}
  - Show saved records

<Login/Register>
- Form submission → POST /users/login or /register
- Store user in localStorage/context

api.ts (utils)
- Contains functions for all endpoints (GET, POST, etc.)

🔄 FLOW OVERVIEW
USER lands on HomePage
→ Clicks Insert Coin → goes to RecordList
→ Browses/searches records → Clicks a record
→ Sees RecordDetail → Clicks "Add to collection"
→ POST request sent

USER optionally logs in/registers
→ USER accesses /collections → sees their saved records

☁️ DEPLOYMENT PLAN
📦 Backend (Spring Boot)
1. Package with Maven: mvn clean install
2. Deploy to:
    * Heroku (easy for Java apps, free tier)
    * Render or Railway (modern alternatives)
    * Set environment variables for MySQL DB URL, username, password
      💽 MySQL
* Use PlanetScale, ElephantSQL, or host your own via ClearDB (Heroku)
* Ensure connection URL is accessible from Spring Boot via application.properties
  💻 Frontend (ReactTS)
1. Build frontend: npm run build
2. Deploy to:
    * Netlify, Vercel, or GitHub Pages
    * Configure it to proxy API requests to your backend URL
      🧪 Optional
* Add CI/CD with GitHub Actions for auto-deploy
* Use .env files to manage frontend/backend credentials







