package com.onlybuns.isa.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.coobird.thumbnailator.Thumbnails;


@Service
public class ImageCompressionService {

    private static final String IMAGE_DIR = "./uploads/images";

    @Scheduled(cron = "0 0 12 * * *") //svaki dan u 12
    public void compressOldImages() {
        File folder = new File(IMAGE_DIR);
        if (!folder.exists() || !folder.isDirectory()) return;

        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (shouldCompress(file)) {
                compressImage(file);
            }
        }
    }

    private boolean shouldCompress(File file) {
        if (file.getName().contains("_compressed")) return false;//preskace ako je slika vec kompresovana

        // Starije od 30 dana?
        long diff = System.currentTimeMillis() - file.lastModified();
        long thirtyDays = 30L * 24 * 60 * 60 * 1000;
        //ovo L je jer proizvod nekad moze da predje opseg inta pa se zato prebacuje u long

        return diff > thirtyDays;

       /* // Starije od 2 minuta?
        long diff = System.currentTimeMillis() - file.lastModified();
        long twoMinutes = 2L * 60 * 1000; // 2 minuta u milisekundama

        return diff > twoMinutes;*/
    }

    private void compressImage(File file) {
        try {
            String compressedName = file.getName().replace(".", "_compressed.");
            File compressedFile = new File(file.getParentFile(), compressedName);

            Thumbnails.of(file)
                    .scale(1.0) //zadrzava dimenzije slike
                    .outputQuality(0.25f) //0.0 bi bilo najlosiji kvalitet a 1.0 bez kompresije
                    .toFile(compressedFile); //Snima obradjenu sliku u fajl compressedFile.

            System.out.println("Kompresovana slika: " + compressedFile.getName());

        } catch (IOException e) {
            System.err.println("Greška pri kompresiji slike: " + file.getName());
            e.printStackTrace();
        }
    }

}
