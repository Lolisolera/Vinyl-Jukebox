import { useState } from 'react';
import './JukeboxFrame.scss';
import SearchAndImport from './SearchAndImport';
import { Record } from '../services/recordService';

interface JukeboxFrameProps {
  children: React.ReactNode;
  /** Called when a track is imported so parent can update carousel */
  onTrackImported: (record: Record) => void;
}

const JukeboxFrame = ({ children, onTrackImported }: JukeboxFrameProps) => {
  const [coinInserted, setCoinInserted] = useState(false);

  const handleInsertCoin = () => {
    setCoinInserted(true);
    const audio = new Audio('/coin-sound.mp3');
    audio.play();
  };

  return (
    <div
      className={`jukebox-frame ${!coinInserted ? 'locked' : ''}`}
      aria-label="Vinyl Jukebox container"
    >
      {/* Top Arch */}
      <div className="jukebox-arch">
        <div
          className="coin-slot"
          onClick={handleInsertCoin}
          style={{ cursor: 'pointer' }}
        >
          <img src="/coin-slot.png" alt="Insert Coin" />
          <span>Insert £1</span>
        </div>
      </div>

      <div className="jukebox-logo">
        <div className="jukebox-header">
          <span className="jukebox-title">Vinyl Jukebox</span>
        </div>
      </div>

      {!coinInserted ? (
        <div className="jukebox-insert-message">
          <span className="neon-flicker">Please insert £1 to start</span>
        </div>
      ) : (
        <div className="jukebox-search">
          <h3>Search and add a record</h3>
          {/* Now guaranteed defined */}
          <SearchAndImport onTrackImported={onTrackImported} />
        </div>
      )}

      <div className="jukebox-carousel">{children}</div>

      <div className="jukebox-bottom">
        <div className="jukebox-glow" />
        <div className="jukebox-badge">Created by Lola Marquez 👽</div>
      </div>
    </div>
  );
};

export default JukeboxFrame;
