// src/services/recordService.ts
import axios from '../api';

export interface Record {
  id: number;
  title: string;
  previewUrl: string | null;
  spotifyTrackId: string;
  artist: {
    name: string;
  };
  genres: { name: string }[];
  albumCover?: {
    imageUrl: string;
  };
}

// GET all records
export const fetchRecords = async (): Promise<Record[]> => {
  const response = await axios.get<Record[]>('/records');
  return response.data;
};

// POST: Add record from Spotify
export const addRecordFromSpotify = async (trackId: string): Promise<Record> => {
  const response = await axios.post<Record>(`/records/spotify-add`, null, {
    params: { trackId },
  });
  return response.data;
};

// DELETE: Delete a record
export const deleteRecord = async (id: number): Promise<void> => {
  await axios.delete(`/records/${id}`);
};

// PUT: Update a record (optional)
export const updateRecord = async (id: number, updatedRecord: Partial<Record>): Promise<Record> => {
  const response = await axios.put<Record>(`/records/${id}`, updatedRecord);
  return response.data;
};
