package com.lola.vinyljukebox.config;

import com.lola.vinyljukebox.services.RecordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecordService recordService;

    public DataLoader(RecordService recordService) {
        this.recordService = recordService;
    }

    @Override
    public void run(String... args) {
        System.out.println("🎧 Seeding favorite tracks...");

        if (recordService.countRecords() == 0) {

            recordService.createRecordManually(
                    "Da Funk", 1995, "Daft Punk", "Electronic",
                    "https://cdns-preview-c.dzcdn.net/stream/c-c0bbda75c7e1c97e556b645820e779b2-4.mp3",
                    "https://api.deezer.com/album/302127/image"
            );

            recordService.createRecordManually(
                    "Sur La Planche", 2013, "La Femme", "Rock",
                    "https://cdns-preview-b.dzcdn.net/stream/c-b6d1db5951b4f4c3cfd5fa6a8dce76f7-6.mp3",
                    "https://api.deezer.com/album/6795004/image"
            );

            recordService.createRecordManually(
                    "Hey Joe", 2017, "Charlotte Gainsbourg", "Alternative",
                    "https://cdns-preview-6.dzcdn.net/stream/c-66f7fd5bd7d367bbc79d47629f10edbc-5.mp3",
                    "https://api.deezer.com/album/50923602/image"
            );

            recordService.createRecordManually(
                    "Thriller", 1982, "Michael Jackson", "Pop",
                    "https://cdns-preview-2.dzcdn.net/stream/c-2121e85c1f6fd7e4ebf70c6fa7b33d60-7.mp3",
                    "https://api.deezer.com/album/302127/image"
            );

            recordService.createRecordManually(
                    "Panique", 2016, "Juniore", "Indie",
                    "https://cdns-preview-9.dzcdn.net/stream/c-9e6848cb5cb9d129dab1b4450e918645-2.mp3",
                    "https://api.deezer.com/album/13828826/image"
            );

            recordService.createRecordManually(
                    "Sexy Boy", 1998, "Air", "Electronic",
                    "https://cdns-preview-7.dzcdn.net/stream/c-7a4539c8b5bc0decdcd28ad9d8d6ca1e-4.mp3",
                    "https://api.deezer.com/album/302127/image"
            );

            recordService.createRecordManually(
                    "Dorothy", 2017, "Polo & Pan", "Electronic",
                    "https://cdns-preview-3.dzcdn.net/stream/c-361a4625fef6f1b7179a4b03f9d2e2b7-5.mp3",
                    "https://api.deezer.com/album/43207581/image"
            );

            recordService.createRecordManually(
                    "Platoon", 2013, "Jungle", "Funk",
                    "https://cdns-preview-4.dzcdn.net/stream/c-426f1a607ce18010b8c357a7650a34e7-3.mp3",
                    "https://api.deezer.com/album/7135942/image"
            );

            recordService.createRecordManually(
                    "Wet Dream", 2022, "Wet Leg", "Alternative",
                    "https://cdns-preview-3.dzcdn.net/stream/c-303fc8c17b8409d6a384e7a2b0d5cf55-5.mp3",
                    "https://api.deezer.com/album/299184512/image"
            );

            recordService.createRecordManually(
                    "Soopertrack", 2005, "Extrawelt", "Electronic",
                    "https://cdns-preview-5.dzcdn.net/stream/c-5c46e9e38a1e64a7e86e8b3ecb6f8f3d-3.mp3",
                    "https://api.deezer.com/album/217800/image"
            );

            recordService.createRecordManually(
                    "Nightlife", 2008, "Miss Kittin", "Electroclash",
                    "https://cdns-preview-2.dzcdn.net/stream/c-2c3e07b1b0a57a3421d8a3f8443e6a5e-5.mp3",
                    "https://api.deezer.com/album/2472584/image"
            );

            recordService.createRecordManually(
                    "The Looking Glass", 2017, "Charlotte Gainsbourg", "Alternative",
                    "https://cdns-preview-0.dzcdn.net/stream/c-05a3b8880f053c3dbf07c7ed1b2cc18a-5.mp3",
                    "https://api.deezer.com/album/50923602/image"
            );

            recordService.createRecordManually(
                    "Beat the Track", 2019, "Carl Cox", "Techno",
                    "https://cdns-preview-d.dzcdn.net/stream/c-d2923cc943ef1f098c3b5ce024f5d1b3-3.mp3",
                    "https://api.deezer.com/album/89382272/image"
            );

            recordService.createRecordManually(
                    "Leather Forever", 2008, "Miss Kittin", "Electroclash",
                    "https://cdns-preview-3.dzcdn.net/stream/c-37d78f4c2c3d3f07d88b093a4c1ee460-4.mp3",
                    "https://api.deezer.com/album/2472584/image"
            );

            System.out.println("✅ Favorite tracks seeded!");
        } else {
            System.out.println("ℹ️ Tracks already exist, skipping seeding.");
        }
    }
}
