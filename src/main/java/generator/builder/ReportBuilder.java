package generator.builder;

import generator.parser.dto.Settings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 2019-02-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class ReportBuilder {

    private static int pageWidth;
    private static String lineDelimiter;
    private static final String LINE_BREAKER = System.lineSeparator();
    private static final String lineIn = "| ";
    private static final String lineOut = " |" + LINE_BREAKER;
    private static final String lineSeparator = " | ";
    private static final String pageDelimiter = "~" + LINE_BREAKER;
    private int pageHeight;
    private String firstColumnTitle;
    private int firstColumnWidth;
    private String secondColumnTitle;
    private int secondColumnWidth;
    private String thirdColumnTitle;
    private int thirdColumnWidth;
    private List<String> sourceData;

    public ReportBuilder(Settings settings, List<String> sourceData) {
        this.pageHeight = settings.getPage().getHeight();
        pageWidth = settings.getPage().getWidth();
        this.firstColumnTitle = settings.getColumns().get(0).getTitle();
        this.firstColumnWidth = settings.getColumns().get(0).getWidth();
        this.secondColumnTitle = settings.getColumns().get(1).getTitle();
        this.secondColumnWidth = settings.getColumns().get(1).getWidth();
        this.thirdColumnTitle = settings.getColumns().get(2).getTitle();
        this.thirdColumnWidth = settings.getColumns().get(2).getWidth();
        this.sourceData = sourceData;
        lineDelimiter = Stream.generate(() -> "-").limit(pageWidth).collect(Collectors.joining()) + LINE_BREAKER;
    }

    public List<String> createReport() {

        List<String> reportLines = getBand();
        for (int i = 0; i < reportLines.size(); i += getPageHeight()) {
            if (i != 0 && reportLines.get(i).equals(lineDelimiter)) {
                reportLines.remove(i);
            }
            if (i != 0) {
                reportLines.add(i, pageDelimiter);
                reportLines.add(i + 1, getHeader());
                reportLines.add(i + 2, lineDelimiter);
            } else {
                reportLines.add(i, getHeader());
                reportLines.add(i + 1, lineDelimiter);
            }
        }
        return reportLines;
    }

    private String getHeader() {
        return  lineIn +
                firstColumnTitle + getSpaceLine(firstColumnWidth - firstColumnTitle.length()) +
                lineSeparator +
                secondColumnTitle + getSpaceLine(secondColumnWidth - secondColumnTitle.length()) +
                lineSeparator +
                thirdColumnTitle + getSpaceLine(thirdColumnWidth - thirdColumnTitle.length()) +
                lineOut;
    }

    private List<String> getBand() {
        List<String> band = new ArrayList<>();
        List<String> firstColumnList = new ArrayList<>();
        List<String> secondColumnList = new ArrayList<>();
        List<String> thirdColumnList = new ArrayList<>();
        int changeList = 1;
        int maxLines;
        for (String single : sourceData) {
            if (changeList == 4) {
                changeList = 1;
            }
            if (changeList == 1) {
                firstColumnList = new ArrayList<>();
                firstColumnList.addAll(splitStringToFewLines(single, firstColumnWidth, changeList));
                changeList++;
                continue;
            }
            if (changeList == 2) {
                secondColumnList = new ArrayList<>();
                secondColumnList.addAll(splitStringToFewLines(single, secondColumnWidth, changeList));
                changeList++;
                continue;
            }
            if (changeList == 3) {
                thirdColumnList = new ArrayList<>();
                thirdColumnList.addAll(splitStringToFewLines(single, thirdColumnWidth, changeList));
                List<Integer> collSizes =
                        Arrays.asList(firstColumnList.size(), secondColumnList.size(), thirdColumnList.size());
                maxLines = Collections.max(collSizes);
                firstColumnList = fillColumnBySpaces(firstColumnList, maxLines, firstColumnWidth);
                secondColumnList = fillColumnBySpaces(secondColumnList, maxLines, secondColumnWidth);
                thirdColumnList = fillColumnBySpaces(thirdColumnList, maxLines, thirdColumnWidth);
                if (!band.isEmpty()) {
                    band.add(lineDelimiter);
                }
                for (int i = 0; i < maxLines; i++) {
                    band.add(lineIn +
                            firstColumnList.get(i) +
                            lineSeparator +
                            secondColumnList.get(i) +
                            lineSeparator +
                            thirdColumnList.get(i) +
                            lineOut);
                }
                changeList++;
            }
        }
        return band;
    }

    /**
     * Разбивает срочку на несколько.
     *
     * @param singleLine  - исходная строчка
     * @param columnWidth - ширина столбца
     * @return массив строк
     */
    private List<String> splitStringToFewLines(String singleLine, int columnWidth, int columnNumber) {
        List<String> singleList = new ArrayList<>();
        String rule;
        switch (columnNumber) {
            case 2:
                rule = "(?<=/)|(?=/)";
                break;
            case 3:
                rule = "\\w|(?<=-)|(?=-)|\\s";
                break;
            default:
                rule = "";
        }
        String[] stringList = singleLine.split(rule);
        if (stringList.length == 0) {
            stringList = new String[]{singleLine};
        }
        StringBuilder fitLine = new StringBuilder();
        for (String word : stringList) {
            if (!word.equals(" ") || !word.isEmpty()) {
                if (word.length() <= columnWidth) {
                    if (isFitInCurrentLine(fitLine, word, columnWidth)) {
                        fitLine.append(word);
                        if (columnNumber == 3) {
                            fitLine.append(" ");
                        }
                    } else {
                        singleList.add(fitLine.toString().trim());
                        fitLine = new StringBuilder();
                        fitLine.append(word);
                    }
                } else {
                    if (fitLine.length() > 0) {
                        singleList.add(fitLine.toString().trim());
                        fitLine = new StringBuilder();
                    }
                    while (word.length() > columnWidth) {
                        singleList.add(word.substring(0, columnWidth));
                        word = word.substring(columnWidth);
                    }
                    fitLine.append(word);
                }
            }
        }
        if (fitLine.length() > 0) {
            singleList.add(fitLine.toString().trim());
        }
        return singleList;
    }

    /**
     * Проверяет, поместится ли еще один элемент (слово/символ) в текущую строчку.
     * @param fitLine       - Строка набора элементов по ширине столбца
     * @param word          - слово/символ, который проверяется на возможность размещения в текущей строке столбца
     * @param columnWidth   - ширина текущего столбца
     * @return              - true (элемент умещается) или false (элемент не умещается)
     */
    private boolean isFitInCurrentLine(StringBuilder fitLine, String word, int columnWidth) {
        return (fitLine.length() + word.length()) <= (columnWidth);
    }

    /**
     * Заполняет существующие строки в столбце пробелами до ширины столбца, а потом и недостающие строки столбца.
     *
     * @param columnList коллекция имеющихся строк столбца
     * @param maxLines   Макс. количество строк в бэнде
     * @param width      ширина столбца
     * @return коллекция заполенных строк
     */
    private List<String> fillColumnBySpaces(List<String> columnList, int maxLines, int width) {
        for (int i = 0; i < columnList.size(); i++) {
            if (columnList.get(i).length() < width) {
                columnList.set(i, columnList.get(i) + getSpaceLine(width - columnList.get(i).length()));
            }
        }
        if (columnList.size() < maxLines) {
            int colSize = columnList.size();
            for (int i = 0; i <= maxLines - colSize; i++) {
                columnList.add(getSpaceLine(width));
            }
        }
        return columnList;
    }

    /**
     * Заполняет строку ячейки пробелами на всю ширину.
     *
     * @param width ширина колонки
     * @return стринга пробелов
     */
    private String getSpaceLine(int width) {
        return Stream.generate(() -> " ").limit(width).collect(Collectors.joining());
    }

    private int getPageHeight() {
        return pageHeight;
    }
}
