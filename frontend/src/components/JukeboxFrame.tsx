import './JukeboxFrame.scss';
import SearchAndImport from './SearchAndImport';

const JukeboxFrame = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="jukebox-frame" aria-label="Vinyl Jukebox container">
      <div className="jukebox-arch" />

      <div className="jukebox-logo">
        <span>Vinyl Jukebox</span>
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
        <div className="jukebox-badge">Sgt. Pepper's Lonely Hearts Club Band</div>
      </div>
    </div>
  );
};

export default JukeboxFrame;
