import './JukeboxFrame.scss';
import SearchAndImport from './SearchAndImport';

const JukeboxFrame = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="jukebox-frame" aria-label="Vinyl Jukebox container">
      {/* 🔸 Top Arch */}
      <div className="jukebox-arch">
        <div className="coin-slot">
          <img src="/coin-slot.png" alt="Insert Coin" />
          <span>Insert £1</span>
        </div>
      </div>


      <div className="jukebox-logo">
        <div className="jukebox-header">
          <span className="jukebox-title">Vinyl Jukebox</span>
        </div>
      </div>


      <div className="jukebox-search">
        <h3>🔍 Search and add a record</h3>
        <SearchAndImport />
      </div>


      <div className="jukebox-carousel">
        {children}
      </div>


      <div className="jukebox-bottom">
        <div className="jukebox-glow" />
        <div className="jukebox-badge">Created by Lola Marquez 👽</div>
      </div>
    </div>
  );
};

export default JukeboxFrame;
