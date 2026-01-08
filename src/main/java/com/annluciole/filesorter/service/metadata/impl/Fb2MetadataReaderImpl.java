package com.annluciole.filesorter.service.metadata.impl;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.repository.FileInfoRepository;
import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandlerRepository;
import com.annluciole.filesorter.service.metadata.MetadataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Component
public class Fb2MetadataReaderImpl extends MetadataReader {

    @Autowired
    public Fb2MetadataReaderImpl(FileInfoRepository fileInfoRepository,
                                 CreationDateHandlerRepository repository) {
        super(fileInfoRepository, repository, "fb2");
    }

    @Override
    public void readAndSaveFileMetadata(Path path) {
        FileInfo fileInfo = defaultReadAndSaveFileMetadata(path);
        try {
            File fb2File = new File(fileInfo.getFilePath());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fb2File);
            doc.getDocumentElement().normalize();
            NodeList titleInfoList = doc.getElementsByTagName("title-info");
            if (titleInfoList.getLength() > 0) {
                Map<String, String> fileInfoMetadata = fileInfo.getMetadata();
                Element titleInfo = (Element) titleInfoList.item(0);
                getBookTitle(titleInfo, fileInfoMetadata);
                getAuthors(titleInfo, fileInfoMetadata);
                getGenre(titleInfo, fileInfoMetadata);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void getGenre(Element titleInfo, Map<String, String> fileInfoMetadata) {
        NodeList genreList = titleInfo.getElementsByTagName("genre");
        if (genreList.getLength() > 0) {
            fileInfoMetadata.put("genre", genreList.item(0).getTextContent());
        }
    }

    private void getAuthors(Element titleInfo, Map<String, String> fileInfoMetadata) {
        NodeList authorList = titleInfo.getElementsByTagName("author");
        for (int i = 0; i < authorList.getLength(); i++) {
            Element author = (Element) authorList.item(i);
            String authorFullName = getAuthorFullName(author);
            String authors = fileInfoMetadata.get("authors");
            if (authors == null) {
                authors = authorFullName;
            } else {
                authors = authors + ", " + authorFullName;
            }
            fileInfoMetadata.put("authors", authors);
        }
    }

    private String getAuthorFullName(Element author) {
        NodeList firstNameList = author.getElementsByTagName("first-name");
        NodeList lastNameList = author.getElementsByTagName("last-name");
        String authorFullName = null;
        if (lastNameList.getLength() > 0) {
            authorFullName = lastNameList.item(0).getTextContent();
        }
        if (firstNameList.getLength() > 0) {
            authorFullName = authorFullName
                    + " "
                    + firstNameList.item(0).getTextContent().charAt(0)
                    + ".";
        }
        return authorFullName;
    }

    private void getBookTitle(Element titleInfo, Map<String, String> fileInfoMetadata) {
        NodeList titleList = titleInfo.getElementsByTagName("book-title");
        if (titleList.getLength() > 0) {
            fileInfoMetadata.put("book-title", titleList.item(0).getTextContent());
        }
    }
}
