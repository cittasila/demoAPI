package com.langying.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by chenxu on 2016/4/25.
 */
@ApiModel(value="题目类型状态",description = "阅读功能目录页按钮状态,0:未开通,1:未解锁,2:已解锁")
public class BookStatusInfo {
    @ApiModelProperty("bookId")
    private String bookId;
    @ApiModelProperty("阅读引导")
    private String readingGuide;
    @ApiModelProperty("原文阅读")
    private String textReading;
    @ApiModelProperty("原文跟读")
    private String readTheText;
    @ApiModelProperty("阅读理解")
    private String readingComprehension;
    private String readingComprehensionNum;

    @ApiModelProperty("阅读理解得分")
    private String readingComprehensionScore;


    @ApiModelProperty("词汇语法")
    private String lexicalGrammar;

    @ApiModelProperty("听力训练")
    private String listeningTraining;
    @ApiModelProperty("完型填空")
    private String cloze;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReadingGuide() {
        return readingGuide;
    }

    public void setReadingGuide(String readingGuide) {
        this.readingGuide = readingGuide;
    }

    public String getTextReading() {
        return textReading;
    }

    public void setTextReading(String textReading) {
        this.textReading = textReading;
    }

    public String getReadTheText() {
        return readTheText;
    }

    public void setReadTheText(String readTheText) {
        this.readTheText = readTheText;
    }

    public String getReadingComprehension() {
        return readingComprehension;
    }

    public void setReadingComprehension(String readingComprehension) {
        this.readingComprehension = readingComprehension;
    }

    public String getLexicalGrammar() {
        return lexicalGrammar;
    }

    public void setLexicalGrammar(String lexicalGrammar) {
        this.lexicalGrammar = lexicalGrammar;
    }

    public String getListeningTraining() {
        return listeningTraining;
    }

    public void setListeningTraining(String listeningTraining) {
        this.listeningTraining = listeningTraining;
    }

    public String getCloze() {
        return cloze;
    }

    public void setCloze(String cloze) {
        this.cloze = cloze;
    }

    public String getReadingComprehensionScore() {
        return readingComprehensionScore;
    }

    public void setReadingComprehensionScore(String readingComprehensionScore) {
        this.readingComprehensionScore = readingComprehensionScore;
    }
}
