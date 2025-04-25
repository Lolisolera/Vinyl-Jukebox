import { useEffect, useState } from 'react';
import { getAllRecords, Record, deleteRecord } from './services/recordService';
import VinylCarousel from './components/VinylCarousel';
import JukeboxFrame from './components/JukeboxFrame';
import './App.scss';

const App = () => {
  const [records, setRecords] = useState<Record[]>([]);
  const [highlightedId, setHighlightedId] = useState<number | null>(null);

  const fetchRecords = async () => {
    const data = await getAllRecords();
    setRecords(data);
  };

  useEffect(() => {
    fetchRecords();
  }, []);

  const handleDelete = async (id: number) => {
    const recordExists = records.some((r) => r.id === id);
    if (!recordExists) return; // Prevent double-deletion attempt

    try {
      await deleteRecord(id);
      setRecords((prev) => prev.filter((record) => record.id !== id));
    } catch (error) {
      console.error('Failed to delete track:', error);
      alert('❌ Failed to delete the track. Please try again later.');
    }
  };

  const handleTrackImported = (newTrack: Record) => {
    setRecords((prev) => [...prev, newTrack]);
    setHighlightedId(newTrack.id);
    setTimeout(() => setHighlightedId(null), 3000);
  };

  return (
    <div className="app-container">
      <JukeboxFrame onTrackImported={handleTrackImported}>
        <VinylCarousel
          records={records}
          onDelete={handleDelete}
          highlightedId={highlightedId}
        />
      </JukeboxFrame>
    </div>
  );
};

export default App;
