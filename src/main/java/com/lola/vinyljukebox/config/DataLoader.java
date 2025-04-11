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

        Record aoc = new Record();
        aoc.setTitle("AOC");
        aoc.setReleaseYear(2021);
        aoc.setArtist(dombrance);
        aoc.setSpotifyTrackId("fakeSpotifyId123"); // placeholder
        aoc.setPreviewUrl("https://p.scdn.co/mp3-preview/sampleAOC"); // placeholder
        recordRepository.save(aoc);

        // Second Artist
        Artist laFemme = new Artist();
        laFemme.setName("La Femme");
        laFemme.setBio("Psychedelic punk band from Biarritz.");
        laFemme.setCountry("FRANCE");
        artistRepository.save(laFemme);

        Record elleNeTaimePas = new Record();
        elleNeTaimePas.setTitle("Elle ne t'aime pas");
        elleNeTaimePas.setReleaseYear(2016);
        elleNeTaimePas.setArtist(laFemme);
        elleNeTaimePas.setSpotifyTrackId("fakeSpotifyId134"); // placeholder
        elleNeTaimePas.setPreviewUrl("https://p.scdn.co/mp3-preview/sampleElleNeTaimePas"); // placeholder
        recordRepository.save(elleNeTaimePas);

        // Third Artist
        Artist juniore = new Artist();
        juniore.setName("Juniore");
        juniore.setBio("Indie pop band from Paris.");
        juniore.setCountry("FRANCE");
        artistRepository.save(juniore);

        Record panique = new Record();
        panique.setTitle("Panique");
        panique.setReleaseYear(2017);
        panique.setArtist(juniore);
        panique.setSpotifyTrackId("fakeSpotifyId145"); // placeholder
        panique.setPreviewUrl("https://p.scdn.co/mp3-preview/samplePanique"); // placeholder
        recordRepository.save(panique);

        // Forth Artist
        Artist samaran = new Artist();
        samaran.setName("Samaran");
        samaran.setBio("Electronic music producer from London.");
        samaran.setCountry("UK");
        artistRepository.save(samaran);

        Record parisMadness = new Record();
        parisMadness.setTitle("Paris Madness");
        parisMadness.setReleaseYear(2018);
        parisMadness.setArtist(samaran);
        parisMadness.setSpotifyTrackId("fakeSpotifyId136"); // placeholder
        parisMadness.setPreviewUrl("https://p.scdn.co/mp3-preview/sampleParismadness"); // placeholder
        recordRepository.save(parisMadness);


        System.out.println("DataLoader: Sample data loaded!");
    }


}
