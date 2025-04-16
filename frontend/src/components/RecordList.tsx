// src/components/RecordList.tsx
import { useEffect, useState } from 'react';
import { fetchRecords, Record } from '../services/recordService';
import './RecordList.scss';

const RecordList = () => {
  const [records, setRecords] = useState<Record[] | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadRecords = async () => {
      try {
        const data = await fetchRecords();
        console.log('Response from backend:', data);

        if (Array.isArray(data)) {
          setRecords(data);
        } else {
          console.error(' Unexpected format:', data);
          throw new Error('Unexpected data format received.');
        }
      } catch (error) {
        console.error('Error fetching records:', error);
        setError('Unexpected data format received.');
      } finally {
        setLoading(false);
      }
    };

    loadRecords();
  }, []);

  if (loading) return <p>Loading records...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (!records || records.length === 0) return <p>No records found.</p>;

  return (
    <div>
      <h2>🎶 All Records</h2>
      {records.map((record) => (
        <div
          key={record.id}
          style={{
            border: '1px solid #ccc',
            marginBottom: '1rem',
            padding: '1rem',
            borderRadius: '8px',
            backgroundColor: '#fffaf0'
          }}
        >
          <h3>{record.title}</h3>
          <p><strong>Artist:</strong> {record.artistName || 'Unknown'}</p>
          <p><strong>Genres:</strong> {record.genres && record.genres.length > 0 ? record.genres.join(', ') : 'N/A'}</p>

          {record.albumCoverUrl && (
            <img src={record.albumCoverUrl} alt={`Cover for ${record.title}`} width="200" />
          )}

          {record.previewUrl ? (
            <audio controls>
              <source src={record.previewUrl} type="audio/mpeg" />
              Your browser does not support the audio element.
            </audio>
          ) : (
            <p className="no-audio"><em>No preview available</em></p>
          )}
        </div>
      ))}
    </div>
  );
};

export default RecordList;
