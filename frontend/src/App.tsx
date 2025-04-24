import { useEffect, useState } from 'react';
import { getAllRecords, Record, deleteRecord } from './services/recordService'; // Import deleteRecord here
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
    try {
      await deleteRecord(id);  // Ensure deleteRecord is used here
      setRecords((prev) => prev.filter((record) => record.id !== id));
    } catch (error) {
      console.error('Failed to delete track:', error);
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
