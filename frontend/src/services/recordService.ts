import axios from '../api';

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
  try {
    const response = await axios.get<Record[]>('/records');
    return response.data;
  } catch (error) {
    console.error("Failed to fetch records:", error);
    throw error;
  }
};

// POST: Add record from Deezer
export const addRecordFromDeezer = async (trackId: string): Promise<Record> => {
  try {
    const response = await axios.post<Record>('/records/deezer-add', null, {
      params: { trackId },
    });
    return response.data;
  } catch (error) {
    console.error("Failed to add record from Deezer:", error);
    throw error;
  }
};

// DELETE a record
export const deleteRecord = async (id: number): Promise<void> => {
  try {
    await axios.delete(`/records/${id}`);
  } catch (error) {
    console.error("Failed to delete record:", error);
    throw error;
  }
};

// PUT: Update a record
export const updateRecord = async (
  id: number,
  updatedRecord: Partial<Record>
): Promise<Record> => {
  try {
    const response = await axios.put<Record>(`/records/${id}`, updatedRecord);
    return response.data;
  } catch (error) {
    console.error("Failed to update record:", error);
    throw error;
  }
};
