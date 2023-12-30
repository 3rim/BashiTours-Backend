package com.bashitours.webapp.controller;

import com.bashitours.webapp.service.CountryService;
import com.bashitours.webapp.service.storage.StorageException;
import com.bashitours.webapp.service.storage.StorageFileNotFoundException;
import com.bashitours.webapp.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController()
@RequestMapping(path = "visited-country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService service){
        this.countryService = service;
    }

    @GetMapping("{country}/images")
    public ResponseEntity<List<String>> serveImages(@PathVariable String country) {
        try {

            List<String> images = countryService.getImagesNames(country);

            if(images.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (StorageException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong :(");
        }
    }

    @GetMapping("{country}/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String country, @PathVariable String filename) {
        Resource file = countryService.imagesAsResource(country,filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping("{country}/info-text")
    public ResponseEntity<String> serveInfoText(@PathVariable String country) {
        try {
            return new ResponseEntity<>(countryService.infoText(country),HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

      /*  System.out.println("post file");
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";*/
        return null;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
