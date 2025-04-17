import axios from '../api';

// Match backend DTO or full entity shape
export interface Artist {
  name: string;
}

export interface AlbumCover {
  imageUrl: string;
}

export interface Record {
  id: number;
  title: string;
  previewUrl: string | null;
  deezerTrackId: string;
  artistName?: string;
  artist?: Artist | null;
  genres: string[];
  albumCoverUrl?: string | null;
  albumCover?: AlbumCover | null;
}

// GET all records
export const getAllRecords = async (): Promise<Record[]> => {
  const response = await axios.get<Record[]>('/records');
  return response.data;
};

// POST: Add record from Deezer
export const addRecordFromDeezer = async (trackId: string): Promise<Record> => {
  const response = await axios.post<Record>(`/records/deezer-add`, null, {
    params: { trackId },
  });
  return response.data;
};

// DELETE a record
export const deleteRecord = async (id: number): Promise<void> => {
  await axios.delete(`/records/${id}`);
};

// PUT: Update a record
export const updateRecord = async (
  id: number,
  updatedRecord: Partial<Record>
): Promise<Record> => {
  const response = await axios.put<Record>(`/records/${id}`, updatedRecord);
  return response.data;
};
