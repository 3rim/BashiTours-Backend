package com.bashitours.webapp.service;

import com.bashitours.webapp.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CountryService  {

    @Autowired
    private final StorageService storageService;
    private final Path visitedCountryDir = Paths.get("upload-dir/visited-countries-dir");
    public CountryService(StorageService storageService){this.storageService = storageService;}


    public List<String> getImagesNames(String country){
        Path dir = visitedCountryDir.resolve(country+"/images");
        Stream<Path> paths = storageService.loadAll(dir);

        return paths
                .filter(path -> !Files.isDirectory(path))
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(file -> file.endsWith(".jpg") || file.endsWith(".png"))
                .toList();
    }

    public Resource imagesAsResource(String country,String filename){
        Path path = Paths.get("visited-countries-dir/"+country+"/images/"+filename);
        return storageService.loadAsResource(path.toString());
    }

    public String infoText (String country) throws IOException {
        Path path = visitedCountryDir.resolve(country+"/info.txt");
        String text = Files.readString(path);
        System.out.println(text);
        return text;
    }

    public void storeImages(){

    }
}
