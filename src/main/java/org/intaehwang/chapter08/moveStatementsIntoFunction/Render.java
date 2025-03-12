package org.intaehwang.chapter08.moveStatementsIntoFunction;

import java.util.List;

public class Render {

    public String renderPerson(Person person) {
        List<String> result = List.of(
                "<p>" + person.getName() + "</p>",
                "<p>제목: " + person.getPhoto().getTitle() + "</p>"
        );

        return String.join("\n", result);
    }

    public String renderPhoto(Photo p) {
        return String.join("\n", List.of(
                "<div>",
                emitPhotoData(p),
                "</div>"
        ));
    }

    public String emitPhotoData(Photo aPhoto) {
        List<String> result = List.of(
                "<p>" + aPhoto.getTitle() + "</p>",
                "<p>위치" + aPhoto.getLocation() + "</p>",
                "<p>날짜" + aPhoto.getDate().toString() + "</p>"
        );

        return String.join("\n", result);
    }
}
