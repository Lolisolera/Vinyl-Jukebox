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
  const audioRefs = useRef<Map<number, HTMLAudioElement>>(new Map());
  const [currentlyPlayingId, setCurrentlyPlayingId] = useState<number | null>(null);
  const [likedRecords, setLikedRecords] = useState<number[]>([]);
  const [deletingIds, setDeletingIds] = useState<number[]>([]);
  const sliderRef = useRef<any>(null);

  useEffect(() => {
    if (highlightedId != null && sliderRef.current) {
      const index = records.findIndex((record) => record.id === highlightedId);
      if (index !== -1) {
        sliderRef.current.slickGoTo(index);
      }
    }
  }, [highlightedId, records]);

  const handleDelete = async (id: number) => {
    if (deletingIds.includes(id)) return;
    setDeletingIds((prev) => [...prev, id]);

    try {
      console.log("Attempting to delete record with ID:", id);
      await deleteRecord(id);
      onDelete(id);
      if (audioRefs.current.has(id)) {
        const audio = audioRefs.current.get(id);
        audio?.pause();
        audioRefs.current.delete(id);
      }
      if (currentlyPlayingId === id) {
        setCurrentlyPlayingId(null);
      }
    } catch (error) {
      console.error('Failed to delete track:', error);
      alert('❌ Failed to delete the track. Please try again later.');
    } finally {
      setDeletingIds((prev) => prev.filter((item) => item !== id));
    }
  };

  const handlePlayPause = (record: Record) => {
    if (!record.previewUrl || document.body.classList.contains('locked')) return;

    const audio = audioRefs.current.get(record.id);

    if (!audio) {
      console.error('Audio element not found for record ID:', record.id);
      return;
    }

    if (currentlyPlayingId === record.id) {
      audio.pause();
      setCurrentlyPlayingId(null);
    } else {
      // Pause any other playing audio first
      if (currentlyPlayingId !== null) {
        const currentlyPlayingAudio = audioRefs.current.get(currentlyPlayingId);
        currentlyPlayingAudio?.pause();
      }

      audio.play().catch((error) => {
        console.error('Error playing audio:', error);
        alert('Failed to play track. The preview URL may be invalid.');
      });
      setCurrentlyPlayingId(record.id);

      audio.onended = () => {
        setCurrentlyPlayingId(null);
      };
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
    arrows: true,
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
          const isDeleting = deletingIds.includes(record.id);

          return (
            <div key={record.id} className="record-slide">
              <div
                className={`image-wrapper ${highlightedId === record.id ? 'highlighted' : ''}`}
              >
                <img src={imageUrl} alt={record.title} />

                <button
                  className="overlay-button delete-button"
                  onClick={() => handleDelete(record.id)}
                  title="Delete"
                  disabled={isDeleting}
                >
                  🗑️
                </button>

                {record.previewUrl ? (
                  <>
                    <audio
                      ref={(el) => {
                        if (el) {
                          audioRefs.current.set(record.id, el);
                        }
                      }}
                      src={record.previewUrl}
                      preload="none"
                      crossOrigin="anonymous"
                    />
                    <button
                      className="overlay-button play-button"
                      onClick={() => handlePlayPause(record)}
                      title={currentlyPlayingId === record.id ? 'Pause' : 'Play'}
                    >
                      {currentlyPlayingId === record.id ? '⏸️' : '▶️'}
                    </button>
                  </>
                ) : (
                  <p style={{ fontStyle: 'italic', color: '#999' }}>
                    No preview available
                  </p>
                )}

                <button
                  className={`overlay-button like-button ${isLiked ? 'liked' : ''}`}
                  onClick={() => handleLike(record.id)}
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
