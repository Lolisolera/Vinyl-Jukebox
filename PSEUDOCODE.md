# 🎛️ Vinyl-Jukebox – Full Stack Project Pseudocode (Revised – April 2025)

## 1. BACKEND (Spring Boot / Java)

### 1.1 Entities

```text
@Entity Record
- id: Long
- title: String
- artist: Artist
- genres: Set<Genre>
- albumCover: AlbumCover
- deezerTrackId: String
- previewUrl: String
```

```text
@Entity Artist
- id: Long
- name: String
```

```text
@Entity Genre
- id: Long
- name: String
```

```text
@Entity AlbumCover
- id: Long
- imageUrl: String
```

---

### 1.2 Repositories

```text
RecordRepository
- findAll()
- findByDeezerTrackId(String deezerTrackId)

ArtistRepository
- findByName(String name)

GenreRepository
- findByName(String name)
```

---

### 1.3 Services

```text
RecordService
- getAllRecords(): List<Record>
- addRecordFromDeezer(String trackId): Record

DeezerIntegrationService
- searchTracks(String query): List<DeezerTrackDTO>
- getTrackById(String id): DeezerTrackDTO
```

---

### 1.4 Controllers

```text
RecordController
- GET /records
- POST /records/deezer-add
- GET /records/deezer-search?query=...
```

---

## 2. DATABASE (PostgreSQL)

```sql
TABLE record (
  id SERIAL PRIMARY KEY,
  title VARCHAR,
  deezer_track_id VARCHAR,
  preview_url VARCHAR,
  artist_id INTEGER,
  album_cover_id INTEGER
)

TABLE artist (
  id SERIAL PRIMARY KEY,
  name VARCHAR
)

TABLE genre (
  id SERIAL PRIMARY KEY,
  name VARCHAR
)

TABLE record_genre (
  record_id INTEGER,
  genre_id INTEGER
)

TABLE album_cover (
  id SERIAL PRIMARY KEY,
  image_url VARCHAR
)
```

---

## 3. FRONTEND (React + TypeScript + SCSS)

### Components

- `<App>`: Holds global state (records, highlightedId, coin inserted)
- `<JukeboxFrame>`: Neon title, insert coin interaction, search area
- `<SearchAndImport>`: Search & import from Deezer API, informs parent
- `<VinylCarousel>`: Displays record list with play, like, delete buttons

### Features

- “Insert £1” enables access
- After 3 tracks played, access locks again
- Newly imported track scrolls into view & flashes
- Coin sound effect
- Neon flashing insert message
- Default album image fallback

---

## 4. DEPLOYMENT PLAN

### Frontend (React + Vite)
- **Host**: Netlify (free)
- `npm run build` → auto-deploy from GitHub
- Set `VITE_API_URL` in Netlify environment settings

### Backend (Spring Boot + Maven)
- **Host**: Railway (free tier)
- Connect GitHub repo
- Add environment variables:
    - `DB_URL`, `DB_USER`, `DB_PASS`
    - `DEEZER_ID`, `DEEZER_SECRET`

### Database (PostgreSQL)
- Hosted on **Railway**
- Replace local pgAdmin settings with Railway connection string
