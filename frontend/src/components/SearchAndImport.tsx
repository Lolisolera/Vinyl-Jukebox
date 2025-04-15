// src/components/SearchAndImport.tsx
import { useState } from 'react';
import axios from 'axios';

const SearchAndImport = () => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/records/spotify-search?query=${query}`);
      setResults(response.data);
    } catch (err) {
      console.error('Search failed:', err);
    } finally {
      setLoading(false);
    }
  };

 const handleImport = async (trackId: string) => {
   try {
     const response = await axios.post(`/api/records/spotify-add`, null, {
       params: { trackId },
     });

     if (response.status === 200) {
       alert("✅ Track imported!");
     } else {
       console.error("⚠️ Backend returned non-200:", response);
       alert("❌ Import failed. See console for details.");
     }
   } catch (error: any) {
     console.error("❌ Import failed:", error.response?.data || error.message);
     alert("❌ Import failed. The track may already exist or there's a problem with the connection.");
   }
 };


  return (
    <div style={{ marginBottom: '2rem' }}>
      <h2>🎧 Search and Import from Spotify</h2>
      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder="Search for a track..."
        style={{ padding: '0.5rem', width: '300px', marginRight: '1rem' }}
      />
      <button onClick={handleSearch}>Search</button>

      {loading && <p>Loading...</p>}
      <ul>
        {results.map((track: any) => (
          <li key={track.id} style={{ marginTop: '1rem' }}>
            <strong>{track.name}</strong> by {track.artistName}
            <button style={{ marginLeft: '1rem' }} onClick={() => handleImport(track.id)}>Import</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SearchAndImport;
