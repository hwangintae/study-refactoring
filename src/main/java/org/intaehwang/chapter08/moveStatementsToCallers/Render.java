package org.intaehwang.chapter08.moveStatementsToCallers;

import org.intaehwang.chapter08.moveField.Clock;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.List;

public class Render {
    public void renderPerson(OutputStreamWriter outStream, Person person) throws IOException {
        outStream.write("<p>" + person.getName() + "</p>\n");
        renderPhoto(outStream, person.getPhoto());
        emitPhotoData(outStream, person.getPhoto());
        outStream.write("<p>위치" + person.getPhoto().getLocation() + "</p>");
    }

    public void listRecentPhotos(OutputStreamWriter outStream, List<Photo> photos) throws IOException {
        photos.stream()
                .filter(p -> p.getDate().isAfter(recentDateCutoff()))
                .forEach(p -> {
                    try {
                        outStream.write("<div>\n");
                        emitPhotoData(outStream, p);
                        outStream.write("<p>위치" + p.getLocation() + "</p>");
                        outStream.write("</div>\n");
                    } catch (Exception ignored) {
                    }
                });
    }

    public LocalDate recentDateCutoff() {
        return Clock.cutOff();
    }

    public void renderPhoto(OutputStreamWriter outStream, Photo p) throws IOException {
    }

    public void emitPhotoData(OutputStreamWriter outStream, Photo photo) throws IOException {
        outStream.write("<p>제목" + photo.getTitle() + "</p>");
        outStream.write("<p>날짜" + photo.getDate().toString() + "</p>");
    }
}
