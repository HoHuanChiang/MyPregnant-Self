package com.example.mypregnant.ObjectClasses;

public class QuestionnaireDocument {
    int documentID;
    int questionnaireID;
    String questionnaireName;
    int Status;

    public int getDocumentID() {
        return documentID;
    }

    public void setDocumentID(int documentID) {
        this.documentID = documentID;
    }

    public int getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(int questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

}
