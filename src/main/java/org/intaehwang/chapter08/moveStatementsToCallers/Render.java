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
    }

    public void listRecentPhotos(OutputStreamWriter outStream, List<Photo> photos) throws IOException {
        photos.stream()
                .filter(p -> p.getDate().isAfter(recentDateCutoff()))
                .forEach(p -> {
                    try {
                        outStream.write("<div>\n");
                        emitPhotoData(outStream, p);
                        outStream.write("</div>\n");
                    } catch (Exception ignored) {
                    }
                });
    }

    public void emitPhotoData(OutputStreamWriter outStream, Photo aPhoto) throws IOException {
        outStream.write("<p>제목" + aPhoto.getTitle() + "</p>");
        outStream.write("<p>날짜" + aPhoto.getDate().toString() + "</p>");
        outStream.write("<p>위치" + aPhoto.getLocation() + "</p>");
    }

    public LocalDate recentDateCutoff() {
        return Clock.cutOff();
    }

    public void renderPhoto(OutputStreamWriter outStream, Photo p) throws IOException {
        emitPhotoData(outStream, p);
    }
}
