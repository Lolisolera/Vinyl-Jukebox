import { useEffect, useState } from 'react';
import { getAllRecords, Record } from './services/recordService';
import VinylCarousel from './components/VinylCarousel';
import JukeboxFrame from './components/JukeboxFrame';
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
      <JukeboxFrame>
        <VinylCarousel records={records} onDelete={handleDelete} />
      </JukeboxFrame>
    </div>
  );
};

export default App;
