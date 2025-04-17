import Slider from 'react-slick';
import { Record, deleteRecord } from '../services/recordService';
import './VinylCarousel.scss';

interface Props {
  records: Record[];
  onDelete: (id: number) => void;
}

const VinylCarousel = ({ records, onDelete }: Props) => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    centerMode: true,
    centerPadding: '40px',
  };

  const handleDelete = async (id: number) => {
    const confirm = window.confirm('Are you sure you want to delete this track?');
    if (confirm) {
      await deleteRecord(id);
      onDelete(id);
    }
  };

  const handlePlay = (previewUrl: string) => {
    const audio = new Audio(previewUrl);
    audio.play();
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
                    onClick={() => handlePlay(record.previewUrl)}
                    title="Play"
                  >
                    ▶️
                  </button>
                )}
              </div>

              <h4>{record.title}</h4>
              <p>{artistName}</p>

              {/* Optional hidden audio */}
              {record.previewUrl && (
                <audio controls src={record.previewUrl} style={{ display: 'none' }} />
              )}
            </div>
          );
        })}
      </Slider>
    </div>
  );
};

export default VinylCarousel;
