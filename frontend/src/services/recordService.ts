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
    throw error; // Rethrow for the calling component to handle
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
    throw error; // Rethrow for the calling component to handle
  }
};

// DELETE a record
export const deleteRecord = async (id: number): Promise<void> => {
  try {
    const response = await axios.delete(`/records/${id}`);
    if (response.status === 200) {
      console.log(`Record with ID: ${id} deleted successfully.`);
    } else {
      console.error(`Failed to delete record with ID: ${id}`);
    }
  } catch (error) {
    console.error(`Error while trying to delete record with ID: ${id}`, error);
    throw error; // Rethrow for the calling component to handle
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
    throw error; // Rethrow for the calling component to handle
  }
};
