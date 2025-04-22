import './SearchAndImport.scss';
import { useState } from 'react';
import axios from 'axios';

const SearchAndImport = () => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    if (!query.trim()) return;

    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/records/deezer-search?query=${query}`);
      console.log("🎧 Deezer search results:", response.data); // Debug previewUrl
      setResults(response.data);
    } catch (err) {
      console.error('Search failed:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleImport = async (trackId: string) => {
    try {
      const response = await axios.post(`/api/records/deezer-add`, null, {
        params: { trackId },
      });

      if (response.status === 200) {
        alert('✅ Track imported!');
      } else {
        alert('❌ Import failed.');
      }
    } catch (error: any) {
      console.error('❌ Import failed:', error.response?.data || error.message);
      alert('❌ Import failed. The track may already exist.');
    }
  };

  return (
    <div style={{ marginBottom: '2rem', textAlign: 'center' }}>

      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder="Search for a track..."
        style={{ padding: '0.5rem', width: '300px', marginRight: '1rem' }}
      />
      <button onClick={handleSearch}>Search</button>

      {loading && <p>Loading...</p>}

      {!loading && query && results.length === 0 && (
        <p style={{ color: 'gray', fontStyle: 'italic', marginTop: '1rem' }}>
          😢 Sorry, no tracks found for "<strong>{query}</strong>".
        </p>
      )}

      <ul style={{ listStyle: 'none', padding: 0 }}>
        {results.map((track: any) => {
          const isPlayable = track.previewUrl && track.previewUrl.endsWith('.mp3');
          console.log(`▶️ Track: ${track.title}, Preview URL:`, track.previewUrl);

          return (
            <li key={track.id} style={{ marginTop: '1.5rem' }}>
              <strong>{track.title}</strong> by {track.artistName}

              {isPlayable ? (
                <div style={{ marginTop: '0.5rem' }}>
                  <audio controls>
                    <source src={track.previewUrl} type="audio/mpeg" />
                    Your browser does not support the audio element.
                  </audio>
                </div>
              ) : (
                <p style={{ fontStyle: 'italic', color: '#999' }}>No preview available</p>
              )}

              <div>
                <button
                  style={{ marginTop: '0.5rem', padding: '0.4rem 0.8rem', cursor: 'pointer' }}
                  onClick={() => handleImport(track.id)}
                >
                  Import
                </button>
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default SearchAndImport;
