package builder;

import parser.dto.Settings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 2019-01-25
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class ReportBuilder {

    private int pageHeight;
    private static int pageWidth;
    private String  firstColumnTitle;
    private int firstColumnWidth;
    private String  secondColumnTitle;
    private int secondColumnWidth;
    private String  thirdColumnTitle;
    private int thirdColumnWidth;
    private List<String> sourceData;
    private StringBuilder finalReport;
    final static String lineIn = "| ";
    final static String lineOut = " |\n\r";
    final static String lineSeparator = " | ";
    final static String lineDelimiter = Stream.generate(() -> "-").limit(pageWidth).collect(Collectors.joining());
    final static String pageDelimiter = "~";
    //final static String endLine = "";


    public ReportBuilder(Settings settings, List<String> sourceData) {
        this.pageHeight = settings.getPage().getHeight();
        this.pageWidth = settings.getPage().getWidth();
        this.firstColumnTitle = settings.getColumns().get(0).getTitle();
        this.firstColumnWidth = settings.getColumns().get(0).getWidth();
        this.secondColumnTitle = settings.getColumns().get(1).getTitle();
        this.secondColumnWidth = settings.getColumns().get(1).getWidth();
        this.thirdColumnTitle = settings.getColumns().get(2).getTitle();
        this.thirdColumnWidth = settings.getColumns().get(2).getWidth();
        this.sourceData = sourceData;
        this.finalReport = new StringBuilder();
    }

    public void createReport() {

        buildPage();

        finalReport.append("");
    }

    private void buildPage() {

        getHeader();


        getBand();


        finalReport.append("");

    }

    private String getHeader() {
        return new StringBuilder().
                append(lineIn).
                append(firstColumnTitle + getSpaceLine(firstColumnWidth - firstColumnTitle.length() - 1)).
                append(lineSeparator).
                append(secondColumnTitle + getSpaceLine(secondColumnWidth - secondColumnTitle.length() - 1)).
                append(lineSeparator).
                append(thirdColumnTitle + getSpaceLine(thirdColumnWidth - thirdColumnTitle.length() - 1)).
                append(lineOut).
                append(lineDelimiter).
                toString();
    }

    private StringBuilder getBand() {
        StringBuilder bandString = new StringBuilder();

        List<String> firstColumnList = new ArrayList<>();
        List<String> secondColumnList = new ArrayList<>();
        List<String> thirdColumnList = new ArrayList<>();
        int changeList = 1;
        int maxLines = 1;
        for (String single : sourceData) {
            if (changeList == 4) {
                changeList = 1;
            }
            if (changeList == 1) {
                checkAndFillCell(firstColumnList, single, firstColumnWidth);

                changeList++;
                System.out.println(bandString.toString());
                continue;
            }
            if (changeList == 2) {

                checkAndFillCell(secondColumnList, single, secondColumnWidth);

                secondColumnList.add(single);
                changeList++;
                System.out.println(bandString.toString());
                continue;
            }
            if (changeList == 3) {
                thirdColumnList.add(single);

                List<Integer> collSizes = Arrays.asList(firstColumnList.size(), secondColumnList.size(), thirdColumnList.size());

                maxLines = Collections.max(collSizes);
                firstColumnList = fillColumnBySpaces(firstColumnList, maxLines, firstColumnWidth);
                secondColumnList =  fillColumnBySpaces(secondColumnList, maxLines, secondColumnWidth);
                thirdColumnList = fillColumnBySpaces(thirdColumnList, maxLines, thirdColumnWidth);
                bandString.append(lineDelimiter);
                changeList++;
                System.out.println(bandString.toString());
                continue;
            }

        }

        //List<String> firstColumnList = getColumn(firstColumnWidth);
        //List<String> secondColumnList = getColumn(secondColumnWidth);
        //List<String> thirdColumnList = getColumn(thirdColumnWidth);

        // сливаем колонки в один бэнд
       /* int i = 0;
        while(i < maxLines - 3) {
            bandString.append(firstColumnList.get(i++));
            bandString.append(secondColumnList.get(i++));
            bandString.append(thirdColumnList.get(i++));
            bandString.append(endLine);
        }*/

        return bandString;
    }

    /**
     * Разбивает срочку на несколько если она не умещается в ширину столбца.
     * @param   singleLine - исходная строчка
     * @param   columnWidth - ширина столбца
     * @return  массив строк
     */
    private String[] splitStringToFewLines(String singleLine, int columnWidth) {
        String[] stringList = singleLine.split("\\W+");

        return stringList;
    }

    /**
     * Проверяет, умещается ли строка в столбец по ширине. Если нет - разбивает на коллекцию строк. Возврщает обработанную коллекцию.
     * @param columnList
     * @param single
     * @param columnWidth
     * @return
     */
    private List<String> checkAndFillCell(List<String> columnList, String single, int columnWidth) {
        //List<String> list;
        if (single.length() - 2 < columnWidth) {
            columnList.add(single + getSpaceLine(columnWidth - single.length()));
        } else {
            for (String line : splitStringToFewLines(single, columnWidth)) {
                columnList.add(line);
            }

            splitStringToFewLines(single, columnWidth);
            //String[] cellArray = single.split() // TODO здесь закончена логика

        }

        return columnList;
    }

    private List<String> fillColumnBySpaces(List<String> columnList, int maxLines, int width) {
        if (columnList.size() < maxLines) {
            for (int i = 0; i < maxLines - columnList.size(); i++) {
                columnList.add(getSpaceLine(width));
            }
        }
        return columnList;
    }

    private String getSpaceLine(int width) {
        return Stream.generate(() -> " ").limit(width).collect(Collectors.joining());
    }

    /*private List<String> getColumn(int width) {
        List<String> column = new ArrayList<>();

        return column;
    }*/

    private void getLine() {

        getCell(thirdColumnWidth);

    }

    private void getCell(int width) {

    }

    private void getCell(String title) {

    }

}
