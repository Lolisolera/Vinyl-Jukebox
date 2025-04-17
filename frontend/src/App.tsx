// App.tsx
import { useEffect, useState } from 'react';
import { getAllRecords, Record } from './services/recordService';
import VinylCarousel from './components/VinylCarousel';
import SearchAndImport from './components/SearchAndImport';
import './App.scss';

const App = () => {
  const [records, setRecords] = useState<Record[]>([]);

  const fetchRecords = async () => {
    const data = await getAllRecords();
    setRecords(data);
  };

  useEffect(() => {
    fetchRecords();
  }, []);

  const handleDelete = (id: number) => {
    setRecords((prev) => prev.filter(record => record.id !== id));
  };

  return (
    <div className="app-container">
      <h1>🎶 Vinyl Jukebox</h1>

      {/* 🔍 Search bar */}
      <SearchAndImport />

      {/* 💿 Carousel of records */}
      <VinylCarousel records={records} onDelete={handleDelete} />
    </div>
  );
};

export default App;
