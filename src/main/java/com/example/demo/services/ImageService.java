package com.example.demo.services;

import com.example.demo.services.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImageFromFIle(MultipartFile uploadedFile) {
        // obtém a extensão do da imagem
        String extensao = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!"png".equals(extensao) && !"jpg".equals(extensao)) {
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }
        try {
            //gera um buffer image a partir do arquivo
            BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
            if ("png".equals(extensao)) {
                image = pngToJpg(image);
            }
            return image;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    //Converte o formato da imagem
    public BufferedImage pngToJpg(BufferedImage image) {
        BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight()
                , BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    //Para fazer upload precisa-se de um objeto InputStream, o qual se obteve pelo BufferedImage
    public InputStream getInputStream(BufferedImage img, String extension) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, extension, os);
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
        return new ByteArrayInputStream(os.toByteArray());
    }

    //função para croppar uma imagem
    public BufferedImage cropSquare(BufferedImage sourceImg) {
        int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getHeight();
        return Scalr.crop(sourceImg, sourceImg.getWidth() / 2 - (min / 2)
                , (sourceImg.getHeight() / 2) - (min / 2), min, min);
    }

    //FUnção para redimensionar uma imagem
    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
    }
}
