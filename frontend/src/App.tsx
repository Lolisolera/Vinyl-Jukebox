import './App.scss';
import { useState } from 'react';

import RecordList from './components/RecordList';
import SearchAndImport from './components/SearchAndImport';

function App() {
  return (
    <div className="App">
      <h1>🎵 Vinyl Jukebox</h1>

      {/* NEW: Spotify Search and Import Feature */}
      <SearchAndImport />

      {/* Existing: List of all records */}
      <RecordList />
    </div>
  );
}

export default App;
