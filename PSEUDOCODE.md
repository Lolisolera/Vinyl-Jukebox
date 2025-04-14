# 🎛️ Vinyl-Jukebox – Full Stack Project Pseudocode 

## 1. **BACKEND (Spring Boot / Java)**

### 1.1 **Entities**

@Entity User
- id: Long
- username: String
- password: String
- email: String
- COLLECTION: List<Record>

@Entity Record
- id: Long
- title: String
- releaseYear: Integer
- artist: Artist
- genres: Set<Genre>
- albumCover: AlbumCover
- spotifyTrackId: String?   // e.g. "3n3Ppam7vgaVa1iaRUc9Lp"

@Entity Artist
- id: Long
- name: String
- bio: String
- country: String
- records: List<Record>

@Entity Genre
- id: Long
- name: String
- records: Set<Record>

@Entity AlbumCover
- id: Long
- imageUrl: String
- record: Record

@Entity RecordCollection
- id: Long
- user: User
- record: Record
- dateAdded: Date
```

> **Note**: I add `spotifyTrackId` to `Record` so that I can reference tracks on Spotify without hosting audio myself.
```

### 1.2 **Repositories**
```text
UserRepository
- findByUsername(String username): Optional<User>

RecordRepository
- findByTitle(String title): List<Record>
- findByGenres(Genre genre): List<Record>
- findByArtist(Artist artist): List<Record>
// Possibly: findBySpotifyTrackId(String spotifyTrackId)

ArtistRepository
- findAll(): List<Artist>

GenreRepository
- findAll(): List<Genre>

RecordCollectionRepository
- findByUser(User user): List<RecordCollection>
```


 
### 1.3 **Services**

UserService
- registerUser(UserDTO): User
- authenticateUser(Credentials): Boolean

RecordService
- getAllRecords(): List<Record>
- getRecordById(long id): Record
- searchRecords(String query): List<Record>
- createOrUpdateRecord(Record record): Record // handles local + Spotify-based track references

CollectionService
- addToCollection(long userId, long recordId): RecordCollection
- getUserCollection(long userId): List<RecordCollection>

### 1.4 **Spotify Integration**

```text
SpotifyIntegrationService
- searchTracks(String query): List<SpotifyTrackDTO>
  // calls Spotify’s /search endpoint, returns minimal info (id, name, artist)
- getTrackById(String spotifyTrackId): SpotifyTrackDTO
  // calls Spotify’s /tracks/{id} endpoint

// Only store the track ID in your DB if you want to reference it locally.
// The audio streams remain on Spotify’s side.
```

---

### 1.5 **Controllers**

```text
UserController
- POST /users/register → register new user
- POST /users/login → validate credentials

RecordController
- GET /records → get all records
- GET /records/{id} → get record by ID
- GET /records/search?q=term → search by title/genre/artist
- POST /records → create or update record
// Possibly a special endpoint if you want to create a local record from a Spotify track ID

CollectionController
- POST /collections → add record to user’s collection
- GET /collections/{userId} → get user’s collection

SpotifyController (Optional)
- GET /spotify/search?q=term → calls SpotifyIntegrationService
- GET /spotify/tracks/{spotifyTrackId} → returns track details
```

> You can combine Spotify endpoints into `RecordController` if you prefer.

---

## 2. **DATABASE (PostgreSQL or MySQL)**

All tables remain the same, but add a column in `records`:

```text
TABLE records
- id BIGSERIAL PRIMARY KEY
- title VARCHAR
- release_year INT
- artist_id BIGINT FK → artists.id
- album_cover_id BIGINT FK → album_covers.id
- spotify_track_id VARCHAR NULL  // optional
```

Everything else stays the same.

---

## 3. **FRONTEND (React + TypeScript + SCSS)**

### 3.1 **Components** (Retro Theme)

<App>
 - Renders Header, Routes, Footer

<HomePage>
 - Insert Coin animation → leads to RecordList

<RecordList>
 - useEffect(): fetch /records
 - Search bar (local DB)
 - Optional: “Search Spotify” → fetch from /spotify/search
 - Filter dropdown
 - Display RecordCard

<RecordCard>
 - Props: record
 - OnClick → navigate to RecordDetail

<RecordDetail>
 - useEffect(): fetch /records/{id}
 - Show record info or Spotify preview link
 - “Add to collection” → POST /collections

<UserCollection>
 - useEffect(): fetch /collections/{userId}
 - Show saved records

<Login/Register>
 - Form submission → POST /users/login or /register
 - Store user in localStorage/context

api.ts (utils)
 - Contains functions for all endpoints (GET, POST, etc.)


> Emphasize a **retro vinyl machine** vibe. If a track has `spotifyTrackId`, show a “Preview on Spotify” button or embed.

## 4. **FLOW OVERVIEW**

1) USER lands on HomePage
   → Insert Coin → goes to RecordList
   → Browses local records or searches Spotify
   → Chooses a record → goes to RecordDetail
   → If record is from Spotify, show link or embed
   → “Add to collection” → POST /collections

2) USER optionally logs in / registers
   → sees own saved records in <UserCollection>


## 5. **DEPLOYMENT PLAN**

Backend (Spring Boot)
1) Package with Maven: mvn clean install
2) Deploy to e.g. Render, Railway, or Heroku
3) Configure environment variables for DB + Spotify credentials

Database (PostgreSQL/MySQL)
- PlanetScale, ElephantSQL, or ClearDB (Heroku)
- Ensure DB is publicly accessible

Frontend (ReactTS)
1) npm run build
2) Deploy to Netlify, Vercel, or GitHub Pages
3) Proxy requests to your backend URL

Optional extras:
- CI/CD with GitHub Actions for automatic deploy
- .env for sensitive config


