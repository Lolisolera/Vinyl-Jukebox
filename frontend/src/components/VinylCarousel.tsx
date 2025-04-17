import { useRef, useState } from 'react';
import Slider from 'react-slick';
import { Record, deleteRecord } from '../services/recordService';
import './VinylCarousel.scss';

interface Props {
  records: Record[];
  onDelete: (id: number) => void;
}

const VinylCarousel = ({ records, onDelete }: Props) => {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const [currentlyPlayingId, setCurrentlyPlayingId] = useState<number | null>(null);

  const handleDelete = async (id: number) => {
    const confirm = window.confirm('Are you sure you want to delete this track?');
    if (confirm) {
      if (audioRef.current && currentlyPlayingId === id) {
        audioRef.current.pause();
        audioRef.current = null;
        setCurrentlyPlayingId(null);
      }

      await deleteRecord(id);
      onDelete(id);
    }
  };

  const handlePlayPause = (record: Record) => {
    if (!record.previewUrl) return;

    if (audioRef.current && currentlyPlayingId === record.id) {
      // Pause if it's the same track
      audioRef.current.pause();
      audioRef.current = null;
      setCurrentlyPlayingId(null);
    } else {
      // Stop current track if another is playing
      if (audioRef.current) {
        audioRef.current.pause();
      }

      const newAudio = new Audio(record.previewUrl);
      newAudio.play();
      audioRef.current = newAudio;
      setCurrentlyPlayingId(record.id);

      // Cleanup when track ends
      newAudio.addEventListener('ended', () => {
        setCurrentlyPlayingId(null);
        audioRef.current = null;
      });
    }
  };

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    centerMode: true,
    centerPadding: '40px',
  };

  return (
    <div className="vinyl-carousel">
      <Slider {...settings}>
        {records.map((record) => {
          const imageUrl = record.albumCover?.imageUrl || record.albumCoverUrl || '/default-cover.jpg';
          const artistName = record.artist?.name || record.artistName || 'Unknown Artist';

          return (
            <div key={record.id} className="record-slide">
              <div className="image-wrapper">
                <img src={imageUrl} alt={record.title} />

                <button
                  className="overlay-button delete-button"
                  onClick={() => handleDelete(record.id)}
                  title="Delete"
                >
                  🗑️
                </button>

                {record.previewUrl && (
                  <button
                    className="overlay-button play-button"
                    onClick={() => handlePlayPause(record)}
                    title={currentlyPlayingId === record.id ? 'Pause' : 'Play'}
                  >
                    {currentlyPlayingId === record.id ? '⏸️' : '▶️'}
                  </button>
                )}
              </div>

              <h4>{record.title}</h4>
              <p>{artistName}</p>
            </div>
          );
        })}
      </Slider>
    </div>
  );
};

export default VinylCarousel;
