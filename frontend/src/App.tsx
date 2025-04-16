import './App.scss';
import { useEffect, useState } from 'react';
import { getAllRecords, Record } from './services/recordService';
import SearchAndImport from './components/SearchAndImport';
import VinylCarousel from './components/VinylCarousel';

function App() {
  const [records, setRecords] = useState<Record[]>([]);

  useEffect(() => {
    getAllRecords().then(setRecords);
  }, []);

  return (
    <div className="App">
      <h1>🎵 Vinyl Jukebox</h1>

      {/* Search and Import */}
      <SearchAndImport />

      {/* Featured Carousel */}
      <h2>🎶 Featured Records</h2>
      <VinylCarousel records={records} />
    </div>
  );
}

export default App;
