package org.intaehwang.chapter06.renameVariable;

public class RenameVariable {
    public void before() {
        String tpHd = "대흥동 타이거 우직";
        String result = "";

        result += "<h1>" + tpHd + "</h1>";
        tpHd = "test";
    }

    public void after() {
        Title title = new Title("대흥동 타이거 우직");
        String result = "";

        result += "<h1>" + title.getTitle() + "</h1>";
        title.setTitle("test");
    }

    public static class Title {
        private String tpHd = "";

        public Title(String tpHd) {
            this.tpHd = tpHd;
        }

        public String getTitle() {
            return tpHd;
        }

        public void setTitle(String tpHd) {
            this.tpHd = tpHd;
        }
    }
}
