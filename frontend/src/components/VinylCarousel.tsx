// VinylCarousel.tsx
import Slider from 'react-slick';
import { Record } from '../services/recordService';
import './VinylCarousel.scss';

interface Props {
  records: Record[];
}

const VinylCarousel = ({ records }: Props) => {
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
        {records.map((record) => (
          <div key={record.id} className="record-slide">
            <img
              src={record.albumCoverUrl || '/default-cover.jpg'}
              alt={`Cover of ${record.title}`}
              style={{
                width: '250px',
                height: '250px',
                objectFit: 'cover',
                borderRadius: '12px',
                boxShadow: '0 4px 10px rgba(0,0,0,0.2)',
              }}
            />
            <h4>{record.title}</h4>
            <p>{record.artistName || 'Unknown Artist'}</p>
            {record.previewUrl && (
              <audio controls src={record.previewUrl} />
            )}
          </div>
        ))}
      </Slider>
    </div>
  );
};

export default VinylCarousel;
