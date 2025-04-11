package com.lola.vinyljukebox.config;

import com.lola.vinyljukebox.entities.Artist;
import com.lola.vinyljukebox.entities.Record;
import com.lola.vinyljukebox.repositories.ArtistRepository;
import com.lola.vinyljukebox.repositories.RecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ArtistRepository artistRepository;
    private final RecordRepository recordRepository;

    public DataLoader(ArtistRepository artistRepository, RecordRepository recordRepository) {
        this.artistRepository = artistRepository;
        this.recordRepository = recordRepository;
    }

    @Override
    public void run(String... args) {
        loadSampleData();
    }

    private void loadSampleData() {
        // First Artist
        Artist dombrance = new Artist();
        dombrance.setName("Dombrance");
        dombrance.setBio("Electronic music producer from Bordeaux.");
        dombrance.setCountry("FRANCE");
        artistRepository.save(dombrance);

        createRecordIfNotExists("AOC", 2021, "fakeSpotifyId123", "https://p.scdn.co/mp3-preview/sampleAOC", dombrance);

        // Second Artist
        Artist laFemme = new Artist();
        laFemme.setName("La Femme");
        laFemme.setBio("Psychedelic punk band from Biarritz.");
        laFemme.setCountry("FRANCE");
        artistRepository.save(laFemme);

        createRecordIfNotExists("Elle ne t'aime pas", 2016, "fakeSpotifyId134", "https://p.scdn.co/mp3-preview/sampleElleNeTaimePas", laFemme);

        // Third Artist
        Artist juniore = new Artist();
        juniore.setName("Juniore");
        juniore.setBio("Indie pop band from Paris.");
        juniore.setCountry("FRANCE");
        artistRepository.save(juniore);

        createRecordIfNotExists("Panique", 2017, "fakeSpotifyId145", "https://p.scdn.co/mp3-preview/samplePanique", juniore);

        // Fourth Artist
        Artist samaran = new Artist();
        samaran.setName("Samaran");
        samaran.setBio("Electronic music producer from London.");
        samaran.setCountry("UK");
        artistRepository.save(samaran);

        createRecordIfNotExists("Paris Madness", 2018, "fakeSpotifyId136", "https://p.scdn.co/mp3-preview/sampleParismadness", samaran);

        System.out.println("DataLoader: Sample data loaded!");
    }

    private void createRecordIfNotExists(String title, Integer year, String trackId, String previewUrl, Artist artist) {
        if (!recordRepository.findBySpotifyTrackId(trackId).isPresent()) {
            Record record = new Record();
            record.setTitle(title);
            record.setReleaseYear(year);
            record.setSpotifyTrackId(trackId);
            record.setPreviewUrl(previewUrl);
            record.setArtist(artist);
            recordRepository.save(record);
        }
    }
}
