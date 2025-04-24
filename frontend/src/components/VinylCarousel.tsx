import { useEffect, useRef, useState } from 'react';
import Slider from 'react-slick';
import { Record, deleteRecord } from '../services/recordService';
import './VinylCarousel.scss';

interface Props {
  records: Record[];
  onDelete: (id: number) => void;
  highlightedId?: number | null;
}

const VinylCarousel = ({ records, onDelete, highlightedId }: Props) => {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const [currentlyPlayingId, setCurrentlyPlayingId] = useState<number | null>(null);
  const [likedRecords, setLikedRecords] = useState<number[]>([]);
  const sliderRef = useRef<any>(null); // `any` for react-slick Slider

  useEffect(() => {
    if (highlightedId != null && sliderRef.current) {
      const index = records.findIndex((record) => record.id === highlightedId);
      if (index !== -1) {
        sliderRef.current.slickGoTo(index);
      }
    }
  }, [highlightedId, records]);

  const handleDelete = async (id: number) => {
    try {
      await deleteRecord(id); // Delete track from backend
      onDelete(id); // Immediately remove the track from the UI carousel
    } catch (error) {
      console.error('Failed to delete track:', error);
      // No alert here, the track is just not deleted if there's an error
    }
  };

  const handlePlayPause = (record: Record) => {
    if (!record.previewUrl || document.body.classList.contains('locked')) return;

    if (audioRef.current && currentlyPlayingId === record.id) {
      audioRef.current.pause();
      audioRef.current = null;
      setCurrentlyPlayingId(null);
    } else {
      audioRef.current?.pause();
      const newAudio = new Audio(record.previewUrl);
      newAudio.play();
      audioRef.current = newAudio;
      setCurrentlyPlayingId(record.id);
      newAudio.addEventListener('ended', () => {
        setCurrentlyPlayingId(null);
        audioRef.current = null;
      });
    }
  };

  const handleLike = (id: number) => {
    if (document.body.classList.contains('locked')) return;
    setLikedRecords((prev) =>
      prev.includes(id) ? prev.filter((likedId) => likedId !== id) : [...prev, id]
    );
  };

  const settings = {
    dots: true,
    infinite: records.length > 3,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    arrows: true, // Ensure arrows are enabled for navigation
    responsive: [
      { breakpoint: 1024, settings: { slidesToShow: 2 } },
      { breakpoint: 768, settings: { slidesToShow: 1 } },
    ],
  };

  return (
    <div className="vinyl-carousel">
      <Slider ref={sliderRef} {...settings}>
        {records.map((record) => {
          const imageUrl =
            record.albumCover?.imageUrl || record.albumCoverUrl || '/default-cover.jpg';
          const artistName =
            record.artist?.name || record.artistName || 'Unknown Artist';
          const isLiked = likedRecords.includes(record.id);

          return (
            <div key={record.id} className="record-slide">
              <div
                className={`image-wrapper ${highlightedId === record.id ? 'highlighted' : ''}`}
              >
                <img src={imageUrl} alt={record.title} />

                <button
                  className="overlay-button delete-button"
                  onClick={() => handleDelete(record.id)} // Deletes the record
                  title="Delete"
                >
                  🗑️
                </button>

                {record.previewUrl && (
                  <button
                    className="overlay-button play-button"
                    onClick={() => handlePlayPause(record)} // Play or Pause the track
                    title={currentlyPlayingId === record.id ? 'Pause' : 'Play'}
                  >
                    {currentlyPlayingId === record.id ? '⏸️' : '▶️'}
                  </button>
                )}

                <button
                  className={`overlay-button like-button ${isLiked ? 'liked' : ''}`}
                  onClick={() => handleLike(record.id)} // Like or Unlike the track
                  title={isLiked ? 'Unlike' : 'Like'}
                >
                  {isLiked ? '❤️' : '🤍'}
                </button>
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
